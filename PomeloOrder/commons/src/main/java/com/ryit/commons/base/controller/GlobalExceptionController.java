package com.ryit.commons.base.controller;

import com.ryit.commons.base.AjaxResult;
import com.ryit.commons.web.exception.user.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * 处理Controller层全局异常处理
 *
 * @author samphin
 * @date 2019-9-3 14:45:10
 */
@ControllerAdvice
public class GlobalExceptionController {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionController.class);

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<AjaxResult> handleCustomException(CustomException ex) {
        GlobalExceptionController.logger.error("哎呦！异常::" + ex.getLocalizedMessage(), ex);
        return new ResponseEntity<AjaxResult>(AjaxResult.error(0, ex.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<AjaxResult> handleValidateException(BindException ex, BindingResult results) {
        ex.printStackTrace();
        StringBuffer sb = new StringBuffer();
        if (results.hasErrors()) {
            List<FieldError> errors = results.getFieldErrors();
            errors.stream().forEach(x -> sb.append(x.getField() + "=[" + x.getDefaultMessage()).append("];"));
        }
        return new ResponseEntity<AjaxResult>(AjaxResult.error(0, sb.toString()), HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<AjaxResult> handleRuntimeException(RuntimeException e) {
        GlobalExceptionController.logger.error(e.getMessage(), e);
        return new ResponseEntity<AjaxResult>(AjaxResult.error(0, e.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<String>(ex.getLocalizedMessage(), HttpStatus.OK);
    }
}
