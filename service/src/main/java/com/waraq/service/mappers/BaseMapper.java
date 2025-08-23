package com.waraq.service.mappers;
import com.waraq.dto.CreateDTO;
import com.waraq.dto.ResponseDTO;
import com.waraq.entities.BaseEntity;
import com.waraq.helpers.UserLoader;

import java.time.LocalDateTime;

public interface BaseMapper <T extends BaseEntity, C extends CreateDTO, R extends ResponseDTO>{

    T mapCreateDtoToEntity(C createDto);

    R mapEntityToResponseDto(T entity);

    R mapEntityToListResponse(T entity);

    default T addBaseEntityFields(T base, boolean isEnabled, LocalDateTime now) {
        base.setCreatedBy(UserLoader.userDetails().getId());
        base.setUpdatedBy(UserLoader.userDetails().getId());
        base.setCreationDate(now);
        base.setUpdatedDate(now);
        base.setIsActive(true);
        base.setIsEnabled(isEnabled);
        return base;
    }

}
