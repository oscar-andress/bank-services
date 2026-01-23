package bank.common_lib.event.dto.client;

import bank.common_lib.enumeration.AccountType;

public class ClientCreateEvent {
    private String clientId;
    private AccountType accountType;
    private String name;

    public ClientCreateEvent() {
    }

    public ClientCreateEvent(String clientId, AccountType accountType, String name) {
        this.clientId = clientId;
        this.accountType = accountType;
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
