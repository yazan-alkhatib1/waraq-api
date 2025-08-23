package com.waraq.service.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.waraq.dto.document.MediaResponse;
import com.waraq.dto.document.UploadDocumentResponse;
import com.waraq.exceptions.BadRequestException;
import com.waraq.exceptions.DocumentUploadException;
import com.waraq.exceptions.NotFoundException;
import com.waraq.repositories.RepositoryFactory;
import com.waraq.repository.repositories.media.MediaRepository;
import com.waraq.validator.CompositeValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.join;

@Slf4j
@Service
public class MediaUploaderService {

    private final AmazonS3 amazonS3;
    private final String spaceName;
    private final String bucketDownload;
    private final Long maxFileSize;
    private final MediaMapper mediaMapper = new MediaMapper();
    private final List<String> filesExtensions = loadFilesExtensions();
    private final RepositoryFactory repositoryFactory;


    public MediaUploaderService(AmazonS3 amazonS3,
                                @Value("${waraq.spaces.name}") String spaceName,
                                @Value("${waraq.bucket.download}") String bucketDownload,
                                @Value("${waraq.spaces.max.file.size}") Long maxFileSize,
                                RepositoryFactory repositoryFactory) {
        this.amazonS3 = amazonS3;
        this.spaceName = spaceName;
        this.bucketDownload = bucketDownload;
        this.maxFileSize = maxFileSize;
        this.repositoryFactory = repositoryFactory;
    }

    public UploadDocumentResponse uploadMedia(MultipartFile file) {
        if (file.isEmpty())
            throw new BadRequestException("file can't be empty");
        log.info("validating file upload...");
        validateFile(file);
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String key = getFileKey(file, fileExtension); //Unique file key
            PutObjectRequest putObjectRequest = new PutObjectRequest(spaceName, key, file.getInputStream(), objectMetadata);
            putObjectRequest.withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest);
            return UploadDocumentResponse.builder()
                    .mediaUrl(bucketDownload.concat(key))
                    .mediaType(fileExtension)
                    .mimeType(file.getContentType())
                    .build();

        } catch (Exception e) {
            throw new DocumentUploadException("Error uploading file, Cause: " + e.getMessage());
        }
    }

    public MediaResponse getMedia(Long id) {
        return mediaMapper.mapEntityToResponseDto(repositoryFactory.getRepository(MediaRepository.class)
                .findById(id).orElseThrow(() -> new NotFoundException("No Media found with ID : " + id)));
    }

    private String getFileKey(MultipartFile file, String documentType) {
        return file.getOriginalFilename()
                .substring(0, file.getOriginalFilename().indexOf("."))
                .concat(LocalDateTime.now().toString())
                .concat(".".concat(documentType)); //Unique file key
    }

    private List<String> loadFilesExtensions() {
        return Arrays.asList("jpg", "docx", "pdf", "txt",
                "ppt", "pptx", "png", "jpeg", "mp4", "mov",
                "avi", "xlsx", "zip", "rar", "rvt","svg",
                "rfa", "dwg", "css");
    }

    private void validateFile(MultipartFile file) {  //Validate File Type to only supported types
        List<String> violations = new CompositeValidator<MultipartFile, String>()
                .addValidator(r -> filesExtensions.contains(StringUtils.getFilenameExtension(r.getOriginalFilename().toLowerCase())), "Unsupported file type")
                .addValidator(r -> !(file.getSize() >= maxFileSize),"File should be less than: "+maxFileSize/1000000+"MB")
                .validate(file);
        if (!violations.isEmpty()) {
            throw new BadRequestException(join(",", violations));
        }
    }
}
