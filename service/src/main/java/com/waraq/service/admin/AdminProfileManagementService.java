package com.waraq.service.admin;

import com.waraq.dto.admin.UserResponse;
import com.waraq.dto.admin.profile.UpdateProfileRequest;
import com.waraq.dto.admin.profile.UpdateProfilePasswordRequest;

public interface AdminProfileManagementService {

    UserResponse retrieveProfile();

    void updateOldPassword(UpdateProfilePasswordRequest request);

    UserResponse updateAdminProfile(UpdateProfileRequest request);

}
