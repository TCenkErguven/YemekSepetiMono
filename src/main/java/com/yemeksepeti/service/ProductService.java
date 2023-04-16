package com.yemeksepeti.service;

import com.yemeksepeti.dto.request.SaveProductRequestDto;
import com.yemeksepeti.dto.response.FindProductByRestaurantIdResponseDto;
import com.yemeksepeti.dto.response.SaveProductResponseDto;
import com.yemeksepeti.exception.ErrorType;
import com.yemeksepeti.exception.YemeksepetiManagerException;
import com.yemeksepeti.mapper.IProductMapper;
import com.yemeksepeti.repository.IProductRepository;
import com.yemeksepeti.repository.entity.Product;
import com.yemeksepeti.repository.entity.Restaurant;
import com.yemeksepeti.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService extends ServiceManager<Product,Long> {
    private final IProductRepository productRepository;
    private final RestaurantService restaurantService;
    public ProductService(IProductRepository productRepository,
                          RestaurantService restaurantService){
        super(productRepository);
        this.productRepository = productRepository;
        this.restaurantService = restaurantService;
    }

    public SaveProductResponseDto save(SaveProductRequestDto dto){
        if(!restaurantService.existsByIdRestaurant(dto.getRestaurantId()))
            throw new YemeksepetiManagerException(ErrorType.RESTAURANT_NOT_FOUND);
        Product product = IProductMapper.INSTANCE.fromSaveProductRequestDtoToProduct(dto);
        List<Product> productList = productRepository.findProductByRestaurantId(dto.getRestaurantId());
        productList.forEach(item ->{
            if(item.getName().equals(dto.getName()))
                throw new YemeksepetiManagerException(ErrorType.DUPLICATE_PRODUCT);
        });
        productRepository.save(product);
        return IProductMapper.INSTANCE.fromProductToSaveProductResponseDto(product);
    }
    public List<Product> findProductByOrderId(Long orderId){
        List<Product> productList = productRepository.findProductByOrderId(orderId);
        if(productList.isEmpty())
            throw new YemeksepetiManagerException(ErrorType.PRODUCT_NOT_FOUND);
        return productList;
    }

    public List<FindProductByRestaurantIdResponseDto> findProductByRestaurantId(Long restaurantId){
        List<Product> productList = productRepository.findProductByRestaurantId(restaurantId);
        List<FindProductByRestaurantIdResponseDto> productResponseDtos = new ArrayList<>();
        Optional<Restaurant> optionalRestaurant = restaurantService.findById(restaurantId);
        if(optionalRestaurant.isEmpty())
            throw new YemeksepetiManagerException(ErrorType.RESTAURANT_NOT_FOUND);
        if (productList.isEmpty())
            throw new YemeksepetiManagerException(ErrorType.PRODUCT_NOT_FOUND);
        productList.forEach(product -> {
            productResponseDtos.add(FindProductByRestaurantIdResponseDto.builder()
                            .restaurantName(optionalRestaurant.get().getName())
                            .name(product.getName())
                            .category(product.getCategory())
                            .cost(product.getCost())
                    .build());
        });
        return productResponseDtos;
    }

}
