package account;

import banks.Bank;
import clients.Client;
import clients.Status;
import lombok.Data;
import lombok.NonNull;

/**
 * Class to represent credit type of account.
 */
@Data
public class CreditAccount implements AccountOption {
    @NonNull private Double cash;
    private Double percent = 0.0;
    @NonNull private Integer period;
    private Double negativeBalanceCommission;
    private Boolean deposit = true;
    private Boolean withdraw = true;
    @NonNull private Integer ID;
    @NonNull private Client client;
    @NonNull private Bank bank;
    private Double maxWithdrawForUnverifiedUsers = bank.getMaxWithdrawForUnverifiedUsers();

    /**
     * Method to deposit money to account.
     * @param sum - amount of money to deposit.
     * @throws InvalidDepositException - exception to invalid deposit if deposit functionality is blocked.
     */
    public void depositMoney(Double sum) throws InvalidDepositException {
        if (deposit) cash += sum;
        else throw new InvalidDepositException("Deposit is blocked");
    }

    /**
     * Method to withdraw money from account.
     * @param sum - amount of money to withdraw.
     * @throws InvalidWithdrawException - exception to invalid withdraw if withdraw functionality is blocked.
     * @throws UnverifiedStatusException - exception to invalid withdraw if unverified user tries to withdraw money that exceed the limit.
     */
    public void withdrawMoney(Double sum) throws InvalidDepositException, UnverifiedStatusException {
        if (cash - sum >= 0 && withdraw && client.getStatus() == Status.VERIFIED) {
            cash -= sum;
        }
        else if (withdraw && client.getStatus() == Status.VERIFIED) cash -= (negativeBalanceCommission + sum);
        else if (withdraw && client.getStatus() == Status.UNVERIFIED && sum > maxWithdrawForUnverifiedUsers) {
            throw new UnverifiedStatusException("Client status is unverified to withdraw this sum");
        }
        else if (cash - sum >= 0 && withdraw && client.getStatus() == Status.UNVERIFIED) {
            cash -= sum;
        }
        else if (withdraw && client.getStatus() == Status.UNVERIFIED) {
            cash -= (negativeBalanceCommission + sum);
        }
        else throw new InvalidDepositException("Withdraw is blocked");
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
