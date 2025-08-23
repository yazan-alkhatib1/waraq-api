package com.waraq.service.s3.spaces;


import com.waraq.dto.document.MediaResponse;
import com.waraq.dto.document.UploadDocumentResponse;
import com.waraq.helpers.UserLoader;
import com.waraq.repositories.RepositoryFactory;
import com.waraq.repository.entities.media.MediaEntity;
import com.waraq.repository.repositories.media.MediaRepository;
import com.waraq.service.UseCase;
import com.waraq.service.s3.MediaMapper;
import com.waraq.service.s3.MediaUploaderService;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Component
public class CreateMediaUseCase implements UseCase<MultipartFile, MediaResponse> {

    private final MediaUploaderService mediaUploaderService;
    private final RepositoryFactory repositoryFactory;
    private final MediaMapper mediaMapper;

    public CreateMediaUseCase(MediaUploaderService mediaUploaderService,
                              RepositoryFactory repositoryFactory) {
        this.mediaUploaderService = mediaUploaderService;
        this.repositoryFactory = repositoryFactory;
        this.mediaMapper = new MediaMapper();
    }

    @Override
    public MediaResponse execute(MultipartFile media) {
        UploadDocumentResponse response = mediaUploaderService.uploadMedia(media);

        return mediaMapper.mapEntityToResponseDto(repositoryFactory.getRepository(MediaRepository.class)
                .save(MediaEntity.builder()
                        .isActive(true)
                        .isEnabled(true)
                        .updatedDate(LocalDateTime.now())
                        .creationDate(LocalDateTime.now())
                        .updatedBy(UserLoader.userDetails().getId())
                        .createdBy(UserLoader.userDetails().getId())
                        .mediaUrl(response.getMediaUrl())
                        .type(response.getMediaType())
                        .mimeType(response.getMimeType())
                        .build()));
    }
}
