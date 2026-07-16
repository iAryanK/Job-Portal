package com.aryan.jobportal.jobs.mapper;

import com.aryan.jobportal.jobs.dto.request.PatchJobReq;
import com.aryan.jobportal.jobs.entities.Job;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UpdateJobMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(PatchJobReq patchJobReq, @MappingTarget Job job);
}
