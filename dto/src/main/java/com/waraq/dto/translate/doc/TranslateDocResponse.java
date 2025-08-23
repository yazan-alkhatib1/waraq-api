package com.waraq.dto.translate.doc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waraq.dto.ResponseDTO;
import com.waraq.repository.enums.TranslateRequestStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TranslateDocResponse implements ResponseDTO {
    private Long id;
    private LocalDateTime creationDate;
    private Boolean isEnabled;

    private Long requestId;
    private TranslateRequestStatus status;

    private Long documentId;
    private String documentUrl;

    private Long templateId;
    private String templateUrl;

    private Long translatedDocumentId;
    private String translatedDocumentUrl;

    private Long finalTranslatedDocumentId;
    private String finalTranslatedDocumentUrl;
}
