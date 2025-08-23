package com.waraq.service.user.admin;
import com.waraq.dto.admin.AdminCreateUserRequest;
import com.waraq.dto.admin.AdminUserResponse;
import com.waraq.dto.admin.AdminUpdateUserRequest;
import com.waraq.dto.admin.UpdateEnabledPlayerRequest;
import com.waraq.service.base.BaseService;

public interface AdminUserService extends BaseService<AdminCreateUserRequest, AdminUpdateUserRequest, AdminUserResponse> {

    void updateEnabledStatus(UpdateEnabledPlayerRequest updateDto);
}
