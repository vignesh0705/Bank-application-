package bank;

import java.util.*;
import java.math.BigDecimal;

import account.AccountDAO;
import admin.AdminDAO;
import transaction.Transaction;
import transaction.TransactionDAO;

public class Bank {

    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            AccountDAO accountDAO = new AccountDAO();
            AdminDAO adminDAO = new AdminDAO();
            TransactionDAO transactionDAO = new TransactionDAO();

            int userAccountId = 0;
            int adminId = 0;

            boolean running = true;

            while (running) {

            System.out.println("\n===== BANK MENU =====");
            System.out.println("1.Login");
            System.out.println("2.Check Balance");
            System.out.println("3.Deposit");
            System.out.println("4.Withdraw");
            System.out.println("5.Transfer");
            System.out.println("6.History");
            System.out.println("7.Logout");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();

            if (userAccountId == 0 && choice > 1 && choice != 7) {
                System.out.println("⚠ Please login first!");
                continue;
            }

            switch (choice) {

                // 🔐 LOGIN
                case 1:
                    System.out.print("Login as (User/Admin): ");
                    String role = sc.next();

                    if (role.equalsIgnoreCase("User")) {

                        System.out.print("Enter Username: ");
                        String username = sc.next();

                        System.out.print("Enter Password: ");
                        String password = sc.next();

                        userAccountId = accountDAO.login(username, password);

                        if (userAccountId != 0)
                            System.out.println("✅ User logged in successfully!");
                        else
                            System.out.println("❌ Invalid credentials!");

                    } else if (role.equalsIgnoreCase("Admin")) {

                        System.out.print("Enter Admin Username: ");
                        String adminUser = sc.next();

                        System.out.print("Enter Password: ");
                        String adminPass = sc.next();

                        adminId = adminDAO.login(adminUser, adminPass);

                        if (adminId != 0) {
                            System.out.println("✅ Admin logged in!");

                            System.out.println("1.Create Account");
                            System.out.println("2.Back");
                            int adminChoice = sc.nextInt();

                            if (adminChoice == 1) {

                                System.out.print("Username: ");
                                String u = sc.next();

                                System.out.print("Account Type: ");
                                String type = sc.next();

                                System.out.print("Initial Balance: ");
                                BigDecimal bal = sc.nextBigDecimal();

                                System.out.print("Password: ");
                                String pass = sc.next();

                                System.out.print("Phone: ");
                                long phone = sc.nextLong();

                                System.out.print("Address: ");
                                String addr = sc.next();

                                System.out.print("Branch: ");
                                String branch = sc.next();

                                boolean created = adminDAO.createAccount(
                                        u, type, bal, pass, phone, addr, branch);

                                if (created)
                                    System.out.println("✅ Account created successfully!");
                                else
                                    System.out.println("❌ Failed to create account.");
                            }

                        } else {
                            System.out.println("❌ Invalid Admin login!");
                        }
                    }
                    break;

                // 💰 CHECK BALANCE
                case 2:
                    BigDecimal balance = accountDAO.getBalance(userAccountId);
                    System.out.println("💰 Current Balance: " + balance);
                    break;

                // ➕ DEPOSIT
                case 3:
                    System.out.print("Enter deposit amount: ");
                    BigDecimal depositAmount = sc.nextBigDecimal();

                    if (accountDAO.deposit(userAccountId, depositAmount)) {

                        BigDecimal newBalance = accountDAO.getBalance(userAccountId);

                        Transaction depositTransaction =
                                new Transaction(userAccountId, 0,
                                        depositAmount, newBalance, "Deposit");

                        transactionDAO.addTransaction(depositTransaction);

                        System.out.println("✅ Deposit successful!");
                    } else {
                        System.out.println("❌ Deposit failed!");
                    }
                    break;

                // ➖ WITHDRAW
                case 4:
                    System.out.print("Enter withdraw amount: ");
                    BigDecimal withdrawAmount = sc.nextBigDecimal();

                    if (accountDAO.withdraw(userAccountId, withdrawAmount)) {

                        BigDecimal newBalance = accountDAO.getBalance(userAccountId);

                        Transaction withdrawTransaction =
                                new Transaction(0, userAccountId,
                                        withdrawAmount, newBalance, "Withdraw");

                        transactionDAO.addTransaction(withdrawTransaction);

                        System.out.println("✅ Withdrawal successful!");
                    } else {
                        System.out.println("❌ Insufficient balance!");
                    }
                    break;

                // 🔁 TRANSFER
                case 5:
                    System.out.print("Enter receiver account ID: ");
                    int receiverId = sc.nextInt();

                    if (!accountDAO.accountExists(receiverId)) {
                        System.out.println("❌ Receiver account not found!");
                        break;
                    }

                    System.out.print("Enter transfer amount: ");
                    BigDecimal transferAmount = sc.nextBigDecimal();

                    if (accountDAO.safeTransfer(userAccountId, receiverId, transferAmount)) {

                        BigDecimal senderBalance =
                                accountDAO.getBalance(userAccountId);

                        Transaction transferTransaction =
                                new Transaction(userAccountId, receiverId,
                                        transferAmount, senderBalance, "Transfer");

                        transactionDAO.addTransaction(transferTransaction);

                        System.out.println("✅ Transfer successful!");
                    } else {
                        System.out.println("❌ Transfer failed (Insufficient balance)");
                    }
                    break;

                // 📜 HISTORY
                case 6:
                    List<Transaction> history =
                            transactionDAO.getHistory(userAccountId);

                    System.out.println("\n---- Transaction History ----");

                    for (Transaction t : history) {
                        System.out.println(
                                "ID: " + t.getTransactionId() +
                                " | From: " + t.getFromAccountId() +
                                " | To: " + t.getToAccountId() +
                                " | Amount: " + t.getAmount() +
                                " | Balance: " + t.getAccBalance() +
                                " | Type: " + t.getTransactionType() +
                                " | Date: " + t.getDate()
                        );
                    }
                    break;

                // 🚪 LOGOUT
                case 7:
                    userAccountId = 0;
                    adminId = 0;
                    System.out.println("👋 Logged out successfully!");
                    break;

                default:
                    System.out.println("❌ Invalid option!");
            }
        }
        }
    }
}