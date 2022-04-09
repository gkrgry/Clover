package com.daelim.clover.user.service;


import com.daelim.clover.user.domain.User;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;


public interface UserService {

    public void userSingUp(User user) throws  Exception;


    //회원가입 시 유효성 체크
    public default Map<String,String> validateHandling(Errors errors){
        Map<String, String > validatorResult = new HashMap<>();
        for(FieldError error : errors.getFieldErrors()){
            String validKeyName=String.format("valid_%s",error.getField());
            validatorResult.put(validKeyName, error.getDefaultMessage());
        }
        return validatorResult;
    }
}
