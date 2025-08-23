package com.waraq.controllers.translate.doc;

import com.waraq.controllers.BaseCRUDController;
import com.waraq.dto.translate.doc.TranslateDocCreateRequest;
import com.waraq.dto.translate.doc.TranslateDocResponse;
import com.waraq.dto.translate.doc.TranslateDocUpdateRequest;
import com.waraq.exceptions.BodyGuardException;
import com.waraq.http_response.Response;
import com.waraq.logging.Monitored;
import com.waraq.service.base.BaseService;
import com.waraq.service.translate.doc.TranslateDocService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Monitored
@RestController
@RequestMapping("/api/v1/translate/docs")
public class TranslateDocController extends BaseCRUDController<TranslateDocCreateRequest, TranslateDocUpdateRequest, TranslateDocResponse> {

    private final TranslateDocService translateDocService;

    public TranslateDocController(TranslateDocService translateDocService) {
        this.translateDocService = translateDocService;
    }

    @Override
    public BaseService<TranslateDocCreateRequest, TranslateDocUpdateRequest, TranslateDocResponse> getService() {
        return translateDocService;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<TranslateDocResponse>> delete(@PathVariable Long id) {
        throw new BodyGuardException("Translate document cannot be deleted.");
    }
}
