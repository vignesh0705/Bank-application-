package transaction;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {

    private int transactionId;
    private int fromAccountId;
    private int toAccountId;
    private BigDecimal amount;
    private BigDecimal accBalance;
    private String transactionType;
    private Timestamp date;

    public Transaction() {}

    public Transaction(int fromAccountId,
                       int toAccountId,
                       BigDecimal amount,
                       BigDecimal accBalance,
                       String transactionType) {

        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.accBalance = accBalance;
        this.transactionType = transactionType;
        this.date = new Timestamp(System.currentTimeMillis());
    }

    // Getters & Setters

    public int getTransactionId() { return transactionId; }
    public void setTransactionId(int transactionId) { this.transactionId = transactionId; }

    public int getFromAccountId() { return fromAccountId; }
    public void setFromAccountId(int fromAccountId) { this.fromAccountId = fromAccountId; }

    public int getToAccountId() { return toAccountId; }
    public void setToAccountId(int toAccountId) { this.toAccountId = toAccountId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public BigDecimal getAccBalance() { return accBalance; }
    public void setAccBalance(BigDecimal accBalance) { this.accBalance = accBalance; }

    public String getTransactionType() { return transactionType; }
    public void setTransactionType(String transactionType) { this.transactionType = transactionType; }

    public Timestamp getDate() { return date; }

    public void setDate(Timestamp timestamp) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setDate'");
    }
}