package accountOptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreditAccount implements AccountOption {
    private Double cash;
    private Double percent = 0.0;
    private Integer period;
    private Double commission = 0.0;
    private Double negativeBalanceCommission;

    public CreditAccount(Double cash, Integer period, Double negativeBalanceCommission) {
        this.cash = cash;
        this.period = 30 * period;
        this.negativeBalanceCommission = negativeBalanceCommission;
    }
}
