package br.com.projetofinal.petconnet.app.address.helper;

import br.com.projetofinal.petconnet.app.address.dto.AddressRequest;
import br.com.projetofinal.petconnet.app.address.dto.AddressResponse;
import br.com.projetofinal.petconnet.app.address.dto.UpdateAddressRequest;
import br.com.projetofinal.petconnet.app.address.entity.Address;
import br.com.projetofinal.petconnet.app.address.repository.AddressRepository;
import br.com.projetofinal.petconnet.core.exceptions.errors.address.AddressNotFoundException;
import br.com.projetofinal.petconnet.core.exceptions.errors.address.AddressValidationException;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@AllArgsConstructor
public class AddressHelper {

    /**
     * Repositório de acesso a dados de Endereços.
     */
    private final AddressRepository addressRepository;

    /**
     * Atualiza os campos de endereço com base na resposta obtida via CEP.
     *
     * @param updateRequest
     *         Dados atualizados do endereço.
     * @param address
     *         Endereço a ser atualizado.
     *
     * @throws AddressValidationException
     *         Caso o CEP esteja inválido.
     */
    public static void updateAddressFieldsFromCepResponse(UpdateAddressRequest updateRequest,
                                                          Address address) {

        if (!StringUtils.isEmpty(updateRequest.getCep())) {
            validateCep(updateRequest.getCep());
            address.setCep(updateRequest.getCep());
        }

        if (!StringUtils.isEmpty(updateRequest.getNumero())) {
            address.setNumero(updateRequest.getNumero());
        }

        if (!StringUtils.isEmpty(updateRequest.getLogradouro())) {
            address.setLogradouro(updateRequest.getLogradouro());
        }

        if (!StringUtils.isEmpty(updateRequest.getComplemento())) {
            address.setComplemento(updateRequest.getComplemento());
        }

        if (!StringUtils.isEmpty(updateRequest.getBairro())) {
            address.setBairro(updateRequest.getBairro());
        }

        if (!StringUtils.isEmpty(updateRequest.getLocalidade())) {
            address.setLocalidade(updateRequest.getLocalidade());
        }

        if (!StringUtils.isEmpty(updateRequest.getUf())) {
            address.setUf(updateRequest.getUf());
        }
    }

    /**
     * Busca um endereço pelo identificador.
     *
     * @param addressId
     *         Identificador do endereço.
     *
     * @return O endereço encontrado, caso exista.
     *
     * @throws AddressNotFoundException
     *         Caso o endereço não seja encontrado.
     */
    public Address findAddressByid(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(AddressNotFoundException::new);
    }

    /**
     * Constrói um objeto de endereço a partir dos dados de requisição e da resposta da API de CEP.
     *
     * @param request
     *         Dados da requisição de criação ou atualização de endereço.
     * @param viaCepResponse
     *         Resposta obtida da API de CEP.
     *
     * @return O objeto de endereço construído.
     */
    public static Address buildAddressFromRequestAndResponse(AddressRequest request,
                                                             AddressResponse viaCepResponse) {

        Address address = Address.builder()
                .cep(request.getCep())
                .numero(request.getNumero())
                .build();

        address.setLogradouro(viaCepResponse.getLogradouro());
        address.setComplemento(viaCepResponse.getComplemento());
        address.setBairro(viaCepResponse.getBairro());
        address.setLocalidade(viaCepResponse.getLocalidade());
        address.setUf(viaCepResponse.getUf());
        return address;
    }

    /**
     * Trata exceções lançadas pelo cliente Feign durante a busca de CEP.
     *
     * @param ex
     *         Exceção lançada pelo cliente Feign.
     *
     * @return Uma exceção {@link AddressNotFoundException} caso o CEP não seja encontrado. <br> Uma exceção
     * {@link AddressValidationException} caso ocorra outro erro.
     */
    public AddressValidationException handleExternalServiceException(FeignException ex) {
        // "CEP não encontrado"
        if (ex.status() == HttpStatus.NOT_FOUND.value()) {
            throw new AddressNotFoundException();
        }
        // "Erro ao buscar endereço via CEP"
        return new AddressValidationException();
    }

    /**
     * Valida o formato do CEP.
     *
     * @param cep
     *         CEP a ser validado.
     *
     * @throws AddressValidationException
     *         Caso o CEP esteja inválido.
     */
    public static void validateCep(String cep) throws AddressValidationException {
        // "CEP é obrigatório"
        if (StringUtils.isEmpty(cep)) {
            throw new AddressValidationException();
        }
        // "CEP inválido" "^[0-9]{8}$"
        if (!cep.matches("^\\d{8}$")) {
            throw new AddressValidationException();
        }
    }


}
