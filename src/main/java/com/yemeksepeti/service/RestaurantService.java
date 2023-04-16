package com.yemeksepeti.service;

import com.yemeksepeti.dto.request.SaveRestaurantRequestDto;
import com.yemeksepeti.dto.request.UpdateRestaurantRequestDto;
import com.yemeksepeti.dto.response.SaveRestaurantResponseDto;
import com.yemeksepeti.dto.response.UpdateRestaurantResponseDto;
import com.yemeksepeti.exception.ErrorType;
import com.yemeksepeti.exception.YemeksepetiManagerException;
import com.yemeksepeti.mapper.IRestaurantMapper;
import com.yemeksepeti.repository.IRestaurantRepository;
import com.yemeksepeti.repository.entity.Restaurant;
import com.yemeksepeti.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantService extends ServiceManager<Restaurant,Long> {
    private final IRestaurantRepository restaurantRepository;
    public RestaurantService(IRestaurantRepository restaurantRepository){
        super(restaurantRepository);
        this.restaurantRepository = restaurantRepository;
    }
    public SaveRestaurantResponseDto save(SaveRestaurantRequestDto dto){
        Restaurant restaurant = IRestaurantMapper.INSTANCE.fromSaveRestaurantRequestDtoToRestaurant(dto);
        restaurantRepository.save(restaurant);
        return IRestaurantMapper.INSTANCE.fromRestaurantToSaveRestaurantResponseDto(restaurant);
    }

    public boolean existsByIdRestaurant(Long id){
       return restaurantRepository.existsById(id);
    }

    public String findRestaurantNameByOrderId(Long orderId){
        String restaurantName = restaurantRepository.findRestaurantNameByOrderId(orderId);
        if(restaurantName.isEmpty())
            throw new YemeksepetiManagerException(ErrorType.RESTAURANT_NOT_FOUND);
        return restaurantName;
    }

    public String findRestaurantNameById(Long restaurantId){
        String restaurantName = restaurantRepository.findRestaurantNameById(restaurantId);
        if(restaurantName.isEmpty())
            throw new YemeksepetiManagerException(ErrorType.RESTAURANT_NOT_FOUND);
        return restaurantName;
    }

    public UpdateRestaurantResponseDto update(UpdateRestaurantRequestDto dto){
            Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(dto.getId());
            if(dto.getPoint() != null) {
                if ((dto.getPoint() > 5 || dto.getPoint() < 0)) {
                    throw new YemeksepetiManagerException(ErrorType.POINT_ERROR);
                }
            }
            if (optionalRestaurant.isPresent()) {
                update(IRestaurantMapper.INSTANCE.fromUpdateRestaurantDtoToRestaurant(dto, optionalRestaurant.get()));
                UpdateRestaurantResponseDto responseDto = IRestaurantMapper.INSTANCE.fromRestaurantToUpdateResponseDto(optionalRestaurant.get());
                return responseDto;
            }
            throw new YemeksepetiManagerException(ErrorType.RESTAURANT_NOT_FOUND);

    }



}
