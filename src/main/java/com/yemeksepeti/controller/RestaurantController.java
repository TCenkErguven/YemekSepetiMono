package com.yemeksepeti.controller;

import com.yemeksepeti.dto.request.SaveRestaurantRequestDto;
import com.yemeksepeti.dto.request.UpdateRestaurantRequestDto;
import com.yemeksepeti.dto.response.SaveRestaurantResponseDto;
import com.yemeksepeti.dto.response.UpdateRestaurantResponseDto;
import com.yemeksepeti.repository.entity.Restaurant;
import com.yemeksepeti.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.yemeksepeti.constants.EndPoints.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(RESTAURANT)
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService service;

    @PostMapping(SAVE)
    public ResponseEntity<SaveRestaurantResponseDto> save(@RequestBody @Valid SaveRestaurantRequestDto dto){
        return ResponseEntity.ok(service.save(dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Restaurant>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(UPDATE)
    public ResponseEntity<UpdateRestaurantResponseDto> update(UpdateRestaurantRequestDto dto){
        return ResponseEntity.ok(service.update(dto));
    }

}
