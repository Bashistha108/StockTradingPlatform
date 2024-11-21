package com.learnbydoing.tradingapp.controller;

import com.learnbydoing.tradingapp.websocket.FinnhubWebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebSocketController {

    private final FinnhubWebSocketService webSocketService;

    @Autowired
    public WebSocketController(FinnhubWebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

    @GetMapping("/startWebSocket")
    public String startWebSocket(@RequestParam String apiKey) {
        webSocketService.startWebSocket(apiKey);
        return "WebSocket started!";
    }

    @GetMapping("/stopWebSocket")
    public String stopWebSocket() {
        webSocketService.stopWebSocket();
        return "WebSocket stopped!";
    }


}
