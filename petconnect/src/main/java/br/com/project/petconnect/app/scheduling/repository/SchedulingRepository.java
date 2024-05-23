package br.com.project.petconnect.app.scheduling.repository;

import br.com.project.petconnect.app.scheduling.domain.dto.SchedulingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SchedulingRepository extends JpaRepository<SchedulingEntity, Long> {

    List<SchedulingEntity> findByPetShopId(Long petShopId);

}
