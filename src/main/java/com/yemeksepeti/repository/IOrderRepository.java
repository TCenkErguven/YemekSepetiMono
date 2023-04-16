package com.yemeksepeti.repository;

import com.yemeksepeti.repository.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IOrderRepository extends JpaRepository<Order,Long> {
    List<Order> findAllByCustomerId(Long customerId);

    @Query(nativeQuery = true, value = "select opi.order_id from tbl_restaurant as r inner join tbl_product as p on p.restaurant_id = r.id\n" +
            "inner join order_product_id as opi on opi.product_id = p.id\n" +
            "inner join tbl_order as o on o.id = opi.order_id\n" +
            "inner join tbl_customer as c on c.id = o.customer_id\n" +
            "where r.id = ?1")
    Set<Long> findOrdersByRestaurant(Long restaurantId);

    @Query(nativeQuery = true, value = "select customer_id from tbl_order where id = ?1")
    Long findByCustomerIdByOrder(Long restaurantId);
}
