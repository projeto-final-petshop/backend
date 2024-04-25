package br.com.projetofinal.petconnet.app.address.service;

import br.com.projetofinal.petconnet.app.address.client.ViaCepClient;
import br.com.projetofinal.petconnet.app.address.dto.AddressRequest;
import br.com.projetofinal.petconnet.app.address.dto.AddressResponse;
import br.com.projetofinal.petconnet.app.address.dto.UpdateAddressRequest;
import br.com.projetofinal.petconnet.app.address.entity.Address;
import br.com.projetofinal.petconnet.app.address.helper.AddressHelper;
import br.com.projetofinal.petconnet.app.address.mapper.AddressMapper;
import br.com.projetofinal.petconnet.app.address.repository.AddressRepository;
import br.com.projetofinal.petconnet.app.users.entity.User;
import br.com.projetofinal.petconnet.app.users.repository.UserRepository;
import br.com.projetofinal.petconnet.core.exceptions.errors.address.AddressNotFoundException;
import br.com.projetofinal.petconnet.core.exceptions.errors.address.AddressValidationException;
import br.com.projetofinal.petconnet.core.exceptions.errors.users.newusers.UsernameNotFoundException;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    /**
     * Utilitário para operações relacionadas a endereços.
     */
    private final AddressHelper addressUtil;

    /**
     * Repositório de acesso a dados de Endereços.
     */
    private final AddressRepository addressRepository;

    /**
     * Cliente Feign para integração com a API de CEP.
     */
    private final ViaCepClient viaCepClient;

    private final UserRepository userRepository;

    /**
     * Busca um endereço pelo CEP.
     *
     * @param cep
     *         CEP a ser consultado.
     *
     * @return O endereço encontrado, caso exista.
     *
     * @throws AddressNotFoundException
     *         Caso o CEP não seja encontrado.
     * @throws AddressValidationException
     *         Caso o CEP esteja inválido.
     */
    @Transactional(readOnly = true)
    public AddressResponse findAddressByCep(String cep)
            throws AddressNotFoundException, AddressValidationException {
        AddressHelper.validateCep(cep);
        AddressResponse response;
        try {
            response = viaCepClient.getAddressByCep(cep);
        } catch (FeignException ex) {
            throw addressUtil.handleExternalServiceException(ex);
        }
        return response;
    }

    /**
     * Recupera todos os endereços cadastrados.
     *
     * @return Uma lista com os endereços encontrados.
     */
    @Transactional(readOnly = true)
    public List<AddressResponse> findAll() {
        List<Address> addressList = addressRepository.findAll();
        return AddressMapper.addressMapper().toAddressListResponse(addressList);
    }

    /**
     * Exclui um endereço pelo identificador.
     *
     * @param addressId
     *         Identificador do endereço a ser excluído.
     *
     * @throws AddressNotFoundException
     *         Caso o endereço não seja encontrado.
     */
    @Transactional
    public void deleteAddress(Long addressId) throws AddressNotFoundException {
        if (!addressRepository.existsById(addressId)) {
            throw new AddressNotFoundException();
        }
        addressRepository.deleteById(addressId);
    }

    /**
     * Cria um novo endereço.
     *
     * @param request
     *         Dados do endereço a ser criado.
     *
     * @return O endereço criado.
     *
     * @throws AddressNotFoundException
     *         Caso o CEP não seja encontrado.
     * @throws AddressValidationException
     *         Caso o CEP esteja inválido.
     */
    @Transactional
    public AddressResponse createAddress(AddressRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UsernameNotFoundException::new);

        AddressHelper.validateCep(request.getCep());
        AddressResponse viaCepResponse;
        try {
            viaCepResponse = viaCepClient.getAddressByCep(request.getCep());
        } catch (FeignException ex) {
            throw addressUtil.handleExternalServiceException(ex);
        }
        Address address = AddressHelper.buildAddressFromRequestAndResponse(request, viaCepResponse);

        address.setUser(user);
        address = addressRepository.save(address);

        return AddressMapper.addressMapper().toAddressResponse(address);

    }

    /**
     * Atualiza um endereço existente.
     *
     * @param addressId
     *         Identificador do endereço a ser atualizado.
     * @param updateRequest
     *         Dados atualizados do endereço.
     *
     * @return O endereço atualizado.
     *
     * @throws AddressNotFoundException
     *         Caso o endereço não seja encontrado.
     */
    @Transactional
    public AddressResponse updateAddress(Long addressId, UpdateAddressRequest updateRequest) {
        Address address = addressUtil.findAddressByid(addressId);
        AddressHelper.updateAddressFieldsFromCepResponse(updateRequest, address);
        address = addressRepository.save(address);
        return AddressMapper.addressMapper().toAddressResponse(address);
    }

}
