package com.synjones.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.DocFlavor;

/**
 * @Author: eric
 * @Description:
 * @Date: 2018/8/9 15:27
 */
@RestController
@RequestMapping("/kill")
public class SecKillController {


    @GetMapping("/query/{productId}")
    public String query(@PathVariable("{productId}")String productId){


        return null;
    }


}
