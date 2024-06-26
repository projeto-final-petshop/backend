package br.com.finalproject.petconnect.address.services;

import br.com.finalproject.petconnect.address.domain.dto.AddressRequest;
import br.com.finalproject.petconnect.address.domain.dto.AddressResponse;
import br.com.finalproject.petconnect.address.domain.entities.Address;
import br.com.finalproject.petconnect.address.http.ViaCepClient;
import br.com.finalproject.petconnect.address.repositories.AddressRepository;
import br.com.finalproject.petconnect.exceptions.runtimes.notfound.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AddressService {

    private final ViaCepClient viaCepClient;
    private final AddressRepository addressRepository;

    public AddressResponse createAddress(AddressRequest addressRequest) {

        ResponseEntity<AddressResponse> response = viaCepClient.getAddress(addressRequest.getZipCode());

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            var addressResponse = response.getBody();

            Address address = Address.builder()
                    .cep(addressResponse.getZipCode())
                    .logradouro(addressResponse.getPublicPlace())
                    .complemento(addressResponse.getComplement())
                    .bairro(addressResponse.getNeighborhood())
                    .cidade(addressResponse.getCity())
                    .uf(addressResponse.getState())
                    .numero(Long.parseLong(addressRequest.getNumber()))
                    .build();

            addressRepository.save(address);

            return AddressResponse.builder()
                    .zipCode(address.getCep())
                    .publicPlace(address.getLogradouro())
                    .complement(address.getComplemento())
                    .neighborhood(address.getBairro())
                    .city(address.getCidade())
                    .state(address.getUf())
                    .build();
        }

        throw new IllegalArgumentException("CEP inválido ou não encontrado.");

    }

    public AddressResponse getAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
        return mapToResponse(address);
    }

    public List<AddressResponse> listAllAddresses() {
        List<Address> addresses = addressRepository.findAll();
        return addresses.stream()
                .map(this::mapToResponse)
                .toList();
    }

    public AddressResponse updateAddress(Long id, AddressRequest addressRequest) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado"));

        ResponseEntity<AddressResponse> response = viaCepClient.getAddress(addressRequest.getZipCode());

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            var addressResponse = response.getBody();

            address.setCep(addressResponse.getZipCode());
            address.setLogradouro(addressResponse.getPublicPlace());
            address.setComplemento(addressResponse.getComplement());
            address.setBairro(addressResponse.getNeighborhood());
            address.setCidade(addressResponse.getCity());
            address.setUf(addressResponse.getState());
            address.setNumero(Long.parseLong(addressRequest.getNumber()));

            addressRepository.save(address);

            return mapToResponse(address);
        }
        throw new ResourceNotFoundException("CEP inválido ou não encontrado.");
    }

    public void deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            throw new ResourceNotFoundException("Endereço não encontrado");
        }
        addressRepository.deleteById(id);
    }

    private AddressResponse mapToResponse(Address address) {
        return AddressResponse.builder()
                .zipCode(address.getCep())
                .publicPlace(address.getLogradouro())
                .complement(address.getComplemento())
                .neighborhood(address.getBairro())
                .city(address.getCidade())
                .state(address.getUf())
                .build();
    }

}
