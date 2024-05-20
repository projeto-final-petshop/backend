package br.com.project.petconnect.app.owner.service;

import br.com.project.petconnect.app.owner.domain.entities.OwnerEntity;
import br.com.project.petconnect.app.owner.domain.dto.OwnerRequest;
import br.com.project.petconnect.app.owner.domain.dto.OwnerResponse;
import br.com.project.petconnect.app.owner.mapping.OwnerMapper;
import br.com.project.petconnect.app.owner.repository.OwnerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;

    @Transactional
    public OwnerResponse createOwner(OwnerRequest request) {
        OwnerEntity owner = OwnerMapper.ownerMapper().toEntity(request);
        ownerRepository.save(owner);
        return OwnerMapper.ownerMapper().toResponse(owner);
    }

}
