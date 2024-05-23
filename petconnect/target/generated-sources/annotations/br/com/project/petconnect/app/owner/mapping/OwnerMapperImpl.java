package br.com.project.petconnect.app.owner.mapping;

import br.com.project.petconnect.app.owner.domain.dto.OwnerRequest;
import br.com.project.petconnect.app.owner.domain.dto.OwnerResponse;
import br.com.project.petconnect.app.owner.domain.entities.OwnerEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-22T14:37:17-0300",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class OwnerMapperImpl implements OwnerMapper {

    @Override
    public OwnerEntity toEntity(OwnerRequest ownerRequest) {
        if ( ownerRequest == null ) {
            return null;
        }

        OwnerEntity.OwnerEntityBuilder ownerEntity = OwnerEntity.builder();

        ownerEntity.name( ownerRequest.getName() );
        ownerEntity.email( ownerRequest.getEmail() );
        ownerEntity.cpf( ownerRequest.getCpf() );
        ownerEntity.phoneNumber( ownerRequest.getPhoneNumber() );
        ownerEntity.address( ownerRequest.getAddress() );

        return ownerEntity.build();
    }

    @Override
    public OwnerResponse toResponse(OwnerEntity ownerEntity) {
        if ( ownerEntity == null ) {
            return null;
        }

        OwnerResponse.OwnerResponseBuilder ownerResponse = OwnerResponse.builder();

        ownerResponse.id( ownerEntity.getId() );
        ownerResponse.name( ownerEntity.getName() );
        ownerResponse.cpf( ownerEntity.getCpf() );
        ownerResponse.phoneNumber( ownerEntity.getPhoneNumber() );
        ownerResponse.email( ownerEntity.getEmail() );
        ownerResponse.address( ownerEntity.getAddress() );
        ownerResponse.createdAt( ownerEntity.getCreatedAt() );
        ownerResponse.updatedAt( ownerEntity.getUpdatedAt() );

        return ownerResponse.build();
    }
}
