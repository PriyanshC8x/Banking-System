import java.sql.*;
import java.util.*;

public class AccountManager {
    private Connection con;
    private Scanner sc;
    public AccountManager(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void debit_money(long account_no) throws SQLException {
        sc.nextLine();
        System.out.println("Enter amount to debit money");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter Security pin");
        String pin = sc.nextLine();
        try {
            con.setAutoCommit(false);
            if (account_no != 0) {
                String check = "Select * from accounts where account_number = ? and Security_pin = ?";
                PreparedStatement ps = con.prepareStatement(check);
                ps.setLong(1, account_no);
                ps.setString(2, pin);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    double curr_balance = rs.getDouble("balance");
                    if (amount <= curr_balance) {
                        String q = "update accounts set balance = balance - ? where account_number = ?";
                        PreparedStatement ps1 = con.prepareStatement(q);
                        ps1.setDouble(1, amount);
                        ps1.setLong(2, account_no);
                        int affected = ps1.executeUpdate();
                        if (affected > 0) {
                            System.out.println("Rs." + amount + " Debit successful");
                            con.commit();
                            con.setAutoCommit(true);
                        } else {
                            System.out.println("transaction failed");
                            con.rollback();
                            con.setAutoCommit(true);
                        }
                    } else {
                        System.out.println("Insufficient balance");
                    }
                } else {
                    System.out.println("Invalid pin!");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        con.setAutoCommit(true);
    }

    public void credit_money(long account_no) throws SQLException {
        sc.nextLine();
        System.out.println("Enter amount to credit money");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter Security pin");
        String pin = sc.nextLine();
        try {
            con.setAutoCommit(false);
            if (account_no != 0) {
                String check = "Select * from accounts where account_number = ? and Security_pin = ?";
                PreparedStatement ps = con.prepareStatement(check);
                ps.setLong(1, account_no);
                ps.setString(2, pin);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                        String q = "update accounts set balance = balance + ? where account_number = ?";
                        PreparedStatement ps1 = con.prepareStatement(q);
                        ps1.setDouble(1, amount);
                        ps1.setLong(2, account_no);
                        int affected = ps1.executeUpdate();
                        if (affected > 0) {
                            System.out.println("Rs." + amount + " Credit successful");
                            con.commit();
                            con.setAutoCommit(true);
                            System.out.println();
                            return;
                        } else {
                            System.out.println("transaction failed");
                            con.rollback();
                            con.setAutoCommit(true);
                        }
                } else {
                    System.out.println("Invalid pin!");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        con.setAutoCommit(true);
    }

    public  void transfer_money(long sender_account_no) throws SQLException {
        sc.nextLine();
        System.out.println("Enter account no. of receivers");
        long reciever_acc = sc.nextLong();
        System.out.println("Enter the transfer money");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter Security pin");
        String pin = sc.nextLine();
        try{
            con.setAutoCommit(false);
            if(reciever_acc != 0 && sender_account_no != 0){
                PreparedStatement ps = con.prepareStatement("Select * from accounts where account_number = ? and Security_pin = ?");
                ps.setLong(1, sender_account_no);
                ps.setString(2, pin);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    double curr_balance = rs.getDouble("balance");
                    if (amount <= curr_balance) {
                        PreparedStatement ps2 = con.prepareStatement("update accounts set balance = balance - ? where account_number = ?");
                        PreparedStatement ps3 = con.prepareStatement("update accounts set balance = balance + ? where account_number = ?");
                        ps2.setDouble(1, amount);
                        ps2.setLong(2, sender_account_no);
                        ps3.setDouble(1, amount);
                        ps3.setLong(2, reciever_acc);
                        int affected = ps2.executeUpdate();
                        int affected2 = ps3.executeUpdate();
                        if (affected > 0 && affected2 > 0) {
                            System.out.println("Rs." + amount + " Transfer successful");
                            con.commit();
                            con.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("transaction failed");
                            con.rollback();
                            con.setAutoCommit(true);
                        }
                    }else{
                        System.out.println("Insufficient balance");
                    }
                }else{
                    System.out.println("Invalid Security pin");
                }
            }else{
                System.out.println("Invalid account_no.");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void get_balance(long account_no) throws SQLException {
        System.out.println("Enter security pin");
        String pin = sc.nextLine();
        try {
            String q = "Select balance from accounts where account_number = ? and Security_pin = ?";
            PreparedStatement ps = con.prepareStatement(q);
            ps.setLong(1, account_no);
            ps.setString(2, sc.nextLine());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                double bal = rs.getInt("balance");
                System.out.println("balance = " + bal);
                return;
            } else {
                System.out.println("Invalid pin!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
