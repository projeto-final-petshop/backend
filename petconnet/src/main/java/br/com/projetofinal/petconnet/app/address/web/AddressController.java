package br.com.projetofinal.petconnet.app.address.web;

import br.com.projetofinal.petconnet.app.address.dto.AddressRequest;
import br.com.projetofinal.petconnet.app.address.dto.AddressResponse;
import br.com.projetofinal.petconnet.app.address.dto.UpdateAddressRequest;
import br.com.projetofinal.petconnet.app.address.service.AddressService;
import br.com.projetofinal.petconnet.core.exceptions.errors.address.AddressNotFoundException;
import br.com.projetofinal.petconnet.core.exceptions.errors.address.AddressValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciamento de endereços.
 * <p>
 * Essa classe expõe endpoints para consulta (READ), criação (CREATE), listagem (READ), atualização (UPDATE) e exclusão
 * (DELETE) de endereços.
 *
 * @author juliane.maran
 */
@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    /**
     * Serviço de aplicação para operações relacionadas a endereços.
     */
    private final AddressService addressService;

    /**
     * Recupera um endereço a partir do CEP informado.
     *
     * @param cep
     *         CEP a ser consultado (obrigatório).
     *
     * @return O recurso contendo o endereço encontrado (status: 201 Created) ou um erro caso o CEP não seja encontrado
     * (por exemplo, status: 404 Not Found).
     *
     * @throws AddressNotFoundException
     *         Caso o CEP não seja encontrado.
     * @throws AddressValidationException
     *         Caso o CEP esteja inválido.
     */
    @GetMapping("/{cep}")
    public ResponseEntity<AddressResponse> findAddressByCep(@PathVariable(name = "cep") String cep)
            throws AddressNotFoundException, AddressValidationException {
        AddressResponse response = addressService.findAddressByCep(cep);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Cria um novo endereço.
     *
     * @param request
     *         Dados do endereço a ser criado (corpo da requisição).
     *
     * @return O recurso contendo o endereço criado (status: 201 Created) ou um erro caso o CEP esteja inválido (por
     * exemplo, status: 400 Bad Request).
     *
     * @throws AddressNotFoundException
     *         Caso o CEP não seja encontrado.
     * @throws AddressValidationException
     *         Caso o CEP esteja inválido.
     */
    @PostMapping("/register")
    public ResponseEntity<AddressResponse> createAddress(@RequestBody AddressRequest request)
            throws AddressNotFoundException, AddressValidationException {
        AddressResponse response = addressService.createAddress(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Recupera uma lista com todos os endereços cadastrados.
     *
     * @return O recurso contendo a lista de endereços (status: 200 OK).
     */
    @GetMapping("/list")
    public ResponseEntity<List<AddressResponse>> listAddress() {
        List<AddressResponse> responses = addressService.findAll();
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    /**
     * Atualiza um endereço existente.
     *
     * @param id
     *         Identificador do endereço a ser atualizado (caminho da requisição).
     * @param updateRequest
     *         Dados atualizados do endereço (corpo da requisição).
     *
     * @return O recurso contendo o endereço atualizado (status: 204 No Content) ou um erro caso o endereço não seja
     * encontrado (por exemplo, status: 404 Not Found).
     *
     * @throws AddressNotFoundException
     *         Caso o endereço não seja encontrado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable(name = "id") Long id,
                                                         @RequestBody UpdateAddressRequest updateRequest)
            throws AddressNotFoundException {
        AddressResponse response = addressService.updateAddress(id, updateRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }

    /**
     * Exclui um endereço.
     *
     * @param id
     *         Identificador do endereço a ser atualizado (caminho da requisição).
     *
     * @return No caso de sucesso, retorna um corpo vazio com status: 204 No Content. Caso o endereço não seja
     * encontrado, retorna um erro (por exemplo, status: 404 Not Found).
     *
     * @throws AddressNotFoundException
     *         Caso o endereço não seja encontrado.
     */

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable(name = "id") Long id)
            throws AddressNotFoundException {
        addressService.deleteAddress(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
