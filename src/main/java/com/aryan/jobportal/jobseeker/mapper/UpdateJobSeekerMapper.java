package com.aryan.jobportal.jobseeker.mapper;

import com.aryan.jobportal.jobseeker.entities.JobSeeker;
import com.aryan.jobportal.jobseeker.dto.request.PatchJobSeekerReq;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UpdateJobSeekerMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(PatchJobSeekerReq patchJobSeekerReq, @MappingTarget JobSeeker jobSeeker);
}
