package fpt.aptech.pjs4.exceptions;

import fpt.aptech.pjs4.DTOs.APIResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

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
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<APIResponse> handlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        APIResponse apiResponse = new APIResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<APIResponse> handlingAccessDeniedException(AccessDeniedException exception){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        APIResponse apiResponse = new APIResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.status(errorCode.getCode()).body(apiResponse);
    }
}
