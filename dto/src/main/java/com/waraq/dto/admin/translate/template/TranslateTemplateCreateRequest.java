package com.waraq.dto.admin.translate.template;

import com.waraq.dto.CreateDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslateTemplateCreateRequest implements CreateDTO {
    private Long documentId;
}
