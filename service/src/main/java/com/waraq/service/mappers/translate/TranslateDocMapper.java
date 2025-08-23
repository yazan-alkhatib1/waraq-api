package com.waraq.service.mappers.translate;

import com.waraq.dto.translate.doc.TranslateDocCreateRequest;
import com.waraq.dto.translate.doc.TranslateDocResponse;
import com.waraq.exceptions.BodyGuardException;
import com.waraq.helpers.UserLoader;
import com.waraq.repositories.RepositoryFactory;
import com.waraq.repository.entities.media.MediaEntity;
import com.waraq.repository.entities.translate.TranslateDocEntity;
import com.waraq.repository.entities.translate.TranslateRequestEntity;
import com.waraq.repository.enums.TranslateRequestStatus;
import com.waraq.repository.repositories.media.MediaRepository;
import com.waraq.repository.repositories.translate.TranslateRequestRepository;
import com.waraq.service.mappers.BaseMapper;

import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

public class TranslateDocMapper implements BaseMapper<TranslateDocEntity, TranslateDocCreateRequest, TranslateDocResponse> {

    private final RepositoryFactory repositoryFactory;

    public TranslateDocMapper(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public TranslateDocEntity mapCreateDtoToEntity(TranslateDocCreateRequest createDto) {
        final LocalDateTime now = LocalDateTime.now();
        TranslateDocEntity entity = TranslateDocEntity.builder()
                .status(nonNull(createDto.getStatus()) ? createDto.getStatus() : TranslateRequestStatus.PENDING)
                .document(getMedia(createDto.getDocumentId()))
                .template(nonNull(createDto.getTemplateId()) ? getMedia(createDto.getTemplateId()) : null)
                .translatedDocument(nonNull(createDto.getTranslatedDocumentId()) ? getMedia(createDto.getTranslatedDocumentId()) : null)
                .finalTranslatedDocument(nonNull(createDto.getFinalTranslatedDocumentId()) ? getMedia(createDto.getFinalTranslatedDocumentId()) : null)
                .createdBy(UserLoader.userDetails().getId())
                .updatedBy(UserLoader.userDetails().getId())
                .creationDate(now)
                .updatedDate(now)
                .isActive(true)
                .isEnabled(true)
                .build();
        entity.setRequest(getRequest(createDto.getRequestId()));
        return entity;
    }

    @Override
    public TranslateDocResponse mapEntityToResponseDto(TranslateDocEntity entity) {
        return TranslateDocResponse.builder()
                .id(entity.getId())
                .creationDate(entity.getCreationDate())
                .isEnabled(entity.getIsEnabled())
                .requestId(getRequestId(entity.getRequest()))
                .status(entity.getStatus())
                .documentId(getId(entity.getDocument()))
                .documentUrl(getUrl(entity.getDocument()))
                .templateId(getId(entity.getTemplate()))
                .templateUrl(getUrl(entity.getTemplate()))
                .translatedDocumentId(getId(entity.getTranslatedDocument()))
                .translatedDocumentUrl(getUrl(entity.getTranslatedDocument()))
                .finalTranslatedDocumentId(getId(entity.getFinalTranslatedDocument()))
                .finalTranslatedDocumentUrl(getUrl(entity.getFinalTranslatedDocument()))
                .build();
    }

    @Override
    public TranslateDocResponse mapEntityToListResponse(TranslateDocEntity entity) {
        return mapEntityToResponseDto(entity);
    }

    private MediaEntity getMedia(Long mediaId) {
        return repositoryFactory.getRepository(MediaRepository.class).findById(mediaId)
                .orElseThrow(() -> new BodyGuardException("Cannot find active media by id (" + mediaId + ")"));
    }

    private TranslateRequestEntity getRequest(Long requestId) {
        return repositoryFactory.getRepository(TranslateRequestRepository.class).findById(requestId)
                .orElseThrow(() -> new BodyGuardException("Cannot find active translate request by id (" + requestId + ")"));
    }

    private Long getId(MediaEntity media) {
        return nonNull(media) ? media.getId() : null;
    }

    private Long getRequestId(TranslateRequestEntity request) {
        return nonNull(request) ? request.getId() : null;
    }

    private String getUrl(MediaEntity media) {
        return nonNull(media) ? media.getMediaUrl() : null;
    }
}
