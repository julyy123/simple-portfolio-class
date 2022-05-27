package portfolio;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@Data
@AllArgsConstructor
public class Stock {
    private String symbol;
    private Map<LocalDate, Double> prices;

    /**
     * Gets the price of the stock for the given date
     * @param date  {@link LocalDate}
     * @return      {@ink Double}
     */
    public double getPrice(final LocalDate date) {
        if (!prices.containsKey(date)){
            System.out.println("There is no price for stock: " + symbol + " for date: " + date.format(DateTimeFormatter.BASIC_ISO_DATE));
            return 0;
        }

        return prices.get(date);
    }
}
