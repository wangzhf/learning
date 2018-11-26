package com.wangzhf.common.handler;

import com.wangzhf.common.domain.ResponseResult;
import com.wangzhf.common.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseResult missingParameterExceptionHandler(HttpServletResponse resp, Exception ex) {
        ResponseResult ret = new ResponseResult();
        ret.setStatus(CommonConstant.Http.EX_REQUEST_PARAM_MISSING_CODE.getStatus());
        ret.setStatusText(ex.getMessage());
        logger.debug(ex.toString());
        logger.debug(ex.getClass().getName() + " yoyo");
        logger.error("MissingServletRequestParameterException: ", ex);
        return ret;
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(HttpServletResponse response, Exception ex) {
        ResponseResult ret = new ResponseResult();
        ret.setStatus(CommonConstant.Http.EX_OTHER_CODE.getStatus());
        ret.setStatusText(ex.getMessage());
        response.setStatus(CommonConstant.Http.EX_OTHER_CODE.getStatus());
        logger.debug(ex.toString());
        logger.debug(ex.getClass().getName());
        logger.error("Exception: ", ex);
        return ret;
    }
}
