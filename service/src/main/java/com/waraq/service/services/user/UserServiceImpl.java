//package com.waraq.service.services.user;
//
//import com.waraq.dto.user.request.UserSignupRequest;
//import com.waraq.dto.user.response.UserSignupResponse;
//import com.waraq.repositories.RepositoryFactory;
//import com.waraq.repository.entities.address.AddressEntity;
//import com.waraq.repository.entities.user.UserEntity;
//import com.waraq.repository.repositories.address.AddressRepository;
//import com.waraq.repository.repositories.user.UserRepository;
//import com.waraq.service.helpers.JwtTokenManager;
//import com.waraq.service.helpers.validators.EmailValidator;
//import com.waraq.service.helpers.validators.PasswordValidator;
//import com.waraq.service.helpers.validators.PhoneNumberValidator;
//import com.waraq.service.mappers.user.UserMapper;
//import com.waraq.validator.CompositeValidator;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.MessageSource;
//import org.springframework.context.i18n.LocaleContextHolder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static com.waraq.validator.CompositeValidator.isStringEmpty;
//import static com.waraq.validator.CompositeValidator.joinViolations;
//import static java.util.Objects.isNull;
//import static java.util.Objects.nonNull;
//
//@Service
//@Slf4j
//public class UserServiceImpl implements UserService {
//
//    private final RepositoryFactory repositoryFactory;
//
//    private final UserMapper userMapper;
//
//    private final AddressMapper addressMapper;
//
//    private final MessageSource messageSource;
//
//    public UserServiceImpl(RepositoryFactory repositoryFactory, JwtTokenManager jwtTokenManager,
//                           BCryptPasswordEncoder bCryptPasswordEncoder, MessageSource messageSource) {
//        this.repositoryFactory = repositoryFactory;
//        this.messageSource = messageSource;
//        this.userMapper = new UserMapper(jwtTokenManager, bCryptPasswordEncoder);
//        addressMapper = new AddressMapper();
//    }
//
//    @Override
//    @Transactional
//    public UserSignupResponse signup(UserSignupRequest request) {
//        log.info("Creating user using signup request for email : {}", request.getEmail());
//
//        validateSignupRequest(request);
//
//        UserEntity userEntity = getRepository().save(userMapper.mapSignupRequestToEntity(request));
//
//        postAdd(userEntity, request);
//
//        return userMapper.mapUserEntityToSignupResponse(userEntity);
//    }
//
//    private void validateSignupRequest(UserSignupRequest request) {
//        List<String> violations = new CompositeValidator<UserSignupRequest, String>()
//                .addValidator(p -> isStringEmpty(p.getFirstName()), messageSource.getMessage("error.message.first.name.not.empty", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> isStringEmpty(p.getLastName()), messageSource.getMessage("error.message.last.name.not.empty", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> isStringEmpty(p.getEmail()), messageSource.getMessage("error.message.email.not.empty", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> nonNull(p.getEmail()) && !EmailValidator.validateEmailFormat(p.getEmail()), messageSource.getMessage("error.message.email.format", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> isStringEmpty(p.getPhoneNumber()),
//                        messageSource.getMessage("error.message.phone.number.not.empty", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> nonNull(p.getPhoneNumber()) && !PhoneNumberValidator.validatePhoneNumberFormat(p.getPhoneNumber()),
//                        messageSource.getMessage("error.message.phone.number.format", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> isStringEmpty(p.getPassword()), messageSource.getMessage("error.message.password.not.empty", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> isStringEmpty(p.getConfirmPassword()), messageSource.getMessage("error.message.confirm.password.not.empty", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> nonNull(p.getPassword()) && nonNull(p.getConfirmPassword()) &&
//                        !p.getPassword().equals(p.getConfirmPassword()), messageSource.getMessage("error.message.password.not.match", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> nonNull(p.getPassword()) && !PasswordValidator.validatePassword(p.getPassword()),
//                        messageSource.getMessage("error.message.password.format", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> nonNull(p.getEmail()) && EmailValidator.validateEmailUniquenessOnCreation(p.getEmail(), getRepository()),
//                        messageSource.getMessage("error.message.email.already.exist", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> nonNull(p.getPhoneNumber()) && PhoneNumberValidator.validatePhoneNumberUniquenessOnCreation(p.getPhoneNumber(), getRepository()),
//                        messageSource.getMessage("error.message.phone.number.already.exist", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> isNull(p.getAddress()), messageSource.getMessage("error.message.address.not.empty", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> nonNull(p.getAddress()) && isStringEmpty(p.getAddress().getCity()), messageSource.getMessage("error.message.city.not.empty", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> nonNull(p.getAddress()) && isNull(p.getAddress().getCountryCode()), messageSource.getMessage("error.message.country.code.not.empty", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> nonNull(p.getAddress()) && isStringEmpty(p.getAddress().getState()), messageSource.getMessage("error.message.state.not.empty", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> nonNull(p.getAddress()) && isStringEmpty(p.getAddress().getStreet()), messageSource.getMessage("error.message.street.not.empty", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> nonNull(p.getAddress()) && isStringEmpty(p.getAddress().getHouseNumber()), messageSource.getMessage("error.message.house.number.not.empty", null, LocaleContextHolder.getLocale()))
//                .addValidator(p -> nonNull(p.getAddress()) && isStringEmpty(p.getAddress().getPostalCode()), messageSource.getMessage("error.message.postal.code.not.empty", null, LocaleContextHolder.getLocale()))
//                .validate(request);
//        joinViolations(violations);
//    }
//
//    private void postAdd(UserEntity userEntity, UserSignupRequest request) {
//        AddressEntity addressEntity = repositoryFactory.getRepository(AddressRepository.class)
//                .save(addressMapper.mapCreateAddressRequestToEntity(request.getAddress()));
//
//        userEntity.setAddress(addressEntity);
//
//        getRepository().save(userEntity);
//    }
//
//    private UserRepository getRepository() {
//        return repositoryFactory.getRepository(UserRepository.class);
//    }
//}
