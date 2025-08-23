package com.waraq.service.base;



import com.waraq.dto.CreateDTO;
import com.waraq.dto.ResponseDTO;
import com.waraq.dto.ResponseList;
import com.waraq.dto.UpdateDTO;
import jakarta.validation.constraints.NotNull;


public interface BaseService<C extends CreateDTO, U extends UpdateDTO, R extends ResponseDTO> {

    R save(C createDto);

    R update(@NotNull U updateDto, Long id);

    ResponseList<R> retrieveAllActive(Integer page, Integer size);

    ResponseList<R> retrieveAllEnabled(Integer page, Integer size);

    R retrieveById(Long id);
}
