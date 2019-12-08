package com.ryit.commons.base.controller;

import com.ryit.commons.base.vo.ResponseData;
import com.ryit.commons.enums.SystemErrorEnum;
import com.ryit.commons.exception.CustomException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 处理Controller层全局异常处理
 *
 * @author samphin
 * @since 2019-9-28 15:30:14
 */
@ControllerAdvice
public class GlobalExceptionController {

    private static Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionController.class);

    /**
     * 运行异常错误码
     */
    private final int runTimeError = 400;

    /**
     * 参数绑定出错
     */
    private final int bindParamError = 300;

    /**
     * 数据校验错误码
     */
    private final int validateError = 301;

    /**
     * SQL错误码
     */
    private final int sqlError = 280;

    /**
     * 捕获自定义异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseData> handleCustomException(CustomException ex) {
        String errmsg = null;
        if (ex.getSystemErrorEnum().equals(SystemErrorEnum.ERROR_PARAM)) {
            errmsg = ex.getLocalizedMessage();
        } else {
            errmsg = ex.getSystemErrorEnum().getErrorMsg();
            if (StringUtils.isEmpty(errmsg)) {
                errmsg = ex.getMessage();
            }
        }
        GlobalExceptionController.LOGGER.error("哎呦！异常::" + ex.getLocalizedMessage(), ex);
        return new ResponseEntity(ResponseData.failure(ex.getSystemErrorEnum().getErrorCode(), errmsg), HttpStatus.OK);
    }

    /**
     * 捕获参数绑定异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseData> handleValidateException(BindException ex, BindingResult results) {
        ex.printStackTrace();
        StringBuffer sb = new StringBuffer();
        if (results.hasErrors()) {
            List<FieldError> errors = results.getFieldErrors();
            errors.stream().forEach(x -> sb.append(x.getField() + "=[" + x.getDefaultMessage()).append("];"));
        }
        return new ResponseEntity(ResponseData.failure(bindParamError, sb.toString()), HttpStatus.OK);
    }

    /**
     * 参数校验拦截器
     * 针对 @RequestBody参数校验异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getAllErrors()
                .stream()
                .map(objectError -> objectError.getDefaultMessage())
                .collect(Collectors.joining(","));
        return new ResponseEntity(ResponseData.failure(validateError, errorMsg), HttpStatus.OK);
    }

    /**
     * 捕获运行时异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseData> handleRuntimeException(RuntimeException ex) {
        GlobalExceptionController.LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity(ResponseData.failure(runTimeError, ex.getMessage()), HttpStatus.OK);
    }

    /**
     * 捕获SQL执行异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ResponseData> handleSQLException(SQLException ex) {
        GlobalExceptionController.LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity(ResponseData.failure(sqlError, ex.getMessage()), HttpStatus.OK);
    }

    /**
     * 捕获全局异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllException(Exception ex) {
        GlobalExceptionController.LOGGER.error(ex.getMessage(), ex);
        return new ResponseEntity(ex.getLocalizedMessage(), HttpStatus.OK);
    }
}
