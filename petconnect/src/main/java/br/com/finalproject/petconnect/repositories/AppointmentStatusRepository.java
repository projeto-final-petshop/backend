package br.com.finalproject.petconnect.repositories;

import br.com.finalproject.petconnect.domain.entities.help.AppointmentStatusEtities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentStatusRepository extends JpaRepository<AppointmentStatusEtities, Long> {
}
