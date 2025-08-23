package com.waraq.service.translate.template;

import com.waraq.dto.admin.translate.template.TranslateTemplateCreateRequest;
import com.waraq.dto.admin.translate.template.TranslateTemplateResponse;
import com.waraq.dto.admin.translate.template.TranslateTemplateUpdateRequest;
import com.waraq.exceptions.BodyGuardException;
import com.waraq.repositories.RepositoryFactory;
import com.waraq.repository.entities.media.MediaEntity;
import com.waraq.repository.entities.translate.TranslateTemplateEntity;
import com.waraq.repository.repositories.media.MediaRepository;
import com.waraq.repository.repositories.translate.TranslateTemplateRepository;
import com.waraq.service.base.BaseServiceImpl;
import com.waraq.service.mappers.BaseMapper;
import com.waraq.service.mappers.translate.TranslateTemplateMapper;
import com.waraq.validator.CompositeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class TranslateTemplateServiceImpl extends BaseServiceImpl<TranslateTemplateEntity, TranslateTemplateCreateRequest, TranslateTemplateUpdateRequest, TranslateTemplateResponse>
        implements TranslateTemplateService {

    private final TranslateTemplateRepository translateTemplateRepository;
    private final TranslateTemplateMapper translateTemplateMapper;
    private final RepositoryFactory repositoryFactory;

    public TranslateTemplateServiceImpl(RepositoryFactory repositoryFactory) {
        this.translateTemplateRepository = repositoryFactory.getRepository(TranslateTemplateRepository.class);
        this.translateTemplateMapper = new TranslateTemplateMapper(repositoryFactory);
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public TranslateTemplateRepository getRepository() {
        return translateTemplateRepository;
    }

    @Override
    public BaseMapper<TranslateTemplateEntity, TranslateTemplateCreateRequest, TranslateTemplateResponse> getMapper() {
        return translateTemplateMapper;
    }

    @Override
    public void updateEntityProps(TranslateTemplateEntity oldEntity, TranslateTemplateUpdateRequest updateDto) {
        log.info("Updating TranslateTemplateEntity ID: {}", oldEntity.getId());
        if (nonNull(updateDto.getDocumentId())) {
            oldEntity.setDocument(getDocument(updateDto.getDocumentId()));
        }
    }

    @Override
    public void preAddValidation(TranslateTemplateCreateRequest createDto) {
        List<String> violations = new CompositeValidator<TranslateTemplateCreateRequest, String>()
                .addValidator(r -> nonNull(r.getDocumentId()), "Document Id Cannot Be Empty")
                .addValidator(r -> repositoryFactory.getRepository(MediaRepository.class).existsById(r.getDocumentId()),
                        "No Media found with this id {" + createDto.getDocumentId() + "}")
                .validate(createDto);
        validate(violations);
    }

    @Override
    public void updatePreValidation(TranslateTemplateUpdateRequest updateDTO) {
        List<String> violations = new CompositeValidator<TranslateTemplateUpdateRequest, String>()
                .addValidator(r -> !nonNull(r.getDocumentId()) || repositoryFactory.getRepository(MediaRepository.class).findById(r.getDocumentId()).isPresent(),
                        "No Media found with this id {" + updateDTO.getDocumentId() + "}")
                .validate(updateDTO);
        validate(violations);
    }

    private MediaEntity getDocument(Long documentId) {
        return repositoryFactory.getRepository(MediaRepository.class).findById(documentId)
                .orElseThrow(() -> new BodyGuardException("Cannot find active media by id (" + documentId + ")"));
    }
}
