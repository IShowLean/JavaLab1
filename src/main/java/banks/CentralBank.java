package banks;

import account.AccountOption;
import account.UnverifiedStatusException;
import clients.Client;
import clients.Status;
import lombok.Data;

import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Class represents a Central Bank object that controls every other small banks and transactions.
 * Also, can notify users about things.
 */
public class CentralBank {
    private final ArrayList<Bank> bankList = new ArrayList<>();
    private ArrayList<Client> clientList = new ArrayList<>();
    private final Logger transactionLogger = Logger.getLogger("Transaction Logger");
    private Integer transactionID = 1;
    public void registerNewBank(String name, Double maxDepositForUnverifiedUsers, Double maxWithdrawForUnverifiedUsers) {
        Bank bank = new Bank(name, maxDepositForUnverifiedUsers, maxWithdrawForUnverifiedUsers);
        bankList.add(bank);
    }

    /**
     * Method to notify users about something important from Central Bank.
     * @param message - string that contains message to users.
     */
    public void notifyUsers(String message) {
        for (Client client : clientList) {
            client.notify(message);
        }
    }

    /**
     * Method allows users to subscribe to Central Bank notification.
     */
    public void subscribe(Client client) {
        clientList.add(client);
    }

    /**
     * Method to transfer money from one user (account) to another.
     * @param bankTransfer - bank that sends money.
     * @param IDTransfer - Account ID that sends money.
     * @param bankReceiver - bank that receives money.
     * @param IDReceiver - Account ID that receives money.
     * @param sum - amount of money that will be sent.
     */
    public void transferMoney(Bank bankTransfer, Integer IDTransfer, Bank bankReceiver, Integer IDReceiver, Double sum) throws UnverifiedStatusException {
        var transferAccounts = bankTransfer.getAccounts();
        var receiverAccounts = bankReceiver.getAccounts();
        AccountOption accountTransfer = null;
        AccountOption accountReceiver = null;
        for (AccountOption transferAccount : transferAccounts) {
            if (transferAccount.getID() == IDTransfer) {
                accountTransfer = transferAccount;
                break;
            }
        }
        for (AccountOption receiverAccount : receiverAccounts) {
            if (receiverAccount.getID() == IDReceiver) {
                accountReceiver = receiverAccount;
                break;
            }
        }
        if (accountTransfer != null && accountReceiver != null && accountTransfer.getCash() - sum >= 0) {
            if (accountTransfer.getClient().getStatus() == Status.UNVERIFIED && sum >= accountTransfer.getmaxWithdrawForUnverifiedUsers()) {
                throw new UnverifiedStatusException("Client status is unverified to transfer this sum");
            }
            accountTransfer.setCash(accountTransfer.getCash() - sum);
            accountReceiver.setCash(accountReceiver.getCash() + sum);
            transactionLogger.info("ID " + transactionID + ": Money: " + sum + " transaction from " + accountTransfer.getID() + " " +
                    accountTransfer.getClient().getName() + " to " + accountReceiver.getID() + " " +
                    accountReceiver.getClient().getName());
            transactionID++;
        }
    }

    /**
     * Method to rollback previous transactions. Allows to refund money.
     * @param bankTransfer - bank that sends money.
     * @param IDTransfer - Account ID that sends money.
     * @param bankReceiver - bank that receives money.
     * @param IDReceiver - Account ID that receives money.
     * @param sum - amount of money that will be sent.
     */
    public void transactionRollback(Bank bankTransfer, Integer IDTransfer, Bank bankReceiver, Integer IDReceiver, Double sum) {
        var transferAccounts = bankTransfer.getAccounts();
        var receiverAccounts = bankReceiver.getAccounts();
        AccountOption accountTransfer = null;
        AccountOption accountReceiver = null;
        for (AccountOption transferAccount : transferAccounts) {
            if (transferAccount.getID() == IDTransfer) {
                accountTransfer = transferAccount;
                break;
            }
        }
        for (AccountOption receiverAccount : receiverAccounts) {
            if (receiverAccount.getID() == IDReceiver) {
                accountReceiver = receiverAccount;
                break;
            }
        }
        if (accountTransfer != null && accountReceiver != null) {
            accountTransfer.setCash(accountTransfer.getCash() - sum);
            accountReceiver.setCash(accountReceiver.getCash() + sum);
            transactionLogger.info("ID " + transactionID + ": Transaction Rollback: " + sum + " transaction from " + accountTransfer.getID() + " " +
                    accountTransfer.getClient().getName() + " to " + accountReceiver.getID() + " " +
                    accountReceiver.getClient().getName());
            transactionID++;
        }
    }
}
