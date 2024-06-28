package br.com.finalproject.petconnect.user.controllers;

import br.com.finalproject.petconnect.exceptions.runtimes.badrequest.InvalidRequestException;
import br.com.finalproject.petconnect.exceptions.runtimes.generics.ResourceNotFoundException;
import br.com.finalproject.petconnect.exceptions.runtimes.service.ServiceException;
import br.com.finalproject.petconnect.pets.dto.response.PetResponse;
import br.com.finalproject.petconnect.roles.dto.RoleResponse;
import br.com.finalproject.petconnect.user.dto.request.UpdateUserRequest;
import br.com.finalproject.petconnect.user.dto.response.UserResponse;
import br.com.finalproject.petconnect.user.entities.User;
import br.com.finalproject.petconnect.user.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponse> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        UserResponse userDTO = convertToDto(currentUser);
        return ResponseEntity.ok(userDTO);
    }

    private UserResponse convertToDto(User user) {

        var response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setCpf(user.getCpf());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setAddress(user.getAddress());
        response.setActive(user.getActive());

        List<RoleResponse> roleResponseList = user.getRoles().stream().map(role -> {
            var roleResponse = new RoleResponse();
            roleResponse.setId(role.getId());
            roleResponse.setName(role.getName());
            roleResponse.setDescription(role.getDescription());
            return roleResponse;
        }).toList();

        List<PetResponse> petResponseList = user.getPets().stream().map(pet -> {
            var petResponse = new PetResponse();
            petResponse.setId(pet.getId());
            petResponse.setName(pet.getName());
            petResponse.setColor(pet.getColor());
            petResponse.setBreed(pet.getBreed());
            petResponse.setAge(pet.getAge());
            petResponse.setPetType(pet.getPetType());
            petResponse.setBirthdate(pet.getBirthdate());
            return petResponse;
        }).toList();

        response.setPets(petResponseList);
        response.setRoles(roleResponseList);

        return response;

    }

    @PutMapping(value = "/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> updateUser(@RequestBody @Valid UpdateUserRequest request)
            throws InvalidRequestException, ResourceNotFoundException, ServiceException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        try {
            userService.updateUser(currentPrincipalName, request);
            log.info("Usuário atualizado com sucesso: {}", currentPrincipalName);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário atualizado com sucesso!");
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo inválido.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        } catch (ServiceException e) {
            log.error("Erro ao atualizar usuário", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar usuário.");
        }
    }

    @DeleteMapping(value = "/delete")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deactivateUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        try {
            userService.deactivateUser(currentPrincipalName);
            log.info("Usuário desativado com sucesso: {}", currentPrincipalName);
            return ResponseEntity.status(HttpStatus.OK).body("Usuário desativado com sucesso!");
            // return ResponseEntity.ok("Usuário desativado com sucesso!");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ServiceException e) {
            log.error("Erro ao desativar usuário", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
