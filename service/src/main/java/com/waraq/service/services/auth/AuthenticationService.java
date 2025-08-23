package com.waraq.service.services.auth;

import com.waraq.dto.auth.request.LoginRequest;
import com.waraq.dto.auth.response.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);
}
