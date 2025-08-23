package com.waraq.dto.translate.doc;

import com.waraq.dto.CreateDTO;
import com.waraq.repository.enums.TranslateRequestStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslateDocCreateRequest implements CreateDTO {
    private Long requestId;
    private Long documentId;
    private Long templateId;
    private Long translatedDocumentId;
    private Long finalTranslatedDocumentId;
    private TranslateRequestStatus status;
}
