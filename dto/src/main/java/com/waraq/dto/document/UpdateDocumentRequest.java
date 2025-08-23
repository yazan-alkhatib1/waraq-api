package com.waraq.dto.document;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateDocumentRequest {
    private Long documentId;
    private MultipartFile document;

}
