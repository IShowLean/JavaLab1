package banks;

import lombok.Data;
import java.util.ArrayList;

@Data
public class CentralBank {
    private ArrayList<Bank> bankList = new ArrayList<Bank>();
    public void registerNewBank(String name, Double maxDepositForUnverifiedUsers, Double maxWithdrawForUnverifiedUsers) {
        Bank bank = new Bank(name, maxDepositForUnverifiedUsers, maxWithdrawForUnverifiedUsers);
        bankList.add(bank);
    }
}
