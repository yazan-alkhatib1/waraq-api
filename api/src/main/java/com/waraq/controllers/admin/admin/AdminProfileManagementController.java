package com.waraq.controllers.admin.admin;

import com.waraq.dto.admin.UserResponse;
import com.waraq.dto.admin.profile.UpdateProfileRequest;
import com.waraq.dto.admin.profile.UpdateProfilePasswordRequest;
import com.waraq.http_response.CODE;
import com.waraq.http_response.Response;
import com.waraq.logging.Monitored;
import com.waraq.service.admin.AdminProfileManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Monitored
@RestController
@RequestMapping("/api/v1/admin/profile")
@Slf4j
public class AdminProfileManagementController {

    private final AdminProfileManagementService adminProfileManagementService;
    public AdminProfileManagementController(AdminProfileManagementService adminProfileManagementService) {
        this.adminProfileManagementService = adminProfileManagementService;
    }

    @GetMapping
    public ResponseEntity<Response<UserResponse>> viewAdminProfile() {
        UserResponse userResponse = adminProfileManagementService.retrieveProfile();
        Response<UserResponse> response = Response.<UserResponse>builder()
                .data(userResponse)
                .success(true)
                .code(CODE.OK.getId())
                .message(CODE.OK.name()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<Response<Void>> updateOldPassword(@RequestBody UpdateProfilePasswordRequest request) {
        adminProfileManagementService.updateOldPassword(request);
        Response<Void> response = Response.<Void>builder()
                .success(true)
                .code(CODE.OK.getId())
                .message(CODE.OK.name()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Response<UserResponse>> updateAdminProfile(@RequestBody UpdateProfileRequest request) {
        UserResponse userResponse = adminProfileManagementService.updateAdminProfile(request);
        Response<UserResponse> response = Response.<UserResponse>builder()
                .data(userResponse)
                .success(true)
                .code(CODE.OK.getId())
                .message(CODE.OK.name()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
