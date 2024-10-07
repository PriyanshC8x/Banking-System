import java.sql.*;
import java.util.*;

public class Accounts {
    private Connection con;
    private Scanner sc;

    public Accounts(Connection con , Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

     public long openAccount(String email) {

         if (!account_exist(email)) {
             String q = "insert into accounts (account_number,full_name,email_id,balance,Security_pin)  values(?,?,?,?,?)";
             sc.nextLine();
             System.out.println("Enter full name: ");
             String fullName = sc.nextLine();
             System.out.println("Enter balance: ");
             double balance = sc.nextDouble();
             sc.nextLine();
             System.out.println("Enter SecurityPin: ");
             String securityPin = sc.nextLine();

             try {
                 long acc_no = GenerateAccountNo();
                 PreparedStatement ps = con.prepareStatement(q);
                 ps.setLong(1, acc_no);
                 ps.setString(2, fullName);
                 ps.setString(3, email);
                 ps.setDouble(4, balance);
                 ps.setString(5, securityPin);
                 int affect = ps.executeUpdate();
                 if(affect > 0) {
                     System.out.println("Account opened");
                     return acc_no;
                 }else {
                     throw new RuntimeException("Account not opened");
                 }

             }catch (Exception e) {
                 e.printStackTrace();
             }
         }
         throw new RuntimeException("Account not opened");
     }

     public long getAccountNumber(String email){
        String q = "select account_number from accounts where email_id = ?";
       try{
           PreparedStatement ps = con.prepareStatement(q);
           ps.setString(1, email);
           ResultSet rs = ps.executeQuery();
           if(rs.next()) {
               return rs.getLong("account_number");
           }else {
               throw new RuntimeException("Account Number not exist");
           }
       }catch (Exception e) {
           e.printStackTrace();
       }
       throw new RuntimeException("Account Number not exist");
     }

     public long GenerateAccountNo() {
        try{
            Statement st = con.createStatement();
            String q = "select account_number from accounts ORDER BY account_number DESC LIMIT 1";
            ResultSet rs = st.executeQuery(q);
            if(rs.next()) {
                long lastAccountNo = rs.getLong("account_number");
                return lastAccountNo + 1;
            }else{
                return 1000100;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 1000100;
     }

     public boolean account_exist(String email){
        String q = "select account_number from accounts where email_id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            }else{
                return false;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
     }
}
