package com.waraq.dto.admin.translate.request;

import com.waraq.dto.UpdateDTO;
import com.waraq.repository.enums.TranslateRequestStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslateRequestUpdateRequest implements UpdateDTO {
    private Long clientId;
    private LocalDateTime requestDate;
    private String studentName;
    private Integer totalPages;
    private Long translatorId;
    private Long proofreaderId;
    private TranslateRequestStatus status;
    private LocalDateTime deliveryDate;
    private Long mistakesDocumentId;
}
