package fpt.aptech.projectapi.exception;

import fpt.aptech.projectapi.DTOs.ApiRespone;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExecptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiRespone> runtimeExceptionHandler(RuntimeException ex) {
        ApiRespone apiRespone = new ApiRespone();
        apiRespone.setMessage(ex.getMessage());
        apiRespone.setCode(401);
        return ResponseEntity.badRequest().body(apiRespone);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ApiRespone<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        ApiRespone apiRespone = new ApiRespone();
        apiRespone.setMessage(ex.getFieldError().getDefaultMessage());
        apiRespone.setCode(401);
        return apiRespone;
    }
}
