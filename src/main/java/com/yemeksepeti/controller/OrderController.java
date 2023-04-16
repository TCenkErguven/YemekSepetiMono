package com.yemeksepeti.controller;

import com.yemeksepeti.dto.request.SendOrderRequestDto;
import com.yemeksepeti.dto.response.ShowOrderResponseDto;
import com.yemeksepeti.dto.response.ShowPreviousOrdersByCustomerIdDto;
import com.yemeksepeti.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.yemeksepeti.constants.EndPoints.*;

import java.util.List;

@RestController
@RequestMapping(ORDER)
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @PostMapping(SEND_ORDER)
    public ResponseEntity<String> sendOrder(@RequestBody @Valid SendOrderRequestDto dto){
        return ResponseEntity.ok(service.sendOrder(dto));
    }

    @GetMapping(SHOW_PREIVOUS_ORDERS_BY_CUSTOMER_ID + "/{customerId}")
    public ResponseEntity<List<ShowPreviousOrdersByCustomerIdDto>> showPreviousOrdersByCustomerId(@PathVariable Long customerId){
        return ResponseEntity.ok(service.findOrdersByCustomer(customerId));
    }

    @GetMapping(FIND_ORDERS_BY_RESTAURANT + "/{restaurantId}")
    public ResponseEntity<List<ShowOrderResponseDto>> findOrdersByRestaurant(@PathVariable Long restaurantId){
        return ResponseEntity.ok(service.findOrdersByRestaurant(restaurantId));
    }

    @GetMapping(SHOW_ORDERS)
    public ResponseEntity<List<ShowOrderResponseDto>> showOrders(){
        return ResponseEntity.ok(service.findOrders());
    }
}
