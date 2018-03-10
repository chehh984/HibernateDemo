package com.hibernatedemo.service;


import com.hibernatedemo.config.HibernateConfig;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {


        EntityManagerFactory myFactory = HibernateConfig.getEntityManagerFactory();


        System.out.println("Hello hibernate 2!");
    }

}
