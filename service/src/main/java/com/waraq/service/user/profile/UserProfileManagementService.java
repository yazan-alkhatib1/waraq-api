package com.waraq.service.user.profile;

import com.waraq.dto.admin.UserResponse;
import com.waraq.dto.admin.profile.UpdateProfileRequest;
import com.waraq.dto.admin.profile.UpdateProfilePasswordRequest;

public interface UserProfileManagementService {

    UserResponse retrieveProfile();

    void updateOldPassword(UpdateProfilePasswordRequest request);

    UserResponse updateProfile(UpdateProfileRequest request);

}
