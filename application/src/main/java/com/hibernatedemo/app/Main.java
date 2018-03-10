package com.hibernatedemo.app;


import com.hibernatedemo.config.HibernateConfig;
import com.hibernatedemo.model.CustomerEntity;
import com.hibernatedemo.model.EmployeeEntity;
import com.hibernatedemo.model.ShopEntity;

import javax.persistence.EntityManagerFactory;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {


        EntityManagerFactory myFactory = HibernateConfig.getEntityManagerFactory();

        Methods methods = new Methods(myFactory);

        CustomerEntity customer = methods.getCustomerCq("Damir");

        EmployeeEntity employeeEntity = methods.getEmployeeCq("Mujo");
        EmployeeEntity joinedEmployee = methods.getEmployeeJoin("Mujo");

        ShopEntity shopEntity = employeeEntity.getShopEntity();
        ShopEntity joinedShopEntity = employeeEntity.getShopEntity();

        EmployeeEntity streamedEmployee = methods.getEmployeeStreamed("Mujo");

        streamedEmployee.setName("NoviMujo");
        methods.updateEmployee(streamedEmployee);

        EmployeeEntity newEmployee = new EmployeeEntity();
        newEmployee.setName("noviSuljo");

        methods.insertEmployee(newEmployee);


        System.out.println("Hello hibernate!");
    }

}
