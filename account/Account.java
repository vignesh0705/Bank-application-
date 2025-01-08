package account;
import Database.DBconnection;
import java.sql.*;
public class Account {
	 public long accountNumber;
	public String userName;
    public String accountType;
    public double balance;
    public String password;
    public long phoneNumber;
    public String address;
    public String branchName;
    
     public Account(long accountNumber,String userName, String accountType, double balance, String password,
			long phoneNumber, String address, String branchName) {
		this.userName = userName;
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = balance;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.address = address;
		this.branchName = branchName;
	}
     
     public Account() {}
     public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public long getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public int login(String userName,String passWord) {
		String query="select * from accounts where userName=? and password=?";
		try {
		Connection con=new DBconnection().getConnection();
		PreparedStatement pst=con.prepareStatement(query);
		pst.setString(1, userName);
		pst.setString(2,passWord);
		ResultSet res=pst.executeQuery();
		if(res.next()) {
			return res.getInt("id");
		}
		else {
			return 0;
		}
		
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}	
	}
	public float getBalanceAmount(int id) {
		String query="select balance from accounts where id=?";
		try {
			Connection con=new DBconnection().getConnection();
			PreparedStatement pst=con.prepareStatement(query);
			pst.setInt(1,id);
			ResultSet res=pst.executeQuery();
			if(res.next()) {
				return res.getFloat("balance");
			}
			else {
				return -1;
			}
		}catch(Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
	public void setBalanceAmount(int id,float amount) {
		String query="update accounts set balance=? where id=?";
		try {
			Connection con=new DBconnection().getConnection();
			PreparedStatement pst=con.prepareStatement(query);
			pst.setFloat(1,amount);
			pst.setInt(2, id);
			int res=pst.executeUpdate();
			if(res!=0) {
				System.out.println("Balance updated successfully");
			}else {
				System.out.println("Balance not updated in database ");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public int checkAccountExist(int id) {
		String query="select * from accounts where id=?";
		try {
			Connection con=new DBconnection().getConnection();
			PreparedStatement pst=con.prepareStatement(query);
			pst.setInt(1, id);
			ResultSet res=pst.executeQuery();
			if(res.next()) {
				return res.getInt("id");
			}
			else {
				return 0;
			}		
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}
