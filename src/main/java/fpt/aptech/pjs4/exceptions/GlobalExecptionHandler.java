package fpt.aptech.pjs4.exceptions;

import fpt.aptech.pjs4.DTOs.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExecptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<APIResponse> runtimeExceptionHandler(RuntimeException ex) {
        APIResponse apiRespone = new APIResponse();
        apiRespone.setMessage(ex.getMessage());
        apiRespone.setCode(401);
        return ResponseEntity.badRequest().body(apiRespone);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<APIResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        APIResponse apiRespone = new APIResponse();
        apiRespone.setMessage(ex.getFieldError().getDefaultMessage());
        apiRespone.setCode(401);
        return ResponseEntity.badRequest().body(apiRespone);

    }
}
