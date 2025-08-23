package com.waraq.repositories;

import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class RepositoryFactory {

    private final ApplicationContext context;

    public RepositoryFactory(ApplicationContext context) {
        this.context = context;
    }

    public <T extends JpaRepository<?, Long>> T getRepository(Class<T> klass) {
        return context.getBean(klass);
    }
}
