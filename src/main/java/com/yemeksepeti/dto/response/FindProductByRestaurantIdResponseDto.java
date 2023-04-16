package com.yemeksepeti.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FindProductByRestaurantIdResponseDto {

    String name;
    String category;
    Double cost;
    String restaurantName;
}
