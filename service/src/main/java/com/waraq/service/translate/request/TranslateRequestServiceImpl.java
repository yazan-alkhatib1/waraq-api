package com.waraq.service.translate.request;

import com.waraq.dto.admin.translate.request.TranslateRequestCreateRequest;
import com.waraq.dto.admin.translate.request.TranslateRequestResponse;
import com.waraq.dto.admin.translate.request.TranslateRequestUpdateRequest;
import com.waraq.exceptions.BodyGuardException;
import com.waraq.repositories.RepositoryFactory;
import com.waraq.repository.entities.media.MediaEntity;
import com.waraq.repository.entities.translate.TranslateRequestEntity;
import com.waraq.repository.entities.user.UserEntity;
import com.waraq.repository.repositories.media.MediaRepository;
import com.waraq.repository.repositories.translate.TranslateRequestRepository;
import com.waraq.repository.repositories.user.UserRepository;
import com.waraq.service.base.BaseServiceImpl;
import com.waraq.service.mappers.BaseMapper;
import com.waraq.service.mappers.translate.TranslateRequestMapper;
import com.waraq.validator.CompositeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.amazonaws.util.StringUtils.hasValue;
import static java.util.Objects.nonNull;

@Slf4j
@Service
public class TranslateRequestServiceImpl extends BaseServiceImpl<TranslateRequestEntity, TranslateRequestCreateRequest, TranslateRequestUpdateRequest, TranslateRequestResponse>
        implements TranslateRequestService {

    private final TranslateRequestRepository translateRequestRepository;
    private final TranslateRequestMapper translateRequestMapper;
    private final RepositoryFactory repositoryFactory;

    public TranslateRequestServiceImpl(RepositoryFactory repositoryFactory) {
        this.translateRequestRepository = repositoryFactory.getRepository(TranslateRequestRepository.class);
        this.translateRequestMapper = new TranslateRequestMapper(repositoryFactory);
        this.repositoryFactory = repositoryFactory;
    }

    @Override
    public TranslateRequestRepository getRepository() {
        return translateRequestRepository;
    }

    @Override
    public BaseMapper<TranslateRequestEntity, TranslateRequestCreateRequest, TranslateRequestResponse> getMapper() {
        return translateRequestMapper;
    }

    @Override
    public void updateEntityProps(TranslateRequestEntity oldEntity, TranslateRequestUpdateRequest updateDto) {
        log.info("Updating TranslateRequestEntity ID: {}", oldEntity.getId());
        final LocalDateTime now = LocalDateTime.now();

        if (nonNull(updateDto.getClientId())) {
            oldEntity.setClient(getUser(updateDto.getClientId()));
        }
        if (nonNull(updateDto.getTranslatorId())) {
            oldEntity.setTranslator(getUser(updateDto.getTranslatorId()));
        }
        if (nonNull(updateDto.getProofreaderId())) {
            oldEntity.setProofreader(getUser(updateDto.getProofreaderId()));
        }
        if (nonNull(updateDto.getMistakesDocumentId())) {
            oldEntity.setMistakesDocument(getMedia(updateDto.getMistakesDocumentId()));
        }
        if (nonNull(updateDto.getRequestDate())) {
            oldEntity.setRequestDate(updateDto.getRequestDate());
        }
        if (hasValue(updateDto.getStudentName())) {
            oldEntity.setStudentName(updateDto.getStudentName());
        }
        if (nonNull(updateDto.getTotalPages())) {
            oldEntity.setTotalPages(updateDto.getTotalPages());
        }
        if (nonNull(updateDto.getStatus())) {
            oldEntity.setStatus(updateDto.getStatus());
        }
        if (nonNull(updateDto.getDeliveryDate())) {
            oldEntity.setDeliveryDate(updateDto.getDeliveryDate());
        }

        oldEntity.setUpdatedDate(now);
    }

    @Override
    public void preAddValidation(TranslateRequestCreateRequest createDto) {
        List<String> violations = new CompositeValidator<TranslateRequestCreateRequest, String>()
                .addValidator(r -> nonNull(r.getClientId()) && repositoryFactory.getRepository(UserRepository.class).existsById(r.getClientId()),
                        "No User found with this id {" + createDto.getClientId() + "}")
                .addValidator(r -> hasValue(r.getStudentName()), "Student Name Cannot Be Empty")
                .addValidator(r -> nonNull(r.getTotalPages()) && r.getTotalPages() > 0, "Total Pages Should Be > 0")
                .addValidator(r -> r.getTranslatorId() == null || repositoryFactory.getRepository(UserRepository.class).existsById(r.getTranslatorId()),
                        "No Translator found with this id {" + createDto.getTranslatorId() + "}")
                .addValidator(r -> r.getProofreaderId() == null || repositoryFactory.getRepository(UserRepository.class).existsById(r.getProofreaderId()),
                        "No Proofreader found with this id {" + createDto.getProofreaderId() + "}")
                .addValidator(r -> r.getMistakesDocumentId() == null || repositoryFactory.getRepository(MediaRepository.class).existsById(r.getMistakesDocumentId()),
                        "No Media found with this id {" + createDto.getMistakesDocumentId() + "}")
                .validate(createDto);
        validate(violations);
    }

    @Override
    public void updatePreValidation(TranslateRequestUpdateRequest updateDTO) {
        List<String> violations = new CompositeValidator<TranslateRequestUpdateRequest, String>()
                .addValidator(r -> r.getClientId() == null || repositoryFactory.getRepository(UserRepository.class).existsById(r.getClientId()),
                        "No User found with this id {" + updateDTO.getClientId() + "}")
                .addValidator(r -> r.getTranslatorId() == null || repositoryFactory.getRepository(UserRepository.class).existsById(r.getTranslatorId()),
                        "No Translator found with this id {" + updateDTO.getTranslatorId() + "}")
                .addValidator(r -> r.getProofreaderId() == null || repositoryFactory.getRepository(UserRepository.class).existsById(r.getProofreaderId()),
                        "No Proofreader found with this id {" + updateDTO.getProofreaderId() + "}")
                .addValidator(r -> r.getTotalPages() == null || r.getTotalPages() > 0, "Total Pages Should Be > 0")
                .addValidator(r -> r.getMistakesDocumentId() == null || repositoryFactory.getRepository(MediaRepository.class).findById(r.getMistakesDocumentId()).isPresent(),
                        "No Media found with this id {" + updateDTO.getMistakesDocumentId() + "}")
                .validate(updateDTO);
        validate(violations);
    }

    private UserEntity getUser(Long userId) {
        return repositoryFactory.getRepository(UserRepository.class).findById(userId)
                .orElseThrow(() -> new BodyGuardException("Cannot find active user by id (" + userId + ")"));
    }

    private MediaEntity getMedia(Long mediaId) {
        return repositoryFactory.getRepository(MediaRepository.class).findById(mediaId)
                .orElseThrow(() -> new BodyGuardException("Cannot find active media by id (" + mediaId + ")"));
    }
}
