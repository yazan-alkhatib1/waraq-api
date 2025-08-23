package com.waraq.controllers.translate.request;

import com.waraq.controllers.BaseCRUDController;
import com.waraq.dto.admin.translate.request.TranslateRequestCreateRequest;
import com.waraq.dto.admin.translate.request.TranslateRequestResponse;
import com.waraq.dto.admin.translate.request.TranslateRequestUpdateRequest;
import com.waraq.exceptions.BodyGuardException;
import com.waraq.http_response.Response;
import com.waraq.logging.Monitored;
import com.waraq.service.base.BaseService;
import com.waraq.service.translate.request.TranslateRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Monitored
@RestController
@RequestMapping("/api/v1/translate/requests")
public class TranslateRequestController extends BaseCRUDController<TranslateRequestCreateRequest, TranslateRequestUpdateRequest, TranslateRequestResponse> {

    private final TranslateRequestService translateRequestService;

    public TranslateRequestController(TranslateRequestService translateRequestService) {
        this.translateRequestService = translateRequestService;
    }

    @Override
    public BaseService<TranslateRequestCreateRequest, TranslateRequestUpdateRequest, TranslateRequestResponse> getService() {
        return translateRequestService;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<TranslateRequestResponse>> delete(@PathVariable Long id) {
        throw new BodyGuardException("Translate request cannot be deleted.");
    }
}
