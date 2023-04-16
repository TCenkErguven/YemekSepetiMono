package com.yemeksepeti.dto.response;

import com.yemeksepeti.repository.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ShowPreviousOrdersByCustomerIdDto {
    Long orderId;
    String customerName;
    List<Product> productNameList;
    String restaurantName;
}
