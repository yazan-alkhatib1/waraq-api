package com.waraq.controllers;


import com.waraq.dto.CreateDTO;
import com.waraq.dto.ResponseDTO;
import com.waraq.dto.ResponseList;
import com.waraq.dto.UpdateDTO;
import com.waraq.http_response.CODE;
import com.waraq.http_response.Response;
import com.waraq.service.base.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseCRUDController<C extends CreateDTO, U extends UpdateDTO, R extends ResponseDTO> {

    public abstract BaseService<C, U, R> getService();

    @PostMapping
    public ResponseEntity<Response<R>> add(@RequestBody C createDTO) {
        Response<R> response = Response.<R>builder().data(getService().save(createDTO))
                .success(true)
                .code(CODE.CREATED.getId())
                .message(CODE.CREATED.name())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<R>> update(@RequestBody U updateDto, @PathVariable Long id) {
        Response<R> response = Response.<R>builder().data(getService().update(updateDto, id))
                .success(true)
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<R>> delete(@PathVariable Long id) {
        return new ResponseEntity<>(Response.<R>builder()
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<R>> getById(@PathVariable Long id) {
        Response<R> response = Response.<R>builder().data(getService().retrieveById(id))
                .success(true)
                .code(CODE.OK.getId())
                .message(CODE.OK.name()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    private Response<List<R>> activeAndEnabled(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "20") Integer size) {
        ResponseList<R> enabledRecords = getService().retrieveAllEnabled(page, size);
        return Response.<List<R>>builder()
                .success(true)
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .data(enabledRecords.getResponse())
                .allRecords(enabledRecords.getTotalElements())
                .build();
    }

    public Response<List<R>> activeRecords(@RequestParam(required = false, defaultValue = "0") Integer page, @RequestParam(required = false, defaultValue = "20") Integer size) {
        ResponseList<R> responseList = getService().retrieveAllActive(page, size);
        return Response.<List<R>>builder()
                .success(true)
                .code(CODE.OK.getId())
                .message(CODE.OK.name())
                .data(responseList.getResponse())
                .allRecords(responseList.getTotalElements())
                .build();
    }
}
