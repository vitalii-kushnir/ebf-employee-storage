package com.example.controller;

import com.example.ApiError;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExceptionsHandlerController {

//    @ExceptionHandler({ DataIntegrityViolationException.class })
//    @ResponseBody
//    public ResponseEntity<?> handleDataIntegrityViolationException() {
//        ApiError exception = new ApiError(HttpStatus.BAD_REQUEST, " Unique index or primary key violation exception");
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception);
//    }


}
