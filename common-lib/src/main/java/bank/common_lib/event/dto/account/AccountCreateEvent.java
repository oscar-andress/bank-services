package bank.common_lib.event.dto.account;

import bank.common_lib.enumeration.AccountType;

public class AccountCreateEvent {
    private Long accountId;        
    private String accountNumber;    
    private AccountType accountType;        
    private String clientId;   

    public AccountCreateEvent() {
    }

    public AccountCreateEvent(Long accountId, String accountNumber, AccountType accountType, String clientId) {
        this.accountId = accountId;
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.clientId = clientId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}