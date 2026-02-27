package transaction;

import Database.DBconnection;
import java.sql.*;
import java.util.ArrayList;

public class TransactionDAO {

    public void addTransaction(Transaction t) {

        String query = "INSERT INTO transactions(fromAcc,toAcc,amount,accBalance,transTime,transType) VALUES(?,?,?,?,?,?)";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            // 🔥 IMPORTANT FIX
            if (t.getFromAccountId() == 0) {
                pst.setNull(1, Types.INTEGER);
            } else {
                pst.setInt(1, t.getFromAccountId());
            }

            if (t.getToAccountId() == 0) {
                pst.setNull(2, Types.INTEGER);
            } else {
                pst.setInt(2, t.getToAccountId());
            }

            pst.setBigDecimal(3, t.getAmount());
            pst.setBigDecimal(4, t.getAccBalance());
            pst.setTimestamp(5, t.getDate());
            pst.setString(6, t.getTransactionType());

            pst.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Transaction> getHistory(int id) {

        ArrayList<Transaction> history = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE fromAcc=? OR toAcc=? ORDER BY transTime DESC";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id);
            pst.setInt(2, id);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                Transaction t = new Transaction();
                t.setTransactionId(rs.getInt("id"));

                // Handle NULL values safely
                int from = rs.getInt("fromAcc");
                if (rs.wasNull()) from = 0;
                t.setFromAccountId(from);

                int to = rs.getInt("toAcc");
                if (rs.wasNull()) to = 0;
                t.setToAccountId(to);

                t.setAmount(rs.getBigDecimal("amount"));
                t.setAccBalance(rs.getBigDecimal("accBalance"));
                t.setTransactionType(rs.getString("transType"));
                t.setDate(rs.getTimestamp("transTime"));

                history.add(t);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return history;
    }
}