package clients;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Client {
    private String name;
    private String surname;
    private String address;
    private String passportData;
    private Status status;

    public Client(String name, String surname, String passportData, String address) {
        this.name = name;
        this.surname = surname;
        this.passportData = passportData;
        this.address = address;

        CheckUserStatus();
    }

    public void CheckUserStatus() {
        if (address.isEmpty() || passportData.isEmpty()) status = Status.UNVERIFIED;
        else status = Status.VERIFIED;
    }

    public Client(String name, String surname) {
        this.name = name;
        this.surname = surname;

        CheckUserStatus();
    }

    public void addAddressAndPassport(String passportData, String address) {
        this.passportData = passportData;
        this.address = address;

        CheckUserStatus();
    }
}
