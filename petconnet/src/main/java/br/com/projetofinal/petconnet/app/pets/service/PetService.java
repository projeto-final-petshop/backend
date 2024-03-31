package br.com.projetofinal.petconnet.app.pets.service;

import br.com.projetofinal.petconnet.app.pets.repository.PetRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class PetService {

    private final PetRepository petRepository;

}
