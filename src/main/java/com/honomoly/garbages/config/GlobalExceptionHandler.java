package com.honomoly.garbages.config;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestNotUsableException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.honomoly.garbages.GlobalHttpException;

/**
 * 에러 반환은 단순히 ProblemDetail 객체를 기준으로 설정
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 서버 내애서 범용적으로 사용하는 예외처리 */
    @ExceptionHandler(GlobalHttpException.class)
    public ProblemDetail handleGlobalHttpException(GlobalHttpException e) {
        return e.getBody();
    }

    // 예외처리 참고 클래스 : org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpMediaTypeNotAcceptableException.class,
            MissingPathVariableException.class,
            MissingServletRequestParameterException.class,
            MissingServletRequestPartException.class,
            ServletRequestBindingException.class,
            MethodArgumentNotValidException.class,
            HandlerMethodValidationException.class,
            NoHandlerFoundException.class,
            NoResourceFoundException.class,
            AsyncRequestTimeoutException.class,
            ErrorResponseException.class,
            MaxUploadSizeExceededException.class,
            ConversionNotSupportedException.class,
            TypeMismatchException.class,
            HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class,
            MethodValidationException.class,
            AsyncRequestNotUsableException.class
        })
    public final ProblemDetail handleException(Exception e) {
        if (e instanceof HttpRequestMethodNotSupportedException subE)
            return subE.getBody();
        else if (e instanceof HttpMediaTypeNotSupportedException subE) 
            return subE.getBody();
        else if (e instanceof HttpMediaTypeNotAcceptableException subE)
            return subE.getBody();
        else if (e instanceof MissingPathVariableException subE)
            return subE.getBody();
        else if (e instanceof MissingServletRequestParameterException subE)
            return subE.getBody();
        else if (e instanceof MissingServletRequestPartException subE)
            return subE.getBody();
        else if (e instanceof ServletRequestBindingException subE)
            return subE.getBody();
        else if (e instanceof MethodArgumentNotValidException subE)
            return subE.getBody();
        else if (e instanceof HandlerMethodValidationException subE)
            return subE.getBody();
        else if (e instanceof NoHandlerFoundException subE)
            return subE.getBody();
        else if (e instanceof NoResourceFoundException subE)
            return subE.getBody();
        else if (e instanceof AsyncRequestTimeoutException subE)
            return subE.getBody();
        else if (e instanceof MaxUploadSizeExceededException subE)
            return subE.getBody();
        else if (e instanceof ErrorResponseException subE)
            return subE.getBody();

        // Lower level exceptions, and exceptions used symmetrically on client and server

        else if (e instanceof ConversionNotSupportedException subE) {
            subE.printStackTrace();
            return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, subE.getMessage());
        }
        else if (e instanceof TypeMismatchException subE)
            return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, subE.getMessage());
        else if (e instanceof HttpMessageNotReadableException subE)
            return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, subE.getMessage());
        else if (e instanceof HttpMessageNotWritableException subE) {
            subE.printStackTrace();
            return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, subE.getMessage());
        }
        else if (e instanceof MethodValidationException subE) {
            subE.printStackTrace();
            return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, subE.getMessage());
        }
        else if (e instanceof AsyncRequestNotUsableException subE) {
            subE.printStackTrace();
            return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, subE.getMessage());
        }
        else {
            e.printStackTrace();
            return ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /** 서버가 예상하지 못한 예외처리 */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpectedException(Exception e) {
        e.printStackTrace();
        return ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
