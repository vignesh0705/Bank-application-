package transaction;
import account.Account;
import Database.DBconnection;
import java.sql.*;
import java.util.ArrayList;
public class Transaction extends Account {
        public int fromAccountId;
        public int toAccountId;
        public int transactionId;
        public long transactionAccount;
        public long date;
        public String transactionType;
        public float accBalance;
        public float amount;
        
		public float getAccBalance() {
			return accBalance;
		}
		public void setAccBalance(float accBalance) {
			this.accBalance = accBalance;
		}
		public Transaction(int fromAccountId, int toAccountId, int transactionId, long accountNumber, long date,
				String transactionType) {
			this.fromAccountId = fromAccountId;
			this.toAccountId = toAccountId;
			this.transactionId = transactionId;
			this.transactionAccount = accountNumber;
			this.date = date;
			this.transactionType = transactionType;
		}
		public Transaction(){};
		public int getFromAccountId() {
			return fromAccountId;
		}
		public void setFromAccountId(int fromAccountId) {
			this.fromAccountId = fromAccountId;
		}
		public int getToAccountId() {
			return toAccountId;
		}
		public void setToAccountId(int toAccountId) {
			this.toAccountId = toAccountId;
		}
		public int getTransactionId() {
			return transactionId;
		}
		public void setTransactionId(int transactionId) {
			this.transactionId = transactionId;
		}
		public long getTransactionAccount() {
			return transactionAccount;
		}
		public void setTransactionAccount(long transactionAccount) {
			this.transactionAccount = transactionAccount;
		}
		public long getDate() {
			return date;
		}
		public void setDate(long date) {
			this.date = date;
		}
		public String getTransactionType() {
			return transactionType;
		}
		public void setTransactionType(String transactionType) {
			this.transactionType = transactionType;
		}
		public void initTransaction(int fromAcc,int toAcc,float amount,float accBalance,long transTime,String transType) {
			String query="insert into transactions(fromAcc,toAcc,amount,accBalance,transTime,transType) values(?,?,?,?,?,?)";
			try {
				Connection con=new DBconnection().getConnection();
				PreparedStatement pst=con.prepareStatement(query);
				if(fromAcc!=0) {
					pst.setInt(1,fromAcc);
				} else {
					pst.setString(1, null);
				}
				if(toAcc!=0) {
					pst.setInt(2,toAcc);
				}
				else {
					pst.setString(2,null);
				}
				
//				pst.setInt(2, toAcc);
				pst.setFloat(3,amount);
				pst.setFloat(4,accBalance);
				pst.setLong(5, transTime);
				pst.setString(6, transType);
				pst.executeUpdate();
				System.out.println("Transaction added successfully");
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		public ArrayList<Transaction> transactionHistory(int id){
			ArrayList<Transaction> history=new ArrayList<>();
			String query="select * from transactions where fromAcc=? or toAcc=?";
			try {
				Connection con=new DBconnection().getConnection();
				PreparedStatement pst=con.prepareStatement(query);
				pst.setInt(1, id);
				pst.setInt(2, id);
				ResultSet res=pst.executeQuery();
				
				while(res.next()) {
					Transaction temp=new Transaction();
					temp.fromAccountId=res.getInt("fromAcc");
					temp.toAccountId=res.getInt("toAcc");
					temp.transactionId=res.getInt("id");
					temp.transactionType=res.getString("transType");
					temp.date=res.getLong("transTime");
					temp.accBalance=res.getFloat("accBalance");
					temp.amount=res.getFloat("amount");
					history.add(temp);
				}
				return history;
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
//			return history;
		}
        
}
