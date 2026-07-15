package com.aryan.jobportal.employer.mapper;

import com.aryan.jobportal.employer.dto.request.PatchEmployerReq;
import com.aryan.jobportal.employer.entities.Employer;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UpdateEmployerMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(PatchEmployerReq patchEmployerReq, @MappingTarget Employer employer);
}
