package br.com.project.petconnect.app.pet.mapping;

import br.com.project.petconnect.app.pet.domain.dto.PetRequest;
import br.com.project.petconnect.app.pet.domain.dto.PetResponse;
import br.com.project.petconnect.app.pet.domain.entities.PetEntity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-22T14:37:17-0300",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class PetMapperImpl implements PetMapper {

    @Override
    public PetEntity toPetEntity(PetRequest petRequest) {
        if ( petRequest == null ) {
            return null;
        }

        PetEntity.PetEntityBuilder petEntity = PetEntity.builder();

        petEntity.name( petRequest.getName() );
        petEntity.species( petRequest.getSpecies() );
        petEntity.breed( petRequest.getBreed() );
        petEntity.sex( petRequest.getSex() );
        petEntity.age( petRequest.getAge() );
        petEntity.size( petRequest.getSize() );
        petEntity.neutered( petRequest.getNeutered() );
        petEntity.vaccination( petRequest.getVaccination() );
        byte[] photo = petRequest.getPhoto();
        if ( photo != null ) {
            petEntity.photo( Arrays.copyOf( photo, photo.length ) );
        }
        petEntity.description( petRequest.getDescription() );

        return petEntity.build();
    }

    @Override
    public PetResponse toPetResponse(PetEntity pet) {
        if ( pet == null ) {
            return null;
        }

        PetResponse.PetResponseBuilder petResponse = PetResponse.builder();

        petResponse.id( pet.getId() );
        petResponse.name( pet.getName() );
        petResponse.breed( pet.getBreed() );
        petResponse.createdAt( pet.getCreatedAt() );
        petResponse.updatedAt( pet.getUpdatedAt() );

        return petResponse.build();
    }

    @Override
    public List<PetResponse> toPetResponseList(List<PetEntity> pet) {
        if ( pet == null ) {
            return null;
        }

        List<PetResponse> list = new ArrayList<PetResponse>( pet.size() );
        for ( PetEntity petEntity : pet ) {
            list.add( toPetResponse( petEntity ) );
        }

        return list;
    }
}
