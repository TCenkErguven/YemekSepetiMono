package com.yemeksepeti.controller;

import com.yemeksepeti.dto.request.SaveProductRequestDto;
import com.yemeksepeti.dto.response.FindProductByRestaurantIdResponseDto;
import com.yemeksepeti.dto.response.SaveProductResponseDto;
import com.yemeksepeti.repository.entity.Product;
import com.yemeksepeti.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.yemeksepeti.constants.EndPoints.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(PRODUCT)
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;

    @PostMapping(SAVE)
    public ResponseEntity<SaveProductResponseDto> save(@RequestBody @Valid SaveProductRequestDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Product>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(FIND_PRODUCT_BY_RESTAURANT_ID + "/{restaurantId}")
    public ResponseEntity<List<FindProductByRestaurantIdResponseDto>> findProductByRestaurantId(@PathVariable Long restaurantId){
        return ResponseEntity.ok(service.findProductByRestaurantId(restaurantId));
    }

}
