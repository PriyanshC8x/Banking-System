import java.sql.*;
import java.util.*;
public class BankingApp {
    public static void main(String[] args) throws ClassNotFoundException , SQLException {
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            String path = "jdbc:mysql://localhost:3306/Banking_app";
            String username = "root";
            String password = "Mukul@2004";

            Connection con = DriverManager.getConnection(path, username, password);
            Scanner sc = new Scanner(System.in);
            User user = new User(con, sc);
            AccountManager accountManager = new AccountManager(con , sc);
            Accounts accounts = new Accounts(con , sc);

            String email;
            Long account_number;

            while(true) {
                System.out.print("|| WELCOME TO BANKING SYSTEM ||");
                System.out.println();
                System.out.print("1.Register\n2.Login\n3.Exit");
                System.out.println();
                System.out.println("Enter your choice");
                int choice = sc.nextInt();
                switch(choice) {
                    case 1:
                        System.out.print("Registration Window");
                        System.out.println();
                        user.Register();
                        break;
                    case 2:
                        System.out.print("Login Window");
                        System.out.println();
                        email = user.Login();
                        if(email != null) {
                            System.out.println();
                            System.out.println("Your are logged in");
                            if(!accounts.account_exist(email)) {
                                System.out.println();
                                System.out.println("1.OPEN AN ACCOUNT");
                                System.out.println("2.EXIT");
                                if(sc.nextInt() == 1){
                                    account_number = accounts.openAccount(email);
                                    System.out.println("Account opened successfully");
                                    System.out.println("Account number: " + account_number);
                                }
                                else{
                                    break;
                                }
                            }
                            account_number = accounts.getAccountNumber(email);
                            System.out.println();
                            int choice2 = 0;
                            while(choice2 != 5) {
                                System.out.println("1.Debit money\n2.Credit money\n3.transfer money\n4.check Balance\n5.Logout");
                                System.out.println();
                                System.out.println("Enter your choice");
                                choice2 = sc.nextInt();
                                switch(choice2){
                                    case 1:
                                        accountManager.debit_money(account_number);
                                        System.out.println();
                                        break;
                                    case 2:
                                        accountManager.credit_money(account_number);
                                        System.out.println();
                                        break;
                                    case 3:
                                        accountManager.transfer_money(account_number);
                                        System.out.println();
                                        break;
                                    case 4:
                                        accountManager.get_balance(account_number);
                                        System.out.println();
                                        break;
                                    case 5:
                                        break;
                                    default:
                                        System.out.println("Invalid choice");
                                        break;
                                }
                            }

                        }else{
                            System.out.print("Incorrect Email_id and Password ");
                        }
                    case 3:
                        System.out.println();
                        System.out.println("|| THANK YOU FOR USING OUR BANKING SYSTEM ||");
                        return;
                        default:
                            System.out.println("Invalid choice");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
