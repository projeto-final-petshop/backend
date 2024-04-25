package br.com.projetofinal.petconnet.app.address.mapper;

import br.com.projetofinal.petconnet.app.address.dto.AddressRequest;
import br.com.projetofinal.petconnet.app.address.dto.AddressResponse;
import br.com.projetofinal.petconnet.app.address.entity.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    static AddressMapper addressMapper() {
        return INSTANCE;
    }

    @Mapping(source = "user.id", target = "userId")
    AddressResponse toAddressResponse(Address address);

    Address requestToAddress(AddressRequest request);

    Address responseToAddress(AddressResponse response);

    List<AddressResponse> toAddressListResponse(List<Address> addressList);

}
