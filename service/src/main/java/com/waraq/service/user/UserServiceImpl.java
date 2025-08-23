package com.waraq.service.user;

import com.waraq.repositories.RepositoryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final RepositoryFactory repositoryFactory;

    public UserServiceImpl(RepositoryFactory repositoryFactory) {
        this.repositoryFactory = repositoryFactory;
    }


}
