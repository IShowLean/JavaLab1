package account;

import banks.Bank;
import clients.Client;
import clients.Status;
import lombok.Data;
import lombok.NonNull;

/**
 * Class to represent debit type of account.
 */
@Data
public class DebitAccount implements AccountOption {
    @NonNull private Double cash;
    @NonNull private Double percent;
    @NonNull private Integer period;
    private Boolean deposit = false;
    private Boolean withdraw = true;
    @NonNull private Integer ID;
    @NonNull private Client client;
    @NonNull private Bank bank;
    private Double maxWithdrawForUnverifiedUsers = bank.getMaxWithdrawForUnverifiedUsers();

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
