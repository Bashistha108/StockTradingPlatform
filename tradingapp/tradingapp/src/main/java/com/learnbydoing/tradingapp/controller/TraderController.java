package com.learnbydoing.tradingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TraderController {

    @GetMapping("/trader-home")
    public String traderHome(){
        return "trader/trader-home";
    }
}
