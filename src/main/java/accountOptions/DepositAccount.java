package accountOptions;

import banks.Bank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositAccount implements AccountOption {
    private Double cash;
    private Double percent;
    private Integer period;
    private Double commission = 0.0;
    private Boolean deposit = true;
    private Boolean withdraw = false;
    private Bank bank;
    private Double maxDepositForUnverifiedUsers = bank.getMaxDepositForUnverifiedUsers();
    private Double maxWithdrawForUnverifiedUsers = bank.getMaxWithdrawForUnverifiedUsers();

    public DepositAccount(Double cash, Integer period, Bank bank, Double percentOne, Double percentTwo, Double percentThree) {
        this.cash = cash;
        this.period = 30 * period;
        this.bank = bank;

        if (cash < 50000) this.percent = percentOne;
        else if (50000 <= cash && cash <= 100000) this.percent = percentTwo;
        else this.percent = percentThree;
    }

    public void checkPeriod() {
        if (period == 0) withdraw = true;
    }

    public void depositMoney(Double cash) throws InvalidDepositException {
        if (deposit) this.cash += cash;
        else throw new InvalidDepositException("Deposit is blocked");
    }

    public void withdrawMoney(Double cash) throws InvalidWithdrawException {
        if (deposit) this.cash += cash;
        else throw new InvalidWithdrawException("Withdraw is blocked");
    }
}
