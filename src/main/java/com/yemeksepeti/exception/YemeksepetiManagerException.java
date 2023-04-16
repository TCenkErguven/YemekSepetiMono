package com.yemeksepeti.exception;

import lombok.Getter;

@Getter
public class YemeksepetiManagerException extends  RuntimeException{
    private final ErrorType errorType;
    public YemeksepetiManagerException(ErrorType errorType, String customMessage){
        super(customMessage);
        this.errorType = errorType;
    }
    public YemeksepetiManagerException(ErrorType errorType){
        this.errorType = errorType;
    }
}
