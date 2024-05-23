package br.com.project.petconnect.app.scheduling.mapping;

import br.com.project.petconnect.app.scheduling.domain.dto.SchedulingEntity;
import br.com.project.petconnect.app.scheduling.domain.entities.SchedulingRequest;
import br.com.project.petconnect.app.scheduling.domain.entities.SchedulingResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-22T14:37:17-0300",
    comments = "version: 1.6.0.Beta1, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class SchedulingMapperImpl implements SchedulingMapper {

    @Override
    public SchedulingEntity toEntity(SchedulingRequest serviceSchedulingRequest) {
        if ( serviceSchedulingRequest == null ) {
            return null;
        }

        SchedulingEntity.SchedulingEntityBuilder schedulingEntity = SchedulingEntity.builder();

        schedulingEntity.dataHora( serviceSchedulingRequest.getDataHora() );
        schedulingEntity.servicos( serviceSchedulingRequest.getServicos() );

        return schedulingEntity.build();
    }

    @Override
    public SchedulingResponse toResponse(SchedulingEntity schedulingEntity) {
        if ( schedulingEntity == null ) {
            return null;
        }

        SchedulingResponse.SchedulingResponseBuilder schedulingResponse = SchedulingResponse.builder();

        schedulingResponse.id( schedulingEntity.getId() );
        schedulingResponse.dataHora( schedulingEntity.getDataHora() );
        schedulingResponse.servicos( schedulingEntity.getServicos() );
        schedulingResponse.createdAt( schedulingEntity.getCreatedAt() );
        schedulingResponse.updatedAt( schedulingEntity.getUpdatedAt() );

        return schedulingResponse.build();
    }
}
