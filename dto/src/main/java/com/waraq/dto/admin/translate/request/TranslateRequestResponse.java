package com.waraq.dto.admin.translate.request;

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
public class TranslateRequestResponse implements ResponseDTO {
    private Long id;
    private LocalDateTime creationDate;
    private Boolean isEnabled;

    private Long clientId;
    private Long translatorId;
    private Long proofreaderId;

    private LocalDateTime requestDate;
    private String studentName;
    private Integer totalPages;
    private TranslateRequestStatus status;
    private LocalDateTime deliveryDate;

    private Long mistakesDocumentId;
    private String mistakesDocumentUrl;
}
