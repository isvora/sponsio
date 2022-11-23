package eridanus.sponsio.helper;

public enum ArbitrageType {
    ARBITRAGE_1, // Broker 1: team 1 win; Broker 2: draw + team 2 win
    ARBITRAGE_2, // Broker 1: draw; Broker 2: team 1 win + team 2 win
    ARBITRAGE_3, // Broker 1: team 2 win; Broker 2: draw + team 1 win
    ARBITRAGE_4, // Broker 2: team 1 win; Broker 1: draw + team 2 win
    ARBITRAGE_5, // Broker 2: draw; Broker 1: team 1 win + team 2 win
    ARBITRAGE_6; // Broker 2: team 2 win; Broker 1: draw + team 1 win
}
