package bank;
import transaction.Transaction;
import java.util.*;
import java.util.ArrayList;

import account.Account;
import admin.Admin;
public class Bank {
   public static void main(String[] args) {
	   Scanner sc=new Scanner(System.in);
//	   HashMap<String,Account>details=new HashMap<>();
//	   HashMap<String,Admin>Admin_details=new HashMap<>();
//	   HashMap<String,ArrayList<Transaction>>TransactionDetails=new HashMap<>();
//	   details.put("Ajay", new Account(101,"vignesh","Savings",12000.35,"123456",82207490,"chinnalapatti","chinnalapatti"));
//	   Admin_details.put("Rajendran", new Admin(102,"Rajendran","12345"));
	   Admin Ad_account=null;
	   Account account=null;
	   Transaction transaction=null;
	   int userAccountId=0;
	   float initAmount=-1;
	   int loginCheck=0;
       boolean flag=true;	   
	   while(flag) {
		   System.out.println("1.Login\n"+
	                            "2.Check Balance\n"+
				                 "3.Deposit\n"+
	                             "4.Withdraw\n"+
				                 "5.Transfer\n"+
	                             "6.History");
		   int eventType=sc.nextInt();
           if(account==null && eventType>1) {
        	   System.out.println("User not logged in");
        	   continue;
           }
           else {
        	   switch(eventType) {
        	   case 1:
        		   System.out.println("Login (Admin/User) :");
        		   String ch=sc.next();
        		   if(ch.equals("User")) {
        			   if(account==null) {
                		   System.out.println("Enter Username :");
                		   String userName=sc.next();
                		   System.out.println("Enter Password :");
                		   String password=sc.next();
                		   account=new Account();
                		   userAccountId=account.login(userName, password);
                		   
                		   if(userAccountId!=0) {
	                 		   System.out.println("User has been logged in with Account no "+userAccountId);
//	                 		   TransactionDetails.put(account.userName, new ArrayList<Transaction>());
                		   }
                		   else {
                			   account=null;
                		   }
        			   	}
                		else {
                			System.out.println("User is already logged in");
                		}
        		   }
        		   else {
        			   if(Ad_account==null) {
                		   System.out.println("Enter Admin Name :");
                		   String AdminName=sc.next();
                		   System.out.println("Enter Password :");
                		   String password=sc.next();
                		   Ad_account=new Admin();
                		   loginCheck=Ad_account.login(AdminName, password);
                		   
                		   if(loginCheck!=0) {
                 		   System.out.println("Admin has been logged in with Account no "+loginCheck);
                		   }
                 		   
                 		   boolean flog1=true;
                 		   while(flog1) {
                 			   System.out.println("1.Create Account\n"
                 			   		+ "2.other options\n"
                 			   		+ "3.back");
                 			    int option=sc.nextInt();
                			    switch(option) {
                 			    case 1:
                 			    	String userName;
                 			    	System.out.print("Enter the userName : ");
                 			    	userName=sc.next();
                 			    	System.out.print("Enter the accType : ");
                 			    	String accType=sc.next();
                 			    	System.out.print("Enter the balance : ");
                 			    	float balance=sc.nextFloat();
                 			    	System.out.print("Enter the password : ");
                 			    	String userPassword=sc.next();
                 			    	System.out.print("Enter the mobilenumber : ");
                 			    	long phone=sc.nextLong();
                 			    	System.out.print("Enter the address : ");
                 			    	String address =sc.next();
                 			    	System.out.print("Enter the Bank branch name : ");
                 			    	String branchName=sc.next();
              			   	        Ad_account.createAccount(userName, accType, balance, userPassword, phone,address, branchName);
//              			   	      
              			   	      break;
//                 			    case 2:
                 			    	
                 			    case 3:
                 			    	flog1=false;
                 			    }
;                 		   }
                		   }
        		   
                		   else {
                			   System.out.println("Admin is already logged in");
                		   }
        		   }
        		   
        		  break;
        	   case 2:
        		   System.out.println("Your Account Balance is : "+account.getBalanceAmount(userAccountId));
        		   break;
        	   case 3:
//Deposit
        		     System.out.println("Enter deposit amount : ");
        		     float deposit_amount=sc.nextFloat();
        		     initAmount=account.getBalanceAmount(userAccountId);
        		     if(deposit_amount>0) {
        		    	 transaction=new Transaction();
        		    	 float bal=initAmount+deposit_amount;
        		    	 account.setBalanceAmount(userAccountId,bal);
        		    	 transaction.initTransaction(userAccountId, 0, deposit_amount, bal, System.currentTimeMillis(), "Deposit");
        		     }
        		     else {
        		    	 System.out.println("Enter a valid amount to deposit");
        		     }
        		     break;
        	   case 4:
//withdraw
        		   System.out.println("Enter the Withdraw amount : ");
        		   float withdrawAmt=sc.nextFloat();
        		   initAmount=account.getBalanceAmount(userAccountId); 
        		   if(initAmount>=withdrawAmt) {
        			   transaction=new Transaction();
        			   float bal=initAmount-withdrawAmt;
        			   account.setBalanceAmount(userAccountId,bal);
      		    	   transaction.initTransaction(0,userAccountId,withdrawAmt,bal,System.currentTimeMillis()/1000,"withdraw");
        		   }
        		   else {
      		    	 System.out.println("Your balance is less than your Withdraw amount !!");
      		     }
      		     break;
        	   case 5:
        		   System.out.println("Enter the receiver Account Number : ");
        		   int receiverAccNo=sc.nextInt();
        		   if(account.checkAccountExist(receiverAccNo)==0) {
        			   System.out.println("No account with id "+receiverAccNo+" exist");
        		   }
        		   else {
        			   System.out.print("Enter the amount : ");
        			   float amount=sc.nextFloat();
        			   float receiverBalance=account.getBalanceAmount(receiverAccNo);
        			   float senderBalance=account.getBalanceAmount(userAccountId);
        			   if(amount>senderBalance) {
        				   System.out.println("Insufficient balance");
        			   }
        			   else {
        				   senderBalance-=amount;
        				   receiverBalance+=amount;
        				   account.setBalanceAmount(userAccountId,senderBalance);
        				   account.setBalanceAmount(receiverAccNo,receiverBalance);
        				   transaction=new Transaction();
        				   transaction.initTransaction(userAccountId, receiverAccNo, amount, senderBalance, System.currentTimeMillis()/1000, "transfer");
        			   }
        			   
        		   }
        		   break;
        	   case 6:
 //History			
        		   Transaction historyTransaction=new Transaction();
        		   ArrayList<Transaction> history=historyTransaction.transactionHistory(userAccountId);
//        		   System.out.println("          History  ");	
//        		   System.out.print("Transaction_id    Transaction_Date    From_Account    To_Account    Transaction_Amount    Transaction_Type\n");
        		   System.out.printf("%-15s %-15s %-15s %-15s %-12s %-10s %-15s%n", 
                           "Transaction ID", "From Account", "To Account", "Type", "Date", "Amount", "Current Balance");
         System.out.println("--------------------------------------------------------------------------------------------");
        		   for(int i=0;i<history.size();i++) {
        			   Transaction temp=new Transaction();
        			   temp=history.get(i);
        			   System.out.printf("%-15s %-15s %-15s %-15s %-12s %-10.2f %-15.2f%n", 
                               temp.transactionId,
                               temp.fromAccountId,
                               temp.toAccountId,
                               temp.transactionType,
                               temp.date,
                               temp.amount,
                               temp.accBalance
                               );
        		   		}
//        			   System.out.print(temp.accountNumber+"    "+temp.date+"    "+temp.fromAccountId+"    "+temp.toAccountId+"    "+temp.amount+"    "+temp.transactionType+"\n");
        		   
        		   break;
        	   case 7:
        		   System.out.println("User has been logged out");
        		   account=null;
        		   flag=false;
        		   break;
        	   }
        	   
           }
   
	   }
	   
	   
   }
//   public static Account checkAccountExist(String username,String password,HashMap<String,Account>accounts) {
//	   if(accounts.getOrDefault(username, null)!=null) {
//		   Account acc=accounts.get(username);
//		   if(acc.password.equals(password) && acc.userName.equals(username)) {
//			   return acc;
//		   }
//		   else {
//			    return new Account();
//		   }
//		   
//	   }
//	   else {
//		   return new Account();
//	   }
//   }
//   public static Admin checkAccountExist_admin(String username,String password,HashMap<String,Admin>Admin_details) {
//	   if(Admin_details.getOrDefault(username, null)!=null) {
//		   Admin acc1=Admin_details.get(username);
//		   if(acc1.password.equals(password) && acc1.userName.equals(username)) {
//			   return acc1;
//		   }
//		   else {
//			    return new Admin();
//		   }
//		   
//	   }
//	   else {
//		   return new Admin();
//	   }
//   }
}
