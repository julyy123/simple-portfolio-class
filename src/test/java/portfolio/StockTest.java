package portfolio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StockTest {
    private Stock stock;
    private static final LocalDate DATE_WITH_NO_DATA = LocalDate.now().minusDays(3);
    private static final LocalDate FIRST_DATE = LocalDate.now().minusDays(2);
    private static final LocalDate SECOND_DATE = LocalDate.now().minusDays(1);
    private static final LocalDate THIRD_DATE = LocalDate.now();

    private final static double FIRST_DATE_PRICE =  3888.07;
    private final static double SECOND_DATE_PRICE =  3905.18;
    private final static double THIRD_DATE_PRICE =  3930.08;


    @BeforeEach
    void setUp() {
        Map<LocalDate, Double> sp500Prices = Map.of(
                FIRST_DATE, FIRST_DATE_PRICE,
                SECOND_DATE, SECOND_DATE_PRICE,
                THIRD_DATE, THIRD_DATE_PRICE
                );

        stock = new Stock("^GSPC", sp500Prices);
    }

    @Test
    @DisplayName("Given date with valid data in stock for that date, should return its price")
    void whenStockHasDataForGivenDateShouldReturnItsPrice() {
        final double actualPrice = stock.getPrice(SECOND_DATE);
        final double expectedPrice = SECOND_DATE_PRICE;

        assertEquals(actualPrice, expectedPrice);
    }

    @Test
    @DisplayName("Given date with no valid data in stock for that date, should return zero")
    void whenStockDoesntHasDataForGivenDateShouldReturnZero() {
        final double actualPrice = stock.getPrice(DATE_WITH_NO_DATA);
        final double expectedPrice = 0;

        assertEquals(actualPrice, expectedPrice);
    }
}
