package com.learnbydoing.tradingapp;

import com.learnbydoing.tradingapp.websocket.FinnhubWebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TradingappApplication {

	//private final FinnhubWebSocketService finnhubWebSocketService;

//	@Autowired
//	public TradingappApplication(FinnhubWebSocketService finnhubWebSocketService) {
//		this.finnhubWebSocketService = finnhubWebSocketService;
//	}
	public static void main(String[] args) {
		SpringApplication.run(TradingappApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//		finnhubWebSocketService.startWebSocket("csv5rf1r01qq28mn1o5gcsv5rf1r01qq28mn1o60");
//	}
}
