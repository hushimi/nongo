package bushigen.nongo.entitymapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import bushigen.nongo.dto.request.SoftwareEngineerCreateRequest;
import bushigen.nongo.dto.request.SoftwareEngineerUpdateRequest;
import bushigen.nongo.dto.response.SoftwareEngineerResponse;
import bushigen.nongo.model.SoftwareEngineer;

@Mapper(componentModel = "spring")
public interface SoftwareEngineerObjMapper {
  SoftwareEngineerObjMapper INSTANCE = Mappers.getMapper(SoftwareEngineerObjMapper.class);

  @Mappings({
    @Mapping(target = "id", ignore = true),
    @Mapping(target = "createdAt", ignore = true),
    @Mapping(target = "updatedAt", ignore = true)
  })
  SoftwareEngineer toModel(SoftwareEngineerCreateRequest request);

  @Mappings({
    @Mapping(target = "createdAt", ignore = true),
    @Mapping(target = "updatedAt", ignore = true)
  })
  SoftwareEngineer toModel(SoftwareEngineerUpdateRequest request);

  SoftwareEngineerResponse toResponse(SoftwareEngineer entity);

  List<SoftwareEngineerResponse> toResponseList(List<SoftwareEngineer> entities);
}
