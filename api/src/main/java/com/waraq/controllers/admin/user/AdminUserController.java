package com.waraq.controllers.admin.user;

import com.waraq.controllers.BaseCRUDController;
import com.waraq.dto.ResponseList;
import com.waraq.dto.admin.AdminCreateUserRequest;
import com.waraq.dto.admin.AdminUserResponse;
import com.waraq.dto.admin.AdminUpdateUserRequest;
import com.waraq.dto.admin.UpdateEnabledPlayerRequest;
import com.waraq.exceptions.BodyGuardException;
import com.waraq.http_response.CODE;
import com.waraq.http_response.Response;
import com.waraq.logging.Monitored;
import com.waraq.service.base.BaseService;
import com.waraq.service.user.admin.AdminUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Monitored
@RestController
@RequestMapping("/api/v1/admin/users")
public class AdminUserController extends BaseCRUDController<AdminCreateUserRequest, AdminUpdateUserRequest, AdminUserResponse> {

    private final AdminUserService adminUserService;
    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @Override
    public BaseService<AdminCreateUserRequest, AdminUpdateUserRequest, AdminUserResponse> getService() {
        return adminUserService;
    }

    @PatchMapping("/enable")
    public ResponseEntity<Response<Void>> updateEnabledStatus(@RequestBody UpdateEnabledPlayerRequest request) {
        adminUserService.updateEnabledStatus(request);
        Response<Void> response = Response.<Void>builder()
                .code(CODE.OK.getId())
                .success(true)
                .message(CODE.OK.name())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<AdminUserResponse>> delete(@PathVariable Long id) {
        throw new BodyGuardException("Admin cannot delete user.");
    }
}
