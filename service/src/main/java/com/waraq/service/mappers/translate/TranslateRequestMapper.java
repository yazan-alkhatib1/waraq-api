package com.waraq.service.mappers.translate;

import com.waraq.dto.admin.translate.request.TranslateRequestCreateRequest;
import com.waraq.dto.admin.translate.request.TranslateRequestResponse;
import com.waraq.exceptions.BodyGuardException;
import com.waraq.helpers.UserLoader;
import com.waraq.repositories.RepositoryFactory;
import com.waraq.repository.entities.media.MediaEntity;
import com.waraq.repository.entities.translate.TranslateRequestEntity;
import com.waraq.repository.entities.user.WaraqUserEntity;
import com.waraq.repository.enums.TranslateRequestStatus;
import com.waraq.repository.repositories.media.MediaRepository;
import com.waraq.repository.repositories.user.WaraqUserRepository;
import com.waraq.service.mappers.BaseMapper;

import java.time.LocalDateTime;

import static java.util.Objects.nonNull;

public class TranslateRequestMapper implements BaseMapper<TranslateRequestEntity, TranslateRequestCreateRequest, TranslateRequestResponse> {

    private final RepositoryFactory repositoryFactory;

    public TranslateRequestMapper(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public TranslateRequestEntity mapCreateDtoToEntity(TranslateRequestCreateRequest createDto) {
        final LocalDateTime now = LocalDateTime.now();
        WaraqUserEntity client = getUser(createDto.getClientId());
        WaraqUserEntity translator = nonNull(createDto.getTranslatorId()) ? getUser(createDto.getTranslatorId()) : null;
        WaraqUserEntity proofreader = nonNull(createDto.getProofreaderId()) ? getUser(createDto.getProofreaderId()) : null;
        MediaEntity mistakes = nonNull(createDto.getMistakesDocumentId()) ? getMedia(createDto.getMistakesDocumentId()) : null;

        TranslateRequestEntity entity = TranslateRequestEntity.builder()
                .client(client)
                .requestDate(nonNull(createDto.getRequestDate()) ? createDto.getRequestDate() : now)
                .studentName(createDto.getStudentName())
                .totalPages(nonNull(createDto.getTotalPages()) ? createDto.getTotalPages() : 0)
                .translator(translator)
                .proofreader(proofreader)
                .status(nonNull(createDto.getStatus()) ? createDto.getStatus() : TranslateRequestStatus.PENDING)
                .deliveryDate(createDto.getDeliveryDate())
                .createdBy(UserLoader.userDetails().getId())
                .updatedBy(UserLoader.userDetails().getId())
                .creationDate(now)
                .updatedDate(now)
                .isActive(true)
                .isEnabled(true)
                .build();
        entity.setMistakesDocument(mistakes);
        return entity;
    }

    @Override
    public TranslateRequestResponse mapEntityToResponseDto(TranslateRequestEntity entity) {
        return TranslateRequestResponse.builder()
                .id(entity.getId())
                .creationDate(entity.getCreationDate())
                .isEnabled(entity.getIsEnabled())
                .clientId(getUserId(entity.getClient()))
                .translatorId(getUserId(entity.getTranslator()))
                .proofreaderId(getUserId(entity.getProofreader()))
                .requestDate(entity.getRequestDate())
                .studentName(entity.getStudentName())
                .totalPages(entity.getTotalPages())
                .status(entity.getStatus())
                .deliveryDate(entity.getDeliveryDate())
                .mistakesDocumentId(getMistakesId(entity))
                .mistakesDocumentUrl(getMistakesUrl(entity))
                .build();
    }

    @Override
    public TranslateRequestResponse mapEntityToListResponse(TranslateRequestEntity entity) {
        return mapEntityToResponseDto(entity);
    }

    private WaraqUserEntity getUser(Long userId) {
        return repositoryFactory.getRepository(WaraqUserRepository.class).findById(userId)
                .orElseThrow(() -> new BodyGuardException("Cannot find active waraq user by id (" + userId + ")"));
    }

    private MediaEntity getMedia(Long mediaId) {
        return repositoryFactory.getRepository(MediaRepository.class).findById(mediaId)
                .orElseThrow(() -> new BodyGuardException("Cannot find active media by id (" + mediaId + ")"));
    }

    private Long getUserId(WaraqUserEntity user) {
        return nonNull(user) ? user.getId() : null;
    }

    private Long getMistakesId(TranslateRequestEntity entity) {
        return nonNull(entity.getMistakesDocument()) ? entity.getMistakesDocument().getId() : null;
    }

    private String getMistakesUrl(TranslateRequestEntity entity) {
        return nonNull(entity.getMistakesDocument()) ? entity.getMistakesDocument().getMediaUrl() : null;
    }
}
