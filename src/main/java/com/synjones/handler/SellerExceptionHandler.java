package com.synjones.handler;

import com.synjones.config.ProjectUrlConfig;
import com.synjones.exception.SellException;
import com.synjones.exception.SellerAuthorizeException;
import com.synjones.util.ResultVoUtil;
import com.synjones.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/7/30 10:30
 */
@ControllerAdvice
public class SellerExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @ExceptionHandler(SellerAuthorizeException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ModelAndView HandlerAuthorizeException(){

        return new ModelAndView("redirect: /wechat/qrAuthorize?returnUrl="
                                +projectUrlConfig.getSell()+"/sell/seller/login");

    }


    @ExceptionHandler(SellException.class)
    @ResponseBody
    public ResultVo handlerSellException(SellException e){
        return ResultVoUtil.error(e.getCode(),e.getMessage());
    }

}
