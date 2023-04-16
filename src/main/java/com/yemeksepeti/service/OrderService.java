package com.yemeksepeti.service;

import com.yemeksepeti.dto.request.SendOrderRequestDto;
import com.yemeksepeti.dto.response.ShowOrderResponseDto;
import com.yemeksepeti.dto.response.ShowPreviousOrdersByCustomerIdDto;
import com.yemeksepeti.exception.ErrorType;
import com.yemeksepeti.exception.YemeksepetiManagerException;
import com.yemeksepeti.mapper.IOrderMapper;
import com.yemeksepeti.repository.IOrderRepository;
import com.yemeksepeti.repository.entity.*;
import com.yemeksepeti.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService extends ServiceManager<Order,Long> {
    private final IOrderRepository orderRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final RestaurantService restaurantService;
    public OrderService(IOrderRepository orderRepository,
                        ProductService productService,
                        CustomerService customerService,
                        RestaurantService restaurantService){
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.productService = productService;
        this.restaurantService = restaurantService;
    }

    public String sendOrder(SendOrderRequestDto dto){
        Optional<Customer> optionalCustomer = customerService.findById(dto.getCustomerId());
        if(optionalCustomer.isEmpty())
            throw new YemeksepetiManagerException(ErrorType.CUSTOMER_NOT_FOUND);
        else if (!optionalCustomer.get().getStatus().equals(EStatus.ACTIVE)) {
            throw new YemeksepetiManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
        Set<Long> restaurantIdList = new HashSet<>();
        dto.getProductId().forEach(productId -> {
            Optional<Product> optionalProduct = productService.findById(productId);
            if(optionalProduct.isEmpty())
            throw new YemeksepetiManagerException(ErrorType.PRODUCT_NOT_FOUND);
            restaurantIdList.add(optionalProduct.get().getRestaurantId());
        });
        if(restaurantIdList.size()>1)
            throw new YemeksepetiManagerException(ErrorType.DIFFERENT_RESTAURANT);
        orderRepository.save(IOrderMapper.INSTANCE.fromSendOrderRequestDtoToOrder(dto));
        return "Siparişiniz başarılıdır.";
    }

    /**
     * Kişinin bakiyesi güncellenmelei Balance'İn alışverişten sonra ve sırasında kontrol edilmeli alabiliyor mu?
     * @param customerId
     * @return
     */

    public List<ShowPreviousOrdersByCustomerIdDto> findOrdersByCustomer(Long customerId){
        List<ShowPreviousOrdersByCustomerIdDto> orderByCustomerList = new ArrayList<>();
        List<Order> orderList = orderRepository.findAllByCustomerId(customerId);
        String customerName = customerService.findCustomerNameById(customerId);
        if(customerName.isEmpty())
            throw new YemeksepetiManagerException(ErrorType.CUSTOMER_NOT_FOUND);
        orderList.forEach(order->{
            orderByCustomerList.add(ShowPreviousOrdersByCustomerIdDto.builder()
                    .orderId(order.getId())
                    .customerName(customerName)
                    .productNameList(productService.findProductByOrderId(order.getId()))
                    .restaurantName(restaurantService.findRestaurantNameByOrderId(order.getId()))
                    .build());
        });
        return orderByCustomerList;
    }

    public List<ShowOrderResponseDto> findOrdersByRestaurant(Long restaurantId){
        List<ShowOrderResponseDto> orderListInfo = new ArrayList<>();
        Optional<Restaurant> optionalRestaurant = restaurantService.findById(restaurantId);
        if(optionalRestaurant.isEmpty())
            throw new YemeksepetiManagerException(ErrorType.RESTAURANT_NOT_FOUND);
        Set<Long> orderList = orderRepository.findOrdersByRestaurant(restaurantId);
        if(orderList.isEmpty())
            throw new YemeksepetiManagerException(ErrorType.RESTAURANT_NO_ORDER);
        String restaurantName = restaurantService.findRestaurantNameById(restaurantId);
        orderList.forEach(order->{
            orderListInfo.add(ShowOrderResponseDto.builder()
                    .restaurantName(restaurantName)
                    .orderId(order)
                    .customerName(customerService.findById(orderRepository.findByCustomerIdByOrder(order)).get().getName())
                    .productNameList(productService.findProductByOrderId(order))
                    .build());
        });
        return orderListInfo;
    }

    public List<ShowOrderResponseDto> findOrders(){
        List<ShowOrderResponseDto> orderListInfo = new ArrayList<>();
        List<Order> orderList = orderRepository.findAll();
        orderList.forEach(order->{
            orderListInfo.add(ShowOrderResponseDto.builder()
                    .orderId(order.getId())
                    .customerName(customerService.findById(order.getCustomerId()).get().getName())
                    .productNameList(productService.findProductByOrderId(order.getId()))
                    .restaurantName(restaurantService.findRestaurantNameByOrderId(order.getId()))
                    .build());
        });
        return orderListInfo;
    }


}


