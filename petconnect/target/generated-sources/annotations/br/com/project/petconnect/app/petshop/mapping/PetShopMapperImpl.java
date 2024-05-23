package br.com.project.petconnect.app.petshop.mapping;

import br.com.project.petconnect.app.pet.domain.dto.PetResponse;
import br.com.project.petconnect.app.pet.domain.entities.PetEntity;
import br.com.project.petconnect.app.petshop.domain.dto.PetShopRequest;
import br.com.project.petconnect.app.petshop.domain.dto.PetShopResponse;
import br.com.project.petconnect.app.petshop.domain.entities.PetShopEntity;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-22T14:37:17-0300",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class PetShopMapperImpl implements PetShopMapper {

    @Override
    public PetShopResponse toResponse(PetShopEntity petShopEntity) {
        if ( petShopEntity == null ) {
            return null;
        }

        PetShopResponse.PetShopResponseBuilder petShopResponse = PetShopResponse.builder();

        petShopResponse.pets( petEntitySetToPetResponseSet( petShopEntity.getPets() ) );
        petShopResponse.id( petShopEntity.getId() );
        petShopResponse.businessName( petShopEntity.getBusinessName() );
        petShopResponse.cnpj( petShopEntity.getCnpj() );
        petShopResponse.phoneNumber( petShopEntity.getPhoneNumber() );
        petShopResponse.email( petShopEntity.getEmail() );
        petShopResponse.address( petShopEntity.getAddress() );
        petShopResponse.services( petShopEntity.getServices() );
        petShopResponse.businessHours( petShopEntity.getBusinessHours() );
        petShopResponse.createdAt( petShopEntity.getCreatedAt() );
        petShopResponse.updatedAt( petShopEntity.getUpdatedAt() );

        return petShopResponse.build();
    }

    @Override
    public PetShopEntity toEntity(PetShopRequest petShopRequest) {
        if ( petShopRequest == null ) {
            return null;
        }

        PetShopEntity.PetShopEntityBuilder petShopEntity = PetShopEntity.builder();

        petShopEntity.businessName( petShopRequest.getBusinessName() );
        petShopEntity.cnpj( petShopRequest.getCnpj() );
        petShopEntity.phoneNumber( petShopRequest.getPhoneNumber() );
        petShopEntity.email( petShopRequest.getEmail() );
        petShopEntity.address( petShopRequest.getAddress() );
        petShopEntity.services( petShopRequest.getServices() );
        petShopEntity.businessHours( petShopRequest.getBusinessHours() );

        return petShopEntity.build();
    }

    protected PetResponse petEntityToPetResponse(PetEntity petEntity) {
        if ( petEntity == null ) {
            return null;
        }

        PetResponse.PetResponseBuilder petResponse = PetResponse.builder();

        petResponse.id( petEntity.getId() );
        petResponse.name( petEntity.getName() );
        petResponse.breed( petEntity.getBreed() );
        petResponse.createdAt( petEntity.getCreatedAt() );
        petResponse.updatedAt( petEntity.getUpdatedAt() );

        return petResponse.build();
    }

    protected Set<PetResponse> petEntitySetToPetResponseSet(Set<PetEntity> set) {
        if ( set == null ) {
            return null;
        }

        Set<PetResponse> set1 = new LinkedHashSet<PetResponse>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( PetEntity petEntity : set ) {
            set1.add( petEntityToPetResponse( petEntity ) );
        }

        return set1;
    }
}
