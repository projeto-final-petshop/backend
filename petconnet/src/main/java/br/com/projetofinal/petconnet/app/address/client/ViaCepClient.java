package br.com.projetofinal.petconnet.app.address.client;

import br.com.projetofinal.petconnet.app.address.dto.AddressResponse;
import feign.FeignException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Interface Feign para integração com a API ViaCep.
 * <p>
 * Essa interface define métodos para consulta de endereços através do CEP ou por combinação de UF, localidade e
 * logradouro.
 *
 * @author juliane.maran
 */
@FeignClient(name = "${feign-client.name}", url = "${feign-client.url}")
public interface ViaCepClient {

    /**
     * Recupera um endereço a partir do CEP informado.
     *
     * @param cep
     *         CEP a ser consultado (obrigatório).
     *
     * @return O endereço encontrado, caso exista.
     *
     * @throws FeignException
     *         Caso ocorra algum erro durante a consulta à API.
     */
    @GetMapping(value = "/{cep}/json",
            produces = MediaType.APPLICATION_JSON_VALUE)
    AddressResponse getAddressByCep(@PathVariable(name = "cep") String cep) throws FeignException;

    /**
     * Recupera um endereço a partir da combinação de UF, localidade e logradouro.
     *
     * @param uf
     *         Sigla da Unidade Federativa (opcional).
     * @param localidade
     *         Nome da localidade (opcional).
     * @param logradouro
     *         Nome do logradouro (rua, avenida, etc.) (opcional).
     *
     * @return O endereço encontrado, caso exista.
     *
     * @throws FeignException
     *         Caso ocorra algum erro durante a consulta à API.
     */
    @GetMapping(value = "/{uf}/{localidade}/{logradouro}/json/")
    AddressResponse findAddressByUfLocalidadeLogradouro(
            @PathVariable(name = "uf", required = false) String uf,
            @PathVariable(name = "localidade", required = false) String localidade,
            @PathVariable(name = "logradouro", required = false) String logradouro)
            throws FeignException;

}
