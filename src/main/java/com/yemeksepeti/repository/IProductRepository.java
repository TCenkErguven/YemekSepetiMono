package com.yemeksepeti.repository;

import com.yemeksepeti.repository.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {
    boolean existsById(Long id);

    @Query(nativeQuery = true, value = "select * from order_product_id as op inner join tbl_product as p on p.id = op.product_id\n" +
            "where order_id = ?1")
    List<Product> findProductByOrderId(Long orderId);

    List<Product> findProductByRestaurantId(Long restaurantId);

}
