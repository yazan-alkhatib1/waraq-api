package com.waraq.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseList<R extends ResponseDto> {

    private List<R> response;

    private Long allRecords;
}
