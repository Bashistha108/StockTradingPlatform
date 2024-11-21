package com.learnbydoing.tradingapp.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLOutput;

@Component
public class FinnhubWebSocketClient extends WebSocketClient {
    public FinnhubWebSocketClient(@Value("${finnhub.api.key}") String apiKey) throws URISyntaxException{
        super(new URI("wss://ws.finnhub.io?token=" + apiKey));
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Connected to Finhub Websocket");
        //Subscribe to a stock symbol for updates
        send("{\"type\":\"subscribe\",\"symbol\":\"BABA\"}");
    }

    @Override
    public void onMessage(String s) {
        System.out.println("Received Message: " + s);

        if (s.contains("\"type\":\"ping\"")) {
            System.out.println("Ping received. Sending pong...");
            // Send a pong response (implement the send logic if required)
            sendMessage("{\"type\":\"pong\"}");
        } else {
            // Handle other message types or log them
            System.out.println("Other message type received: " + s);
        }
    }

    // Method to send a response (implementation depends on your WebSocket client)
    private void sendMessage(String message) {
        System.out.println("Sending message: " + message);
        // Example:
        // session.getBasicRemote().sendText(message); // Uncomment for real implementation
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("Websocket closed: "+s);
    }

    @Override
    public void onError(Exception e) {
        System.err.println("Error: "+e.getMessage());
    }
}
