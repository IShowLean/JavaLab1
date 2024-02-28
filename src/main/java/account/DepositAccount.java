package account;

import banks.Bank;
import clients.Client;
import clients.Status;
import lombok.Getter;
import lombok.Setter;

/**
 * Class to represent deposit type of account.
 */
@Getter
@Setter
public class DepositAccount implements AccountOption {
    private Double cash;
    private Double percent;
    private Integer period;
    private Boolean deposit = true;
    private Boolean withdraw = false;
    private Bank bank;
    private Double maxWithdrawForUnverifiedUsers = bank.getMaxWithdrawForUnverifiedUsers();
    private Integer ID;
    private Client client;

    /**
     * Constructor of a class.
     * @param cash - amount of money that account has.
     * @param period - duration of deposit account in days.
     * @param bank - reference to a bank that determines max and min amount of money to withdraw or deposit.
     * @param client - reference to a client that owns this account.
     * @param percentOne - percent if amount of money is < 50.000.
     * @param percentTwo - percent if amount of money is in range 50.000 and 100.000.
     * @param percentThree - percent if amount of money is > 100.000.
     */
    public DepositAccount(Double cash, Integer period, Bank bank, Client client, Double percentOne, Double percentTwo, Double percentThree) {
        this.cash = cash;
        this.period = 30 * period;
        this.bank = bank;
        this.client = client;

        if (cash < 50000) this.percent = percentOne;
        else if (50000 <= cash && cash <= 100000) this.percent = percentTwo;
        else this.percent = percentThree;
    }

    /**
     * Method to accumulate money due to debit type of account.
     */
    public void moneyAccumulation() {
        double accumulation = 0.0;
        Integer days = 0;
        while (period != days) {
            double percentForDay = percent/365;
            accumulation += percentForDay * cash;
            if (days%30 == 0) {
                cash += accumulation;
                accumulation = 0;
            }
            days++;
        }
    }

    /**
     * Method to check period when withdraw functionality is allowed.
     */
    public void checkPeriod() {
        if (period == 0) withdraw = true;
    }

    /**
     * Method to deposit money to account.
     * @param cash - amount of money to deposit.
     * @throws InvalidDepositException - exception to invalid deposit if deposit functionality is blocked.
     */
    public void depositMoney(Double cash) throws InvalidDepositException {
        if (deposit) this.cash += cash;
        else throw new InvalidDepositException("Deposit is blocked");
    }

    /**
     * Method to withdraw money from account.
     * @param sum - amount of money to withdraw.
     * @throws InvalidWithdrawException - exception to invalid withdraw if withdraw functionality is blocked.
     * @throws UnverifiedStatusException - exception to invalid withdraw if unverified user tries to withdraw money that exceed the limit.
     */
    public void withdrawMoney(Double sum) throws InvalidWithdrawException, UnverifiedStatusException {
        if (cash - sum >= 0 && withdraw && client.getStatus() == Status.VERIFIED) {
            cash -= sum;
        }
        else if (withdraw && client.getStatus() == Status.UNVERIFIED && sum > maxWithdrawForUnverifiedUsers) {
            throw new UnverifiedStatusException("Client status is unverified to withdraw this sum");
        }
        else if (cash - sum >= 0 && withdraw && client.getStatus() == Status.UNVERIFIED) {
            cash -= sum;
        }
        else throw new InvalidWithdrawException("Withdraw is blocked");
    }

    /**
     * Getter to a maxWithdrawForUnverifiedUsers field.
     * @return - value of maxWithdrawForUnverifiedUsers field.
     */
    @Override
    public Double getmaxWithdrawForUnverifiedUsers() {
        return maxWithdrawForUnverifiedUsers;
    }
}
