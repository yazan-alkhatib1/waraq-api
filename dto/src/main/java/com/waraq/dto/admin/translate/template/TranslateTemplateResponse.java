package com.waraq.dto.admin.translate.template;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waraq.dto.ResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TranslateTemplateResponse implements ResponseDTO {
    private Long id;
    private LocalDateTime creationDate;
    private Boolean isEnabled;
    private Long documentId;
    private String documentUrl;
}
