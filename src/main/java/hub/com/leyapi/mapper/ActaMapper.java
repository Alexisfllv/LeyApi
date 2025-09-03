package hub.com.leyapi.mapper;

import hub.com.leyapi.dto.actas.ActaDTOResponse;
import hub.com.leyapi.model.Actas;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ChiveMapper.class})
public interface ActaMapper {
    // entity


    // response
//    @Mapping(source = "idActa", target = "idActa")
//    @Mapping(source = "titulo", target = "titulo")
//    @Mapping(source = "chives", target = "chives")
    ActaDTOResponse toActaDTOResponse(Actas actas);
}
