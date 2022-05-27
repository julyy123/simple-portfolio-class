package portfolio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PortfolioTest {
    private Stock sp500Stock;
    private Stock appleStock;
    private Portfolio portfolio;

    private static final LocalDate FIRST_DATE = LocalDate.now().minusDays(2);
    private static final LocalDate SECOND_DATE = LocalDate.now().minusDays(1);
    private static final LocalDate THIRD_DATE = LocalDate.now();

    private static final double[] SP500_PRICES = {3888.07, 3905.18, 3930.08};
    private static final double SP500_SHARES = 43.07;

    private static final double[] APPLE_PRICES = {147.11, 146.45, 149.09};
    private static final double APPLE_SHARES = 127.65;

    @BeforeEach
    void setUp() {
        Map<LocalDate, Double> sp500Prices = Map.of(
                FIRST_DATE, SP500_PRICES[0],
                SECOND_DATE, SP500_PRICES[1],
                THIRD_DATE, SP500_PRICES[2]
        );

        Map<LocalDate, Double> applePrices = Map.of(
                FIRST_DATE, APPLE_PRICES[0],
                SECOND_DATE, APPLE_PRICES[1],
                THIRD_DATE, APPLE_PRICES[2]
        );

        sp500Stock = new Stock("^GSPC", sp500Prices);
        appleStock = new Stock("AAPL", applePrices);

        Map<Stock, Double> stockShares =Map.of(
                sp500Stock, SP500_SHARES,
                appleStock, APPLE_SHARES
        );

        portfolio = new Portfolio(stockShares);
    }

    @Test
    @DisplayName("Given two dates with valid stocks in portfolio, should return the calculated profit")
    void calculateProfit() {
        final double actualProfit = portfolio.profit(FIRST_DATE, THIRD_DATE);
        final double expectedProfit = SP500_SHARES * (sp500Stock.getPrice(THIRD_DATE) - sp500Stock.getPrice(FIRST_DATE))
                 + APPLE_SHARES * (appleStock.getPrice(THIRD_DATE) - appleStock.getPrice(FIRST_DATE));

        assertEquals(actualProfit, expectedProfit, 0.00001);
    }

    @Test
    @DisplayName("Given two dates with no stocks in portfolio, should return 0")
    void calculateProfitWithNoStocks() {
        portfolio = new Portfolio(Map.of());
        final double actualProfit = portfolio.profit(FIRST_DATE, SECOND_DATE);

        assertEquals(actualProfit, 0.0);
    }

    @Test
    @DisplayName("Given two dates with valid stocks in portfolio, should calculate the annualized return")
    void calculateAnnualizedReturn() {
        final double actualAnnualizedReturn = portfolio.annualizedReturn(FIRST_DATE, THIRD_DATE);
        final double beginningValue = (SP500_SHARES * sp500Stock.getPrice(FIRST_DATE)) + (APPLE_SHARES * appleStock.getPrice(FIRST_DATE));
        final double endingValue = (SP500_SHARES * sp500Stock.getPrice(THIRD_DATE)) + (APPLE_SHARES * appleStock.getPrice(THIRD_DATE));

        final double expectedAnnualizedReturn = Math.pow((1 + ((endingValue - beginningValue) / beginningValue)), (365 / 2.0)) - 1;

        assertEquals(actualAnnualizedReturn, expectedAnnualizedReturn, 0.00001);
    }

    @Test
    @DisplayName("Given two dates with no stocks in portfolio, annualized return should be 0")
    void calculateAnnualizedReturnWithNoStocks() {
        portfolio = new Portfolio(Map.of());
        final double actualAnnualizedReturn = portfolio.profit(FIRST_DATE, SECOND_DATE);

        assertEquals(actualAnnualizedReturn, 0.0);
    }
}
