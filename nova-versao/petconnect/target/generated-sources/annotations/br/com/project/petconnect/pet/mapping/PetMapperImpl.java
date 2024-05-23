package br.com.project.petconnect.pet.mapping;

import br.com.project.petconnect.pet.dto.PetRequest;
import br.com.project.petconnect.pet.dto.PetResponse;
import br.com.project.petconnect.pet.entities.Pet;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-23T13:01:42-0300",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class PetMapperImpl implements PetMapper {

    @Override
    public Pet toEntity(PetRequest petRequest) {
        if ( petRequest == null ) {
            return null;
        }

        Pet.PetBuilder pet = Pet.builder();

        pet.name( petRequest.getName() );
        pet.age( petRequest.getAge() );
        pet.color( petRequest.getColor() );
        pet.breed( petRequest.getBreed() );
        pet.animalType( petRequest.getAnimalType() );
        pet.birthdate( petRequest.getBirthdate() );

        return pet.build();
    }

    @Override
    public PetResponse toResponse(Pet pet) {
        if ( pet == null ) {
            return null;
        }

        PetResponse.PetResponseBuilder petResponse = PetResponse.builder();

        petResponse.id( pet.getId() );
        petResponse.name( pet.getName() );
        petResponse.age( pet.getAge() );
        petResponse.color( pet.getColor() );
        petResponse.breed( pet.getBreed() );
        petResponse.animalType( pet.getAnimalType() );
        petResponse.birthdate( pet.getBirthdate() );
        petResponse.createdAt( pet.getCreatedAt() );

        return petResponse.build();
    }

    @Override
    public List<PetResponse> toResponseList(List<Pet> pet) {
        if ( pet == null ) {
            return null;
        }

        List<PetResponse> list = new ArrayList<PetResponse>( pet.size() );
        for ( Pet pet1 : pet ) {
            list.add( toResponse( pet1 ) );
        }

        return list;
    }
}
