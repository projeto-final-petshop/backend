package br.com.project.petconnect.app.petshop.service;

import br.com.project.petconnect.app.petshop.domain.entities.PetShopEntity;
import br.com.project.petconnect.app.petshop.domain.dto.PetShopRequest;
import br.com.project.petconnect.app.petshop.domain.dto.PetShopResponse;
import br.com.project.petconnect.app.petshop.mapping.PetShopMapper;
import br.com.project.petconnect.app.petshop.repository.PetShopRepository;
import br.com.project.petconnect.core.exceptions.petshop.CnpjAlreadyRegisteredException;
import br.com.project.petconnect.core.exceptions.petshop.PetShopNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PetShopService {

    private final PetShopRepository petShopRepository;
    private final PetShopMapper petShopMapper;

    public PetShopResponse createPetShop(PetShopRequest petShopRequest) {

        // verificar se o CNPJ já está cadastrado
        if (petShopRepository.existsByCnpj(petShopRequest.getCnpj())) {
            throw new CnpjAlreadyRegisteredException();
        }

        PetShopEntity petShopEntity = PetShopMapper.petShopMapper().toEntity(petShopRequest);
        PetShopEntity savedEntity = petShopRepository.save(petShopEntity);
        return PetShopMapper.petShopMapper().toResponse(savedEntity);

    }

    public PetShopResponse getPetShopById(Long id) {
        PetShopEntity petShopEntity = petShopRepository.findById(id)
                .orElseThrow(() -> new PetShopNotFoundException("PetShop não encontrado com ID: " + id));
        return PetShopMapper.petShopMapper().toResponse(petShopEntity);
    }

    public List<PetShopResponse> getPetShopsByNomeFantasia(String nomeFantasia) {
        List<PetShopEntity> petShops = petShopRepository.findByBusinessNameContainingIgnoreCase(nomeFantasia);
        return petShops.stream()
                .map(petShopMapper::toResponse)
                .collect(Collectors.toList());
    }

    public PetShopResponse getPetShopByCnpj(String cnpj) {
        PetShopEntity petShopEntity = petShopRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new PetShopNotFoundException("PetShop não encontrado."));
        return PetShopMapper.petShopMapper().toResponse(petShopEntity);
    }

    public PetShopResponse getPetShopByEmail(String email) {
        PetShopEntity petShopEntity = petShopRepository.findByEmail(email)
                .orElseThrow(() -> new PetShopNotFoundException("PetShop não encontrado."));
        return PetShopMapper.petShopMapper().toResponse(petShopEntity);
    }

}
