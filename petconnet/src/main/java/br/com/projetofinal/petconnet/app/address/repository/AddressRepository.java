package br.com.projetofinal.petconnet.address.repository;

import br.com.projetofinal.petconnet.address.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByCep(String cep);

    Address findByUfAndLocalidadeAndLogradouro(String uf, String localidade, String logradouro);

}