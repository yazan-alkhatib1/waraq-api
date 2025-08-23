package com.waraq.service.translate.doc;

import com.waraq.dto.translate.doc.TranslateDocCreateRequest;
import com.waraq.dto.translate.doc.TranslateDocResponse;
import com.waraq.dto.translate.doc.TranslateDocUpdateRequest;
import com.waraq.exceptions.BodyGuardException;
import com.waraq.helpers.UserLoader;
import com.waraq.repositories.RepositoryFactory;
import com.waraq.repository.entities.media.MediaEntity;
import com.waraq.repository.entities.translate.TranslateDocEntity;
import com.waraq.repository.entities.translate.TranslateRequestEntity;
import com.waraq.repository.repositories.media.MediaRepository;
import com.waraq.repository.repositories.translate.TranslateDocRepository;
import com.waraq.repository.repositories.translate.TranslateRequestRepository;
import com.waraq.service.base.BaseServiceImpl;
import com.waraq.service.mappers.BaseMapper;
import com.waraq.service.mappers.translate.TranslateDocMapper;
import com.waraq.validator.CompositeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class TranslateDocServiceImpl extends BaseServiceImpl<TranslateDocEntity, TranslateDocCreateRequest, TranslateDocUpdateRequest, TranslateDocResponse>
        implements TranslateDocService {
    private final TranslateDocRepository translateDocRepository;
    private final TranslateDocMapper translateDocMapper;
    private final RepositoryFactory repositoryFactory;

    public TranslateDocServiceImpl(RepositoryFactory repositoryFactory) {
        this.translateDocRepository = repositoryFactory.getRepository(TranslateDocRepository.class);
        this.translateDocMapper = new TranslateDocMapper(repositoryFactory);
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public TranslateDocRepository getRepository() {
        return translateDocRepository;
    }

    @Override
    public BaseMapper<TranslateDocEntity, TranslateDocCreateRequest, TranslateDocResponse> getMapper() {
        return translateDocMapper;
    }

    @Override
    public void updateEntityProps(TranslateDocEntity oldEntity, TranslateDocUpdateRequest updateDto) {
        log.info("Updating TranslateDocEntity ID: {}", oldEntity.getId());
        final LocalDateTime now = LocalDateTime.now();

        if (nonNull(updateDto.getStatus())) {
            oldEntity.setStatus(updateDto.getStatus());
        }
        if (nonNull(updateDto.getRequestId())) {
            oldEntity.setRequest(getRequest(updateDto.getRequestId()));
        }
        if (nonNull(updateDto.getDocumentId())) {
            oldEntity.setDocument(getMedia(updateDto.getDocumentId()));
        }
        if (nonNull(updateDto.getTemplateId())) {
            oldEntity.setTemplate(getMedia(updateDto.getTemplateId()));
        }
        if (nonNull(updateDto.getTranslatedDocumentId())) {
            oldEntity.setTranslatedDocument(getMedia(updateDto.getTranslatedDocumentId()));
        }
        if (nonNull(updateDto.getFinalTranslatedDocumentId())) {
            oldEntity.setFinalTranslatedDocument(getMedia(updateDto.getFinalTranslatedDocumentId()));
        }
        oldEntity.setUpdatedDate(now);
        oldEntity.setUpdatedBy(UserLoader.userDetails().getId());
    }

    @Override
    public void preAddValidation(TranslateDocCreateRequest createDto) {
        List<String> violations = new CompositeValidator<TranslateDocCreateRequest, String>()
                .addValidator(r -> nonNull(r.getRequestId()), "Request Id Cannot Be Empty")
                .addValidator(r -> repositoryFactory.getRepository(TranslateRequestRepository.class).existsById(r.getRequestId()),
                        "No Request found with this id {" + createDto.getRequestId() + "}")
                .addValidator(r -> nonNull(r.getDocumentId()), "Document Id Cannot Be Empty")
                .addValidator(r -> repositoryFactory.getRepository(MediaRepository.class).existsById(r.getDocumentId()),
                        "No Media found with this id {" + createDto.getDocumentId() + "}")
                .addValidator(r -> r.getTemplateId() == null || repositoryFactory.getRepository(MediaRepository.class).existsById(r.getTemplateId()),
                        "No Media found with this id {" + createDto.getTemplateId() + "}")
                .addValidator(r -> r.getTranslatedDocumentId() == null || repositoryFactory.getRepository(MediaRepository.class).existsById(r.getTranslatedDocumentId()),
                        "No Media found with this id {" + createDto.getTranslatedDocumentId() + "}")
                .addValidator(r -> r.getFinalTranslatedDocumentId() == null || repositoryFactory.getRepository(MediaRepository.class).existsById(r.getFinalTranslatedDocumentId()),
                        "No Media found with this id {" + createDto.getFinalTranslatedDocumentId() + "}")
                .validate(createDto);
        validate(violations);
    }

    @Override
    public void updatePreValidation(TranslateDocUpdateRequest updateDTO) {
        List<String> violations = new CompositeValidator<TranslateDocUpdateRequest, String>()
                .addValidator(r -> r.getRequestId() == null || repositoryFactory.getRepository(TranslateRequestRepository.class).findById(r.getRequestId()).isPresent(),
                        "No Request found with this id {" + updateDTO.getRequestId() + "}")
                .addValidator(r -> r.getDocumentId() == null || repositoryFactory.getRepository(MediaRepository.class).findById(r.getDocumentId()).isPresent(),
                        "No Media found with this id {" + updateDTO.getDocumentId() + "}")
                .addValidator(r -> r.getTemplateId() == null || repositoryFactory.getRepository(MediaRepository.class).findById(r.getTemplateId()).isPresent(),
                        "No Media found with this id {" + updateDTO.getTemplateId() + "}")
                .addValidator(r -> r.getTranslatedDocumentId() == null || repositoryFactory.getRepository(MediaRepository.class).findById(r.getTranslatedDocumentId()).isPresent(),
                        "No Media found with this id {" + updateDTO.getTranslatedDocumentId() + "}")
                .addValidator(r -> r.getFinalTranslatedDocumentId() == null || repositoryFactory.getRepository(MediaRepository.class).findById(r.getFinalTranslatedDocumentId()).isPresent(),
                        "No Media found with this id {" + updateDTO.getFinalTranslatedDocumentId() + "}")
                .validate(updateDTO);
        validate(violations);
    }

    private TranslateRequestEntity getRequest(Long requestId) {
        return repositoryFactory.getRepository(TranslateRequestRepository.class).findById(requestId)
                .orElseThrow(() -> new BodyGuardException("Cannot find active request by id (" + requestId + ")"));
    }

    private MediaEntity getMedia(Long mediaId) {
        return repositoryFactory.getRepository(MediaRepository.class).findById(mediaId)
                .orElseThrow(() -> new BodyGuardException("Cannot find active media by id (" + mediaId + ")"));
    }
}
