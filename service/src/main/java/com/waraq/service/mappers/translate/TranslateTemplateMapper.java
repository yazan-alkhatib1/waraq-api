package com.waraq.service.mappers.translate;

import com.waraq.dto.admin.translate.template.TranslateTemplateCreateRequest;
import com.waraq.dto.admin.translate.template.TranslateTemplateResponse;
import com.waraq.exceptions.BodyGuardException;
import com.waraq.helpers.UserLoader;
import com.waraq.repositories.RepositoryFactory;
import com.waraq.repository.entities.media.MediaEntity;
import com.waraq.repository.entities.translate.TranslateTemplateEntity;
import com.waraq.repository.repositories.media.MediaRepository;
import com.waraq.service.mappers.BaseMapper;

import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

public class TranslateTemplateMapper implements BaseMapper<TranslateTemplateEntity, TranslateTemplateCreateRequest, TranslateTemplateResponse> {

    private final RepositoryFactory repositoryFactory;

    public TranslateTemplateMapper(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public TranslateTemplateEntity mapCreateDtoToEntity(TranslateTemplateCreateRequest createDto) {
        final LocalDateTime now = LocalDateTime.now();
        return TranslateTemplateEntity.builder()
                .document(getDocument(createDto))
                .createdBy(UserLoader.userDetails().getId())
                .updatedBy(UserLoader.userDetails().getId())
                .creationDate(now)
                .updatedDate(now)
                .isActive(true)
                .isEnabled(true)
                .build();
    }

    @Override
    public TranslateTemplateResponse mapEntityToResponseDto(TranslateTemplateEntity entity) {
        return TranslateTemplateResponse.builder()
                .id(entity.getId())
                .creationDate(entity.getCreationDate())
                .isEnabled(entity.getIsEnabled())
                .documentId(getDocumentId(entity))
                .documentUrl(getDocumentUrl(entity))
                .build();
    }

    @Override
    public TranslateTemplateResponse mapEntityToListResponse(TranslateTemplateEntity entity) {
        return mapEntityToResponseDto(entity);
    }

    private MediaEntity getDocument(TranslateTemplateCreateRequest createDto) {
        return nonNull(createDto.getDocumentId())
                ? repositoryFactory.getRepository(MediaRepository.class).findById(createDto.getDocumentId())
                .orElseThrow(() -> new BodyGuardException("Cannot find active media by id (" + createDto.getDocumentId() + ")"))
                : null;
    }

    private Long getDocumentId(TranslateTemplateEntity entity) {
        return nonNull(entity.getDocument()) ? entity.getDocument().getId() : null;
    }

    private String getDocumentUrl(TranslateTemplateEntity entity) {
        return nonNull(entity.getDocument()) ? entity.getDocument().getMediaUrl() : null;
    }
}
