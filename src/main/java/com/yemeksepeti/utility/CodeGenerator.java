package com.yemeksepeti.utility;

import lombok.Data;

import java.util.UUID;

@Data
public class CodeGenerator {

    public static String generateCode(){
        String code = UUID.randomUUID().toString(); //örnek UUID --> 321321-sdase-123213-e213-32112sdada
        String[] data = code.split("-");
        String newCode="";
        for(String str: data){
            newCode += str.charAt(0);
        }
        return newCode;
    }
}
