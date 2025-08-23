package com.waraq.service.base;

import com.waraq.dto.CreateDTO;
import com.waraq.dto.ResponseDTO;
import com.waraq.dto.ResponseList;
import com.waraq.dto.UpdateDTO;
import com.waraq.entities.BaseEntity;
import com.waraq.exceptions.BodyGuardException;
import com.waraq.exceptions.NotFoundException;
import com.waraq.repositories.BaseRepository;
import com.waraq.service.mappers.BaseMapper;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.join;

public abstract class BaseServiceImpl<T extends BaseEntity, C extends CreateDTO, U extends UpdateDTO, R extends ResponseDTO> implements BaseService<C, U, R> {

    public abstract BaseRepository<T> getRepository();

    public abstract void updateEntityProps(T oldEntity, U updateDto);

    public abstract void preAddValidation(C createDto);

    public abstract void updatePreValidation(U updateDTO);

    public abstract BaseMapper<T, C, R> getMapper();

    public R save(@NotNull C createDto) {
        preAddValidation(createDto);
        preAdd(createDto);
        T entity = getMapper().mapCreateDtoToEntity(createDto);
        entity = getRepository().save(entity);
        postAdd(createDto, entity);
        return getMapper().mapEntityToResponseDto(entity);
    }

    public R update(@NotNull U updateDto, Long id) {
        preUpdate(updateDto, id);
        updatePreValidation(updateDto);
        T entity = findActiveEntityById(id);
        updateEntityProps(entity, updateDto);
        entity = getRepository().save(entity);
        postUpdate(updateDto, entity);
        return getMapper().mapEntityToResponseDto(entity);
    }


    public ResponseList<R> retrieveAllActive(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page + 1, size, Sort.Direction.ASC, "id");
        Pageable pageable = pageRequest.previous();

        Page<T> allPageable = getRepository().findAll(pageable);

        List<R> data = allPageable.stream()
                .map(getMapper()::mapEntityToListResponse)
                .collect(Collectors.toList());

        return ResponseList.<R>builder()
                .response(data)
                .totalElements(allPageable.getTotalElements())
                .build();
    }

    public ResponseList<R> retrieveAllEnabled(Integer page, Integer size) {
        PageRequest pageRequest = PageRequest.of(page + 1, size, Sort.Direction.ASC, "id");
        Pageable pageable = pageRequest.previous();

        Page<T> allEnabled = getRepository().findAll(pageable);

        List<R> data = allEnabled.stream()
                .map(getMapper()::mapEntityToListResponse)
                .collect(Collectors.toList());

        return ResponseList.<R>builder()
                .response(data)
                .totalElements(allEnabled.getTotalElements())
                .build();
    }


    public R retrieveById(@NotNull Long id) {
        return getMapper().mapEntityToResponseDto(findActiveEntityById(id));
    }

    protected T findActiveEntityById(@NotNull Long id) {
        return getRepository().findById(id)
                .orElseThrow(() -> new NotFoundException("Failed to retrieve entity with id {" + id + "}" +
                        ". Entity is not found."));
    }

    protected void validate(List<String> violations) {
        if (!violations.isEmpty()) {
            throw new BodyGuardException(join(",", violations));
        }
    }

    public void preAdd(C createDto) {
    }

    public void postAdd(C createDto, T entity) {
    }

    public void preUpdate(U updateDto, Long id) {
    }

    public void postUpdate(U updateDto, T entity) {
    }
}
