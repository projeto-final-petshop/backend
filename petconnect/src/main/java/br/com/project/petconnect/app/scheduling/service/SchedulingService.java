package br.com.project.petconnect.app.scheduling.service;

import br.com.project.petconnect.app.pet.domain.entities.PetEntity;
import br.com.project.petconnect.app.pet.repository.PetRepository;
import br.com.project.petconnect.app.petshop.domain.entities.PetShopEntity;
import br.com.project.petconnect.app.petshop.repository.PetShopRepository;
import br.com.project.petconnect.app.scheduling.domain.dto.SchedulingEntity;
import br.com.project.petconnect.app.scheduling.domain.entities.SchedulingRequest;
import br.com.project.petconnect.app.scheduling.domain.entities.SchedulingResponse;
import br.com.project.petconnect.app.scheduling.mapping.SchedulingMapper;
import br.com.project.petconnect.app.scheduling.repository.SchedulingRepository;
import br.com.project.petconnect.core.exceptions.pet.PetNotFoundException;
import br.com.project.petconnect.core.exceptions.petshop.PetShopNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchedulingService {

    private final SchedulingRepository schedulingRepository;
    private final PetShopRepository petShopRepository;
    private final PetRepository petRepository;
    private final SchedulingMapper schedulingMapper;

    public SchedulingResponse createAgendamento(SchedulingRequest schedulingRequest) {
        PetShopEntity petShop = petShopRepository.findById(schedulingRequest.getPetShopId())
                .orElseThrow(() -> new PetShopNotFoundException("PetShop não encontrado."));
        PetEntity pet = petRepository.findById(schedulingRequest.getPetId())
                .orElseThrow(() -> new PetNotFoundException("Pet não encontrado."));

        SchedulingEntity agendamentoEntity = SchedulingMapper.serviceScheulingMapper().toEntity(schedulingRequest);
        agendamentoEntity.setPetShop(petShop);
        agendamentoEntity.setPet(pet);

        SchedulingEntity savedEntity = schedulingRepository.save(agendamentoEntity);
        return SchedulingMapper.serviceScheulingMapper().toResponse(savedEntity);
    }

    public List<SchedulingResponse> getAgendamentosByPetShopId(Long petShopId) {
        List<SchedulingEntity> agendamentos = schedulingRepository.findByPetShopId(petShopId);
        return agendamentos.stream()
                .map(schedulingMapper::toResponse)
                .collect(Collectors.toList());
    }

}
