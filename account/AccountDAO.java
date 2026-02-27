package account;

import Database.DBconnection;
import java.sql.*;
import java.math.BigDecimal;

public class AccountDAO {

    // Login
    public int login(String userName, String password) {

        String query = "SELECT id FROM accounts WHERE userName=? AND password=?";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, userName);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    // Get Balance
    public BigDecimal getBalance(int id) {

        String query = "SELECT balance FROM accounts WHERE id=?";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("balance");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return BigDecimal.ZERO;
    }

    // Deposit
    public boolean deposit(int id, BigDecimal amount) {

        String query = "UPDATE accounts SET balance = balance + ? WHERE id=?";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setBigDecimal(1, amount);
            pst.setInt(2, id);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Withdraw (Safe)
    public boolean withdraw(int id, BigDecimal amount) {

        String checkQuery = "SELECT balance FROM accounts WHERE id=?";
        String updateQuery = "UPDATE accounts SET balance = balance - ? WHERE id=?";

        Connection con = null;

        try {
            con = DBconnection.getConnection();
            con.setAutoCommit(false);

            // Step 1: Check balance
            try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, id);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    BigDecimal currentBalance = rs.getBigDecimal("balance");

                    if (currentBalance.compareTo(amount) < 0) {
                        con.rollback();
                        return false; // insufficient balance
                    }
                } else {
                    con.rollback();
                    return false; // account not found
                }
            }

            // Step 2: Deduct amount
            try (PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
                updateStmt.setBigDecimal(1, amount);
                updateStmt.setInt(2, id);

                if (updateStmt.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }
            }

            con.commit();
            return true;

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // SAFE Transfer (ACID Compliant)
    public boolean safeTransfer(int senderId, int receiverId, BigDecimal amount) {

        String checkQuery = "SELECT balance FROM accounts WHERE id=?";
        String deductQuery = "UPDATE accounts SET balance = balance - ? WHERE id=?";
        String addQuery = "UPDATE accounts SET balance = balance + ? WHERE id=?";

        Connection con = null;

        try {
            con = DBconnection.getConnection();
            con.setAutoCommit(false);  // 🔥 Start transaction

            // Step 1: Check sender balance
            try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
                checkStmt.setInt(1, senderId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    BigDecimal currentBalance = rs.getBigDecimal("balance");

                    if (currentBalance.compareTo(amount) < 0) {
                        con.rollback();
                        return false; // insufficient balance
                    }
                } else {
                    con.rollback();
                    return false; // sender not found
                }
            }

            // Step 2: Deduct from sender
            try (PreparedStatement deductStmt = con.prepareStatement(deductQuery)) {
                deductStmt.setBigDecimal(1, amount);
                deductStmt.setInt(2, senderId);

                if (deductStmt.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }
            }

            // Step 3: Add to receiver
            try (PreparedStatement addStmt = con.prepareStatement(addQuery)) {
                addStmt.setBigDecimal(1, amount);
                addStmt.setInt(2, receiverId);

                if (addStmt.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }
            }

            con.commit();  
            return true;

        } catch (Exception e) {
            try {
                if (con != null) con.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    // Check Account Exists
    public boolean accountExists(int id) {

        String query = "SELECT id FROM accounts WHERE id=?";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}