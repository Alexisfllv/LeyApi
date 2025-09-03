package hub.com.leyapi.mapper;

import hub.com.leyapi.dto.chive.ChiveDTOResponse;
import hub.com.leyapi.model.Chives;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChiveMapper {
    // response
//    @Mapping(source = "idChives", target = "idChives")
//    @Mapping(source = "nombre", target = "nombre")
//    @Mapping(source = "url", target = "url")
    ChiveDTOResponse toChiveDTOResponse(Chives chives);
}
