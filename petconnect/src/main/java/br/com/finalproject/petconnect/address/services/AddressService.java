package br.com.finalproject.petconnect.address.services;

import br.com.finalproject.petconnect.address.domain.dto.AddressRequest;
import br.com.finalproject.petconnect.address.domain.dto.AddressResponse;
import br.com.finalproject.petconnect.address.domain.entities.Address;
import br.com.finalproject.petconnect.address.http.ViaCepClient;
import br.com.finalproject.petconnect.address.repositories.AddressRepository;
import br.com.finalproject.petconnect.exceptions.runtimes.generics.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AddressService {

    private final ViaCepClient viaCepClient;
    private final AddressRepository addressRepository;

    public AddressResponse createAddress(AddressRequest addressRequest) {
        ResponseEntity<AddressResponse> response = viaCepClient.getAddress(addressRequest.getZipCode());
        Address address = getAddressFromResponse(response, addressRequest);
        addressRepository.save(address);
        return mapToResponse(address);
    }

    public AddressResponse getAddressById(Long id) {
        final var address = getAddress(id);
        return mapToResponse(address);
    }

    public List<AddressResponse> listAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public AddressResponse updateAddress(Long id, AddressRequest addressRequest) {
        final var address = getAddress(id);

        ResponseEntity<AddressResponse> response = viaCepClient.getAddress(addressRequest.getZipCode());

        Address updatedAddress = getAddressFromResponse(response, addressRequest);
        address.setZipCode(updatedAddress.getZipCode());
        address.setStreet(updatedAddress.getStreet());
        address.setComplement(updatedAddress.getComplement());
        address.setNeighborhood(updatedAddress.getNeighborhood());
        address.setCity(updatedAddress.getCity());
        address.setState(updatedAddress.getState());
        address.setNumber(updatedAddress.getNumber());

        addressRepository.save(address);

        return mapToResponse(address);
    }

    public void deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Endereço não encontrado");
        }
        addressRepository.deleteById(id);
    }

    public AddressResponse getAddressByZipCode(String zipCode) {
        Optional<Address> optionalAddress = addressRepository.findByZipCode(zipCode);
        if (optionalAddress.isPresent()) {
            return mapToResponse(optionalAddress.get());
        }
        ResponseEntity<AddressResponse> response = viaCepClient.getAddress(zipCode);
        Address address = getAddressFromResponse(response, null); // Pass null as AddressRequest
        addressRepository.save(address);
        return mapToResponse(address);
    }

    public List<AddressResponse> searchAddresses(String state, String city, String neighborhood, String zipCode) {
        ResponseEntity<?> responseEntity = determineSearchCriteria(state, city, neighborhood, zipCode);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return processSuccessfulResponse(responseEntity);
        }
        throw new ResourceNotFoundException("Nenhum endereço encontrado.");
    }

    private Address getAddress(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
    }

    private Address getAddressFromResponse(ResponseEntity<AddressResponse> response, AddressRequest addressRequest) {
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            AddressResponse addressResponse = response.getBody();

            return Address.builder()
                    .zipCode(addressResponse.getZipCode())
                    .street(addressResponse.getStreet())
                    .complement(addressResponse.getComplement())
                    .neighborhood(addressResponse.getNeighborhood())
                    .city(addressResponse.getCity())
                    .state(addressResponse.getState())
                    .number(addressRequest != null ? addressRequest.getNumber() : null)
                    .build();
        }
        throw new ResourceNotFoundException("CEP inválido ou não encontrado.");
    }

    private ResponseEntity<?> determineSearchCriteria(String state, String city, String neighborhood, String zipCode) {

        if (state != null && city != null && neighborhood != null) {
            return viaCepClient.getAddressByNeighborhood(state, city, neighborhood);
        }

        if (state != null && city != null) {
            return viaCepClient.getAddressByCity(state, city);
        }

        if (state != null) {
            return viaCepClient.getAddressByState(state);
        }

        return viaCepClient.getAddress(zipCode);

    }

    private List<AddressResponse> processSuccessfulResponse(ResponseEntity<?> responseEntity) {

        if (responseEntity.getBody() instanceof List) {
            @SuppressWarnings("unchecked")
            List<AddressResponse> addressResponses = (List<AddressResponse>) responseEntity.getBody();
            addressResponses.forEach(this::saveAddressFromResponse);
            return addressResponses;
        }

        if (responseEntity.getBody() instanceof AddressResponse addressResponse) {
            saveAddressFromResponse(addressResponse);
            return List.of(addressResponse);
        }

        throw new ResourceNotFoundException("Resposta inválida da API externa.");

    }

    private void saveAddressFromResponse(AddressResponse addressResponse) {
        Address address = Address.builder()
                .zipCode(addressResponse.getZipCode())
                .street(addressResponse.getStreet())
                .complement(addressResponse.getComplement())
                .neighborhood(addressResponse.getNeighborhood())
                .city(addressResponse.getCity())
                .state(addressResponse.getState())
                .number(null)  // numero não é fornecido pela API externa
                .build();
        addressRepository.save(address);
    }

    private AddressResponse mapToResponse(Address address) {
        return AddressResponse.builder()
                .zipCode(address.getZipCode())
                .street(address.getStreet())
                .complement(address.getComplement())
                .neighborhood(address.getNeighborhood())
                .city(address.getCity())
                .state(address.getState())
                .number(address.getNumber() != null ? address.getNumber().toString() : null)
                .build();
    }
}
