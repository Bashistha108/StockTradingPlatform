package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.entity.Portfolio;
import com.learnbydoing.tradingapp.entity.Stock;
import com.learnbydoing.tradingapp.entity.User;
import com.learnbydoing.tradingapp.repository.StockRepository;
import com.learnbydoing.tradingapp.repository.UserRepository;
import com.learnbydoing.tradingapp.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StockRepository stockRepository;

    @GetMapping("/{userId}/{stockId}")
    public Portfolio getPortfolio(@PathVariable Integer userId, @PathVariable Integer stockId) {
        return portfolioService.findPortfolio(userId, stockId);
    }

    @PostMapping("/add")
    public void addPortfolio(@RequestParam int userId,
                             @RequestParam int stockId,
                             @RequestParam double quantity) {
        portfolioService.addPortfolio(userId, stockId, quantity);
    }

//    @GetMapping("/{userId}")
//    public String usersPortfolio(@PathVariable("userId") int userId, Model model){
//        User user = userRepository.findByUserId(userId);
//        List<Portfolio> portfolios = user.getPortfolios();
//        System.out.println(portfolios);
//        model.addAttribute("portfolios", portfolios);
//        return "user/portfolio";
//    }

    @GetMapping("/")
    public String usersPortfolio(Model model){
        // Get the logged-in user's username
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // or authentication.getPrincipal() if you're using a custom user principal

        // Find the user by username
        User user = userRepository.findByEmail(email);
        List<Portfolio> portfolios = user.getPortfolios();
        System.out.println(portfolios);
        model.addAttribute("portfolios", portfolios);

        return "user/portfolio";
    }


    @DeleteMapping
    public void deletePortfolio(@RequestBody Portfolio portfolio) {
        portfolioService.deletePortfolio(portfolio);
    }


























}
