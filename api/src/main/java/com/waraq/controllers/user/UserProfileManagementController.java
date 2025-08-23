package com.waraq.controllers.user;

import com.waraq.dto.admin.UserResponse;
import com.waraq.dto.admin.profile.UpdateProfileRequest;
import com.waraq.dto.admin.profile.UpdateProfilePasswordRequest;
import com.waraq.http_response.CODE;
import com.waraq.http_response.Response;
import com.waraq.logging.Monitored;
import com.waraq.service.user.profile.UserProfileManagementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Monitored
@RestController
@RequestMapping("/api/v1/user/profile")
@Slf4j
public class UserProfileManagementController {

    private final UserProfileManagementService userProfileManagementService;
    public UserProfileManagementController(UserProfileManagementService userProfileManagementService) {
        this.userProfileManagementService = userProfileManagementService;
    }

    @GetMapping
    public ResponseEntity<Response<UserResponse>> viewProfile() {
        UserResponse userResponse = userProfileManagementService.retrieveProfile();
        Response<UserResponse> response = Response.<UserResponse>builder()
                .data(userResponse)
                .success(true)
                .code(CODE.OK.getId())
                .message(CODE.OK.name()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/password")
    public ResponseEntity<Response<Void>> updateOldPassword(@RequestBody UpdateProfilePasswordRequest request) {
        userProfileManagementService.updateOldPassword(request);
        Response<Void> response = Response.<Void>builder()
                .success(true)
                .code(CODE.OK.getId())
                .message(CODE.OK.name()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Response<UserResponse>> updateProfile(@RequestBody UpdateProfileRequest request) {
        UserResponse userResponse = userProfileManagementService.updateProfile(request);
        Response<UserResponse> response = Response.<UserResponse>builder()
                .data(userResponse)
                .success(true)
                .code(CODE.OK.getId())
                .message(CODE.OK.name()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
