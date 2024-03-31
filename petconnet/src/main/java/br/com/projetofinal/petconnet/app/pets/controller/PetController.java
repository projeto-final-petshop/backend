package br.com.projetofinal.petconnet.app.pets.controller;

import br.com.projetofinal.petconnet.app.pets.service.PetService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pets")
@AllArgsConstructor
public class PetController {

    private final PetService petService;


}
