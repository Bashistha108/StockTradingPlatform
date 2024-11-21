
// Get the stock symbol from the HTML page (the value is passed from the server-side model)
const stockSymbol = document.getElementById("stock-symbol").textContent.trim();
// Get the live price element where we will display the updated stock price
const livePriceElement = document.getElementById("live-price");

if (stockSymbol) {
    const apiKey = "csv5rf1r01qq28mn1o5gcsv5rf1r01qq28mn1o60";
     // Create a WebSocket connection to Finnhub's WebSocket endpoint
    const socket = new WebSocket(`wss://ws.finnhub.io?token=${apiKey}`);


    // Event: When the WebSocket connection is successfully opened
    socket.addEventListener("open", () => {
        console.log("Connected to Finnhub WebSocket JS ---------------------------------");
        // Send a subscription message to the WebSocket for the given stock symbol
        // Finnhub expects this format to subscribe to a specific stock ticker
        socket.send(JSON.stringify({ type: "subscribe", symbol: stockSymbol }));
    });

// Event: When the WebSocket receives a new message
    socket.addEventListener("message", (event) => {
        // Parse the incoming data from the WebSocket
        const data = JSON.parse(event.data);

        // Check if the message contains trade data (type "trade")
        if (data.type === "trade") {
            // Finnhub provides trade data as an array; we pick the first one
            const tradeData = data.data[0];

            // Ensure the trade data matches the requested stock symbol
            if (tradeData && tradeData.s === stockSymbol) {
                // Update the live price on the HTML page with the trade price
                // `p` is the price field in Finnhub's response
                livePriceElement.textContent = tradeData.p.toFixed(2); // Display with two decimal places
            }
        }
    });

    // Event: When the WebSocket connection is closed
    socket.addEventListener("close", () => {
        console.log("WebSocket closed");
    });

    // Event: If the WebSocket encounters an error
    socket.addEventListener("error", (error) => {
        console.error("WebSocket error:", error);
    });

    // Close the WebSocket connection when the user leaves or reloads the page
    // This ensures clean resource management and prevents memory leaks
    window.addEventListener("beforeunload", () => {
        socket.close();
    });
} else {
    // If no stock symbol is provided, log an error to the console
    console.error("No stock symbol provided");
}
