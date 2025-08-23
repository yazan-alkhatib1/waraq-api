package com.waraq.service.s3.spaces;

import com.waraq.dto.document.MediaResponse;
import com.waraq.dto.document.UpdateDocumentRequest;
import com.waraq.dto.document.UploadDocumentResponse;
import com.waraq.repositories.RepositoryFactory;
import com.waraq.repository.entities.media.MediaEntity;
import com.waraq.repository.repositories.media.MediaRepository;
import com.waraq.service.UseCase;
import com.waraq.service.s3.MediaMapper;
import com.waraq.service.s3.MediaUploaderService;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class UpdateDocumentUseCase implements UseCase<UpdateDocumentRequest, MediaResponse> {

    private final MediaUploaderService mediaUploaderService;
    private final MediaMapper mediaMapper;
    private final RepositoryFactory repositoryFactory;

    public UpdateDocumentUseCase(MediaUploaderService mediaUploaderService,
                                 RepositoryFactory repositoryFactory) {
        this.mediaUploaderService = mediaUploaderService;
        this.repositoryFactory = repositoryFactory;
        this.mediaMapper = new MediaMapper();
    }

    @Override
    public MediaResponse execute(UpdateDocumentRequest request) {
        UploadDocumentResponse response = mediaUploaderService.uploadMedia(request.getDocument());

        return mediaMapper.mapEntityToResponseDto(repositoryFactory.getRepository(MediaRepository.class)
                .save(MediaEntity.builder()
                .id(request.getDocumentId())
                .mediaUrl(response.getMediaUrl())
                .type(response.getMediaType())
                .mimeType(response.getMimeType())
                .updatedDate(LocalDateTime.now())
                .creationDate(LocalDateTime.now())
                .isActive(true)
                .isEnabled(true)
                .build()));
    }
}
