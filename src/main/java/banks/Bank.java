package banks;

import accountOptions.AccountOption;
import clients.Client;
import lombok.Data;
import lombok.NonNull;

import java.util.HashMap;
@Data
public class Bank {
    @NonNull private String name;
    private HashMap<Client, AccountOption> clients = new HashMap<Client, AccountOption>();
    @NonNull private Double maxDepositForUnverifiedUsers;
    @NonNull private Double maxWithdrawForUnverifiedUsers;
    public void registerNewClient(Client client, AccountOption option) {
        clients.put(client, option);
    }
}
