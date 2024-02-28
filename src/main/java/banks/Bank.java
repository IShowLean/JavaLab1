package banks;

import account.AccountOption;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;

/**
 * Bank class represents bank object.
 */
@Data
public class Bank {
    @NonNull String name;
    private ArrayList<AccountOption> accounts = new ArrayList<AccountOption>();
    @NonNull Double maxDepositForUnverifiedUsers;
    @NonNull private Double maxWithdrawForUnverifiedUsers;

    /**
     * Method allows to register new client with his account in a bank system.
     * @param option - Type of account that belongs to user (Debit, Deposit, Credit).
     */
    public void registerNewClient(AccountOption option) {
        accounts.add(option);
    }
}
