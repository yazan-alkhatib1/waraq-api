package com.waraq.dto.translate.doc;

import com.waraq.dto.UpdateDTO;
import com.waraq.repository.enums.TranslateRequestStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslateDocUpdateRequest implements UpdateDTO {
    private Long requestId;
    private Long documentId;
    private Long templateId;
    private Long translatedDocumentId;
    private Long finalTranslatedDocumentId;
    private TranslateRequestStatus status;
}
