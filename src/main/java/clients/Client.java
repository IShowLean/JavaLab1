package clients;

import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents bank client.
 */
@Getter
@Setter
public class Client {
    private String name;
    private String surname;
    private String address;
    private String passportData;
    private Status status;

    /**
     * Constructs a new Client instance with 4 arguments (passport and address included).
     * @param name - client name.
     * @param surname - client surname.
     * @param passportData - client passport (series and number).
     * @param address - client address.
     */
    public Client(String name, String surname, String passportData, String address) {
        this.name = name;
        this.surname = surname;
        this.passportData = passportData;
        this.address = address;

        CheckUserStatus();
    }

    /**
     * Constructs a new Client instance with 2 arguments (passport and address are not included).
     * @param name - client name.
     * @param surname - client surname.
     */
    public Client(String name, String surname) {
        this.name = name;
        this.surname = surname;

        CheckUserStatus();
    }

    /**
     * Method checks if address and passport are valid and assigns a status.
     * Verified if user has valid address and passport, otherwise unverified.
     */
    public void CheckUserStatus() {
        if (address.isEmpty() || passportData.isEmpty()) status = Status.UNVERIFIED;
        else status = Status.VERIFIED;
    }

    /**
     * Method that adds passport and address to user fields.
     * @param passportData - string that represents passport series and number.
     * @param address - string that represents address.
     */
    public void addAddressAndPassport(String passportData, String address) {
        this.passportData = passportData;
        this.address = address;

        CheckUserStatus();
    }

    /**
     * Method to notify users about things from Central Bank.
     * @param message - message from Central Bank.
     */
    public void notify(String message) {
        System.out.println(message);
    }

}
