package com.waraq.service.s3;


import com.waraq.dto.document.MediaResponse;
import com.waraq.repository.entities.media.MediaEntity;

public class MediaMapper {

    public MediaResponse mapEntityToResponseDto(MediaEntity entity) {
        return MediaResponse.builder()
                .id(entity.getId())
                .mediaUrl(entity.getMediaUrl())
                .mediaType(entity.getType())
                .mimeMediaType(entity.getMimeType())
                .build();
    }
}
