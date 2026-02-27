package account;

import java.math.BigDecimal;

public class Account {

    private int id;
    private long accountNumber;
    private String userName;
    private String accountType;
    private BigDecimal balance;   
    private String password;
    private long phoneNumber;
    private String address;
    private String branchName;

    public Account() {}

    public Account(int id, long accountNumber, String userName,
                   String accountType, BigDecimal balance,
                   String password, long phoneNumber,
                   String address, String branchName) {

        this.id = id;
        this.accountNumber = accountNumber;
        this.userName = userName;
        this.accountType = accountType;
        this.balance = balance;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.branchName = branchName;
    }

    // Getters & Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public long getAccountNumber() { return accountNumber; }
    public void setAccountNumber(long accountNumber) { this.accountNumber = accountNumber; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public BigDecimal getBalance() { return balance; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public long getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(long phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getBranchName() { return branchName; }
    public void setBranchName(String branchName) { this.branchName = branchName; }
}