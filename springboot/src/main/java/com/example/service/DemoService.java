package com.example.service;

import com.example.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoService
        implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean {

    @Autowired private UserRepository repository;

    public DemoService() {
        System.out.println("1. Constructor");
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("3. BeanNameAware : " + name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        System.out.println("4. BeanFactoryAware");
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        System.out.println("5. ApplicationContextAware");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("7. @PostConstruct");
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("8. afterPropertiesSet()");
    }

    public void customInit() {
        System.out.println("9. customInit()");
    }

    @Transactional
    public void save() {
        System.out.println("Saving User");
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("12. @PreDestroy");
    }

    public void customDestroy() {
        System.out.println("13. customDestroy()");
    }
}
