package portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Data
@AllArgsConstructor
public class Portfolio {
    private Map<Stock, Double> stockShares;

    public double profit(final LocalDate startDate, final LocalDate endDate) {
        final double beginningValue = sumAllStockPricesByDate(startDate);
        final double endingValue = sumAllStockPricesByDate(endDate);

        return endingValue - beginningValue;
    }

    /**
     * Gets the annualized return for the given dates.
     * 
     * The annualized return is calculated using this formula:
     * Annualized return = (1 + return) ^ (365 / numbersOfDaysHeld) - 1
     *
     * More info in: https://www.investopedia.com/terms/a/annualized-total-return.asp
     *
     * @param startDate {@link LocalDate}
     * @param endDate   {@link LocalDate}
     * @return          {@link Double}
     */
    public double annualizedReturn(final LocalDate startDate, final LocalDate endDate) {
        final double returnValue = calculateReturnValue(startDate, endDate);
        final double numberOfDaysHeld = DAYS.between(startDate, endDate);

        return Math.pow((1 + (returnValue)), (365 / numberOfDaysHeld)) - 1;
    }

    /**
     * Sum all stock prices prices in the portfolio by given date
     * @param date  {@link LocalDate}
     * @return      {@link Double}
     */
    private double sumAllStockPricesByDate(final LocalDate date) {
        return stockShares.entrySet().stream()
                .mapToDouble(entry -> {
                    final double stockPrice = entry.getKey().getPrice(date);
                    final double stockShare = entry.getValue();

                    return stockPrice * stockShare;
                }).sum();
    }

    /**
     * Calculates the return value for all the stocks in the portfolio for the given start date and end date
     * @param startDate
     * @param endDate
     * @return
     */
    private double calculateReturnValue(final LocalDate startDate, final LocalDate endDate) {
        final double beginningValue = sumAllStockPricesByDate(startDate);
        final double endingValue = sumAllStockPricesByDate(endDate);

        return (endingValue - beginningValue) / beginningValue;
    }
}
