package com.yemeksepeti.controller;

import com.yemeksepeti.dto.request.ActivateStatusRequestDto;
import com.yemeksepeti.dto.request.AddFundRequestDto;
import com.yemeksepeti.dto.request.LoginRequestDto;
import com.yemeksepeti.dto.request.RegisterRequestDto;
import com.yemeksepeti.dto.response.RegisterResponseDto;
import com.yemeksepeti.exception.ErrorType;
import com.yemeksepeti.exception.YemeksepetiManagerException;
import com.yemeksepeti.repository.entity.Customer;
import com.yemeksepeti.service.CustomerService;
import lombok.RequiredArgsConstructor;
import static com.yemeksepeti.constants.EndPoints.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(CUSTOMER)
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService service;

    @PostMapping(REGISTER)
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto){
        if(dto.getPassword().equals(dto.getRePassword()))
            return ResponseEntity.ok(service.register(dto));
        throw new YemeksepetiManagerException(ErrorType.PASSWORD_ERROR);
    }
    @PutMapping(ACTIVATE_STATUS)
    public ResponseEntity<Boolean> activateStatus(@RequestBody @Valid ActivateStatusRequestDto dto){
        return ResponseEntity.ok(service.activateStatus(dto));
    }
    @PostMapping(LOGIN)
    public ResponseEntity<Boolean> login(@RequestBody @Valid LoginRequestDto dto){
        return ResponseEntity.ok(service.login(dto));
    }
    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Customer>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping(ADD_FUND)
    public ResponseEntity<String> addFund(@RequestBody @Valid AddFundRequestDto dto){
        return ResponseEntity.ok(service.addFund(dto));
    }






}