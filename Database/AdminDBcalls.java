package Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDBcalls {
	public int checkLogin(String userName,String passWord) {
		String query="select * from admins where user_name=? and password=?";
		try{
			Connection con=new DBconnection().getConnection();
			PreparedStatement pst=con.prepareStatement(query);
			pst.setString(1, userName);
			pst.setString(2, passWord);
			ResultSet res=pst.executeQuery();
			if(res.next()) {
				return res.getInt("admin_id");
			}else {
				return 0;
			}
		}catch(Exception e) {
			System.out.println("Error occured in getting the admin details in login"+e);
		}
		return 0;
	}
}
