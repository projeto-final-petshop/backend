package br.com.project.petconnect.app.pet.repository;

import br.com.project.petconnect.app.pet.domain.entities.PetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<PetEntity, Long> {

//    List<PetEntity> findByTutor(TutorEntity tutor);

//    List<PetEntity> findByNomeContainingIgnoreCase(String nome);
//
//    List<PetEntity> findByEspecie(String especie);
//
//    List<PetEntity> findByRaca(String raca);
//
//    List<PetEntity> findBySexo(String sexo);
//
//    List<PetEntity> findByPorte(String porte);
//
//    List<PetEntity> findByCastrado(Boolean castrado);
//
//    List<PetEntity> findByVacinado(Boolean vacinado);
//
//    List<PetEntity> findByIdadeLessThanEqual(Integer idade);
//
//    List<PetEntity> findByPetShopId(Long petShopId);
//
//    List<PetEntity> findByTutorId(Long tutorId);

}
