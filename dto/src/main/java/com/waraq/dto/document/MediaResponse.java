package com.waraq.dto.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaResponse {

    private Long id;

    private String mediaUrl;

    private String mimeMediaType;

    private String mediaType;
}
