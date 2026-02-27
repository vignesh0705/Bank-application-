package admin;

import Database.DBconnection;
import java.sql.*;
import java.math.BigDecimal;

public class AdminDAO {

    // Admin Login
    public int login(String userName, String password) {

        String query = "SELECT id FROM admins WHERE user_name=? AND password=?";

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

    // Create Account (Admin Full Rights)
    public boolean createAccount(String userName,
                                 String accType,
                                 BigDecimal balance,
                                 String password,
                                 long phone,
                                 String address,
                                 String branch) {

        String query = "INSERT INTO accounts(userName, accType, balance, password, phone, address, branch) VALUES(?,?,?,?,?,?,?)";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, userName);
            pst.setString(2, accType);
            pst.setBigDecimal(3, balance);
            pst.setString(4, password);
            pst.setLong(5, phone);
            pst.setString(6, address);
            pst.setString(7, branch);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete Account
    public boolean deleteAccount(int id) {

        String query = "DELETE FROM accounts WHERE id=?";

        try (Connection con = DBconnection.getConnection();
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, id);
            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}