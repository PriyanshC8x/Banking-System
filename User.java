import java.sql.*;
import java.util.*;

public class User {
    private Connection con;
    private Scanner sc;
    public User(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void Register(){
        sc.nextLine();
        System.out.println("Enter full_name: ");
        String full_name = sc.nextLine();
        System.out.println("Enter email: ");
        String email = sc.nextLine();
        System.out.println("Enter password: ");
        String password = sc.nextLine();
        if(Exist_User(email)){
            System.out.println("Email already exists!");
            return;
        }
        try {
            String q = "insert into user(full_name,email_id,password) values(?,?,?)";

            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, full_name);
            ps.setString(2, email);
            ps.setString(3, password);

            int affectedRows = ps.executeUpdate();
            if(affectedRows > 0){
                System.out.println("User successfully registered!");
            }else {
                System.out.println("registration failed!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public String Login(){
        sc.nextLine();
        System.out.println("Enter email: ");
        String email = sc.nextLine();
        System.out.println("Enter password: ");
        String password = sc.nextLine();
        String q = "select * from user where email_id = ? and password=?";
        try{
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return email;
            }else{
                return null;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public boolean Exist_User(String email){
        String q = "select * from user where email_id=?";
        try {
            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
