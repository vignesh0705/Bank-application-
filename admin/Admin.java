package admin;
import Database.AdminDBcalls;
import Database.DBconnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
public class Admin {
        public int id;
        public String userName;
        public String password;
		public Admin(int id, String userName, String password) {
			super();
			this.id = id;
			this.userName = userName;
			this.password = password;
		}
		public Admin(){};
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public int login(String userName,String passWord) {
			AdminDBcalls admin=new AdminDBcalls();
			return admin.checkLogin(userName,passWord);
		}
		public void createAccount(String userName,String accType,float balance,String password,long phone,String address,String branch) {
			Connection con=new DBconnection().getConnection();
			String query="insert into accounts(userName,accType,balance,password,phone,address,branch) values(?,?,?,?,?,?,?)";
			try {
				PreparedStatement pst=con.prepareStatement(query);
				pst.setString(1,userName);
				pst.setString(2,accType);
				pst.setFloat(3,balance);
				pst.setString(4,password);
				pst.setLong(5,phone);
				pst.setString(6,address);
				pst.setString(7,branch);
				pst.executeUpdate();	
				System.out.println("User added successfully");
				
			}
			catch(Exception e) {
				e.printStackTrace();
//				return 0;
			}
		}
}
