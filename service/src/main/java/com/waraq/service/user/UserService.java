package com.waraq.service.user;

import com.padelgate.bo.user.VerifyEmailRequest;
import com.padelgate.bo.user.VerifyEmailResponse;
import com.padelgate.bo.user.VerifyPhoneNumberRequest;
import com.padelgate.bo.user.VerifyPhoneNumberResponse;

public interface UserService {

    VerifyPhoneNumberResponse verifyPhoneNumber(VerifyPhoneNumberRequest request);

    VerifyEmailResponse verifyEmail(VerifyEmailRequest request);
}
