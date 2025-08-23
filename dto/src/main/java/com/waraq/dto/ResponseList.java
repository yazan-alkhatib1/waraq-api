package com.waraq.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ResponseList<R> implements ResponseDTO {

    private List<R> response;

    private Long totalElements;
}
