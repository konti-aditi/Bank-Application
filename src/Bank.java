import java.sql.*;
import java.util.Scanner;
// Remove invalid imports. Use fully qualified class names if not in package.

class Bank {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        welcomeLoop:
        while (true) {
            System.out.println("\n******** WELCOME TO BANK OF INDIA ********");
            System.out.println("1.CREATE ACCOUNT\n2.LOGIN");
            System.out.print("Enter your response: ");
            int resp = sc.nextInt();
            switch (resp) {
                case 1:
                    Account.createAccount(sc);
                    break;
                case 2:
                    Login.login(sc);
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}
