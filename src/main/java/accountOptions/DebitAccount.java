package accountOptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DebitAccount implements AccountOption {
    private Double cash;
    private Double percent;
    private Integer period;
    private Double commission = 0.0;

    public DebitAccount(Double cash, Double percent, Integer period) {
        this.cash = cash;
        this.percent = percent;
        this.period = 30 * period;
    }

    public void withdrawMoney(Double sum) throws InvalidWithdrawException {
        if (cash - sum >= 0) cash -= sum;
        else throw new InvalidWithdrawException("Operation must not lead to negative balance");
    }
}
