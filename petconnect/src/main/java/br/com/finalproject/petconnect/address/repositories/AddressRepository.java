package br.com.finalproject.petconnect.address.repositories;

import br.com.finalproject.petconnect.address.domain.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
