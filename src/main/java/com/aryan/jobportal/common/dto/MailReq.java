package com.aryan.jobportal.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailReq {

    private String to;

    private String subject;

    private String htmlContent;
}
