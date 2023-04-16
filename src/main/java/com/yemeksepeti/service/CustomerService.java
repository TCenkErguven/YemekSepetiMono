package com.yemeksepeti.service;

import com.yemeksepeti.dto.request.ActivateStatusRequestDto;
import com.yemeksepeti.dto.request.AddFundRequestDto;
import com.yemeksepeti.dto.request.LoginRequestDto;
import com.yemeksepeti.dto.request.RegisterRequestDto;
import com.yemeksepeti.dto.response.RegisterResponseDto;
import com.yemeksepeti.exception.ErrorType;
import com.yemeksepeti.exception.YemeksepetiManagerException;
import com.yemeksepeti.mapper.ICustomerMapper;
import com.yemeksepeti.repository.ICustomerRepository;
import com.yemeksepeti.repository.entity.Customer;

import com.yemeksepeti.repository.entity.EStatus;
import com.yemeksepeti.utility.CodeGenerator;
import com.yemeksepeti.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService extends ServiceManager<Customer,Long> {
    private final ICustomerRepository customerRepository;

    public CustomerService(ICustomerRepository customerRepository){
        super(customerRepository);
        this.customerRepository = customerRepository;
    }

    public RegisterResponseDto register(RegisterRequestDto dto){
        Customer customer = ICustomerMapper.INSTANCE.fromRegisterRequestDtoToCustomer(dto);
        customer.setActivationCode(CodeGenerator.generateCode());
        save(customer);
        RegisterResponseDto responseDto = ICustomerMapper.INSTANCE.fromCustomerToRegisterResponseDto(customer);
        return responseDto;
    }

    public Boolean activateStatus(ActivateStatusRequestDto dto){
        Optional<Customer> optionalCustomer = findById(dto.getId());
        if(optionalCustomer.isPresent()){
            if(optionalCustomer.get().getActivationCode().equals(dto.getActivationCode())){
                optionalCustomer.get().setStatus(EStatus.ACTIVE);
                update(optionalCustomer.get());
                return true;
            }
            throw new YemeksepetiManagerException(ErrorType.ACTIVATE_CODE_ERROR);
        }
        throw new YemeksepetiManagerException(ErrorType.CUSTOMER_NOT_FOUND);
    }

    public Boolean login(LoginRequestDto dto){
        Optional<Customer> optionalCustomer = customerRepository.findByEmailAndPassword(dto.getEmail(), dto.getPassword());
        if(optionalCustomer.isPresent()){
            if(optionalCustomer.get().getStatus().equals(EStatus.ACTIVE)){
                return true;
            }
            throw new YemeksepetiManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
        }
        throw new YemeksepetiManagerException(ErrorType.LOGIN_ERROR);
    }

    public List<Customer> findAll(){
        List<Customer> customerList = customerRepository.findAll();
        if(customerList.isEmpty()){
            throw new YemeksepetiManagerException(ErrorType.CUSTOMER_NOT_FOUND);
        }
        return customerList;
    }

    public String addFund(AddFundRequestDto dto){
        Optional<Customer> optionalCustomer = customerRepository.findById(dto.getId());
        if(optionalCustomer.isEmpty())
            throw new YemeksepetiManagerException(ErrorType.CUSTOMER_NOT_FOUND);
        else if(optionalCustomer.get().getStatus().equals(EStatus.ACTIVE)) {
            if(dto.getBalance()<=0)
                throw new YemeksepetiManagerException(ErrorType.INVALID_FUND);
            else if(optionalCustomer.get().getCardDetails().equals(""))
                throw new YemeksepetiManagerException(ErrorType.INVALID_CARD);
            Double newDeposit = (dto.getBalance() + optionalCustomer.get().getBalance());
            optionalCustomer.get().setBalance(newDeposit);
            save(optionalCustomer.get());
            return dto.getBalance() + " bakiye yüklenmiştir. Güncel bakiye: " + newDeposit;
        }
        throw new YemeksepetiManagerException(ErrorType.ACCOUNT_NOT_ACTIVE);
    }

    public String  findCustomerNameById(Long customerId){
        String customerName = customerRepository.findCustomerNameById(customerId);
        if (customerName == null)
            throw new YemeksepetiManagerException(ErrorType.CUSTOMER_NOT_FOUND);
        return customerName;
    }

    public Optional<Customer> findById(Long customerId){
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if(optionalCustomer.isPresent())
            return optionalCustomer;
        throw new YemeksepetiManagerException(ErrorType.CUSTOMER_NOT_FOUND);
    }

}
