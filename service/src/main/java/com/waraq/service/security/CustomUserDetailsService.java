package com.waraq.service.security;

import com.waraq.dto.auth.CustomUserDetails;
import com.waraq.exceptions.NotFoundException;
import com.waraq.repositories.RepositoryFactory;
import com.waraq.repository.entities.user.UserEntity;
import com.waraq.repository.repositories.user.UserRepository;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.waraq.repository.enums.Role.CLIENT;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final RepositoryFactory repositoryFactory;

    public CustomUserDetailsService(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }

    private static CustomUserDetails buildUserDetailsByUser(UserEntity merchant) {
        return CustomUserDetails
                .builder()
                .id(merchant.getId())
                .isEnabled(merchant.getIsEnabled())
                .firstName(merchant.getFirstName())
                .lastName(merchant.getLastName())
                .email(merchant.getEmail())
                .password(merchant.getPassword())
                .role(CLIENT.getRole())
                .build();
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = getUserEntityByEmail(username);
        return buildUserDetailsByUser(userEntity);
    }

    private UserEntity getUserEntityByEmail(String username) {
        return repositoryFactory.getRepository(UserRepository.class)
                .findOne(Example.of(UserEntity.builder()
                        .isEnabled(true)
                        .isActive(true)
                        .email(username.toLowerCase())
                        .build()))
                .orElseThrow(() -> new NotFoundException("No merchant associated with this email was found"));
    }
}
