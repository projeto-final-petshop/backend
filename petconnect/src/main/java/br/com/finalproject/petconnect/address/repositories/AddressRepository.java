package br.com.finalproject.petconnect.address.repositories;

import br.com.finalproject.petconnect.address.domain.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByZipCode(String zipCode);

    @Query("SELECT a FROM Address a WHERE " +
            "(:state IS NULL OR a.state = :state) AND " +
            "(:city IS NULL OR a.city = :city) AND " +
            "(:neighborhood IS NULL OR a.neighborhood = :neighborhood) AND " +
            "(:zipCode IS NULL OR a.zipCode = :zipCode)")
    List<Address> findByCriteria(@Param("state") String state,
                                 @Param("city") String city,
                                 @Param("neighborhood") String neighborhood,
                                 @Param("zipCode") String zipCode);

}
