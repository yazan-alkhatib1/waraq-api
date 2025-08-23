package com.waraq.service.services.user;

import com.waraq.dto.user.request.UserSignupRequest;
import com.waraq.dto.user.response.UserSignupResponse;

public interface UserService {

    UserSignupResponse signup(UserSignupRequest request);
}
