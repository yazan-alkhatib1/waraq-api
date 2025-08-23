package com.waraq.service.admin;


import com.waraq.dto.admin.*;
import com.waraq.service.base.BaseService;

public interface AdminService extends BaseService<CreateUserRequest, UpdateUserRequest, UserResponse> {

    void updateEnabled(UpdateEnabledAdminRequest request);

    void updatePassword(UpdatePasswordRequest request);

    UserResponse findAdminByUserId(Long userId);


}
