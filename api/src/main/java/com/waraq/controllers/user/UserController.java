//package com.waraq.controllers.user;
//
//import com.waraq.dto.user.request.UserSignupRequest;
//import com.waraq.dto.user.response.UserSignupResponse;
//import com.waraq.http_response.CODE;
//import com.waraq.http_response.Response;
//import com.waraq.logging.Monitored;
//import com.waraq.service.services.user.UserService;
//import org.springframework.context.MessageSource;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import static org.springframework.http.HttpStatus.CREATED;
//
//@RestController
//@RequestMapping("/api/v1/users")
//@Monitored
//public class UserController {
//
//    private final UserService userService;
//
//    private final MessageSource messageSource;
//
//    public UserController(UserService userService, MessageSource messageSource) {
//        this.userService = userService;
//        this.messageSource = messageSource;
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<Response<UserSignupResponse>> signup(@RequestBody UserSignupRequest request) {
//
//        Response<UserSignupResponse> response = Response.<UserSignupResponse>builder()
//                .data(userService.signup(request))
//                .code(CODE.CREATED.getId())
//                .message(messageSource.getMessage("message.created", null, LocaleContextHolder.getLocale()))
//                .success(true)
//                .build();
//
//        return new ResponseEntity<>(response, CREATED);
//    }
//}
