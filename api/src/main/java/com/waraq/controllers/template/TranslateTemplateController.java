package com.waraq.controllers.template;

import com.waraq.controllers.BaseCRUDController;
import com.waraq.dto.admin.translate.template.TranslateTemplateCreateRequest;
import com.waraq.dto.admin.translate.template.TranslateTemplateResponse;
import com.waraq.dto.admin.translate.template.TranslateTemplateUpdateRequest;
import com.waraq.exceptions.BodyGuardException;
import com.waraq.http_response.Response;
import com.waraq.logging.Monitored;
import com.waraq.service.base.BaseService;
import com.waraq.service.translate.template.TranslateTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Monitored
@RestController
@RequestMapping("/api/v1/admin/translate/templates")
public class TranslateTemplateController extends BaseCRUDController<TranslateTemplateCreateRequest, TranslateTemplateUpdateRequest, TranslateTemplateResponse> {

    private final TranslateTemplateService translateTemplateService;

    public TranslateTemplateController(TranslateTemplateService translateTemplateService) {
        this.translateTemplateService = translateTemplateService;
    }

    @Override
    public BaseService<TranslateTemplateCreateRequest, TranslateTemplateUpdateRequest, TranslateTemplateResponse> getService() {
        return translateTemplateService;
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<TranslateTemplateResponse>> delete(@PathVariable Long id) {
        throw new BodyGuardException("Translate template cannot be deleted.");
    }
}
