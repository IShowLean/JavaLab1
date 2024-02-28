package account;
import banks.Bank;
import clients.Client;

/**
 * Interface represents all common methods for all types of account.
 */
public interface AccountOption {
    Double cash = null;
    Double getCash();
    void setCash(Double cash);
    Integer getID();
    void setID(Integer ID);
    Client getClient();
    void setClient(Client client);
    Double getmaxWithdrawForUnverifiedUsers();
}
