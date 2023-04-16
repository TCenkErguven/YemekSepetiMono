package com.yemeksepeti.repository;

import com.yemeksepeti.repository.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<Customer,Long> {
    Optional<Customer> findByEmailAndPassword(String email,String password);
    boolean existsById(Long id);
    @Query("select c.name from Customer c where c.id = ?1")
    String findCustomerNameById(Long id);


}
