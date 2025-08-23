package com.waraq.dto.document;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UploadDocumentResponse {
    private String mediaUrl;

    private String mimeType;

    private String mediaType;
}
