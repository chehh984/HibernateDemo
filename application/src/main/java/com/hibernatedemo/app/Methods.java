package com.hibernatedemo.app;

import com.hibernatedemo.model.CustomerEntity;
import com.hibernatedemo.model.EmployeeEntity;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import java.util.stream.Stream;

public class Methods {

    private EntityManagerFactory managerFactory;

    public Methods(EntityManagerFactory managerFactory){
        this.managerFactory = managerFactory;
    }

    public CustomerEntity getCustomerCq(String name)
    {
        EntityManager manager = managerFactory.createEntityManager();

        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<CustomerEntity> query = cb.createQuery(CustomerEntity.class);
        Root<CustomerEntity> root = query.from(CustomerEntity.class);

        query.select(root).where(cb.equal(root.get("name"),name));

        return manager.createQuery(query).getSingleResult();
    }

    public EmployeeEntity getEmployeeCq(String name){

        EntityManager manager = managerFactory.createEntityManager();
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> query = cb.createQuery(EmployeeEntity.class);

        Root<EmployeeEntity> root = query.from(EmployeeEntity.class);

        query.select(root).where(cb.equal(root.get("name"),name));

        return manager.createQuery(query).getSingleResult();
    }

    public EmployeeEntity getEmployeeJoin(String name){

        EntityManager manager = managerFactory.createEntityManager();
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> query = cb.createQuery(EmployeeEntity.class);
        Root<EmployeeEntity> root = query.from(EmployeeEntity.class);

        root.fetch("shopEntity", JoinType.LEFT);

        query.select(root).where(cb.equal(root.get("name"),name));

        return manager.createQuery(query).getSingleResult();
    }

    public EmployeeEntity getEmployeeStreamed(String name){

        EntityManager manager = managerFactory.createEntityManager();
        Session session = (Session)manager.getDelegate();

        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<EmployeeEntity> query = cb.createQuery(EmployeeEntity.class);
        Root<EmployeeEntity> root = query.from(EmployeeEntity.class);

        root.fetch("shopEntity", JoinType.LEFT);

        Stream<EmployeeEntity> employeeStream = session.createQuery(query).stream();


        return employeeStream.filter(x -> x.getName().equals(name)).findFirst().get();
    }

    public void insertEmployee(EmployeeEntity employeeEntity)
    {
        EntityManager manager = managerFactory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try{
            transaction.begin();
            manager.persist(employeeEntity);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }
    }

    public void updateEmployee(EmployeeEntity employeeEntity){
        EntityManager manager = managerFactory.createEntityManager();
        EntityTransaction transaction = manager.getTransaction();

        try{
            transaction.begin();
            manager.merge(employeeEntity);
            transaction.commit();
        }catch (Exception e){
            e.printStackTrace();
            transaction.rollback();
        }
    }
}
