package com.yemeksepeti.repository;

import com.yemeksepeti.repository.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRestaurantRepository extends JpaRepository<Restaurant,Long> {
    boolean existsById(Long id);

    @Query(nativeQuery = true,value = "select r.name from order_product_id as op inner join tbl_product \n" +
            "as p on p.id = op.product_id inner join tbl_restaurant as r on r.id = p.restaurant_id\n" +
            "where order_id = ?1\n" +
            "LIMIT 1")
    String findRestaurantNameByOrderId(Long orderId);

    @Query(nativeQuery = true,value = "select name from tbl_restaurant where id = ?1")
    String findRestaurantNameById(Long restaurantId);
}
