import java.sql.*;
import java.util.Scanner;

class Bank {

    static String name;
    static String address;
    static long contact;
    static long aadhaar;
    static String pancard;
    static double balance;
    static int pin;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        welcomeLoop:
        for (;;) {

            System.out.println("\n******** WELCOME TO BANK OF INDIA ********");
            System.out.println("1.CREATE ACCOUNT\n2.LOGIN");
            System.out.print("Enter your response: ");
            int resp = sc.nextInt();

            switch (resp) {

                // ================= CREATE ACCOUNT =================
                case 1: {

                    System.out.println("\nACCOUNT CREATION PAGE");

                    System.out.print("NAME: ");
                    sc.nextLine();
                    name = sc.nextLine();

                    System.out.print("Address: ");
                    address = sc.nextLine();

                    System.out.print("Contact: ");
                    contact = sc.nextLong();

                    System.out.print("Aadhaar: ");
                    aadhaar = sc.nextLong();

                    System.out.print("Pancard: ");
                    pancard = sc.next();

                    System.out.print("Initial Amount: ");
                    balance = sc.nextDouble();

                    System.out.print("Set PIN: ");
                    pin = sc.nextInt();

                    try {
                        Connection con = DBConnection.getConnection();

                        String query = "INSERT INTO users(name,address,contact,aadhaar,pancard,balance,pin) VALUES(?,?,?,?,?,?,?)";
                        PreparedStatement ps = con.prepareStatement(query);

                        ps.setString(1, name);
                        ps.setString(2, address);
                        ps.setLong(3, contact);
                        ps.setLong(4, aadhaar);
                        ps.setString(5, pancard);
                        ps.setDouble(6, balance);
                        ps.setInt(7, pin);

                        ps.executeUpdate();

                        // Save creation transaction
                        PreparedStatement ps2 = con.prepareStatement(
                                "INSERT INTO transactions(contact,type,amount,time) VALUES(?,?,?,NOW())");
                        ps2.setLong(1, contact);
                        ps2.setString(2, "ACCOUNT CREATED");
                        ps2.setDouble(3, balance);
                        ps2.executeUpdate();

                        System.out.println("ACCOUNT CREATED SUCCESSFULLY");

                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    break;
                }

                // ================= LOGIN =================
                case 2: {

                    System.out.println("\nLOGIN MODULE");

                    System.out.print("Contact: ");
                    long userContact = sc.nextLong();

                    System.out.print("PIN: ");
                    int userPin = sc.nextInt();

                    try {
                        Connection con = DBConnection.getConnection();

                        String query = "SELECT * FROM users WHERE contact=? AND pin=?";
                        PreparedStatement ps = con.prepareStatement(query);

                        ps.setLong(1, userContact);
                        ps.setInt(2, userPin);

                        ResultSet rs = ps.executeQuery();

                        if (rs.next()) {

                            name = rs.getString("name");
                            balance = rs.getDouble("balance");
                            contact = rs.getLong("contact");
                            pin = rs.getInt("pin");

                            System.out.println("\nLOGIN SUCCESSFUL");

                            // ========== FEATURES ==========
                            features:
                            for (;;) {

                                System.out.println("\n1.DEPOSIT\n2.WITHDRAW\n3.CHECK BALANCE\n4.TRANSACTIONS\n5.LOGOUT");
                                System.out.print("Enter option: ");
                                int opt = sc.nextInt();

                                switch (opt) {

                                    // ===== DEPOSIT =====
                                    case 1: {
                                        System.out.print("Enter amount: ");
                                        double amt = sc.nextDouble();

                                        balance += amt;

                                        Connection con2 = DBConnection.getConnection();

                                        PreparedStatement ps1 = con2.prepareStatement(
                                                "UPDATE users SET balance=? WHERE contact=?");
                                        ps1.setDouble(1, balance);
                                        ps1.setLong(2, contact);
                                        ps1.executeUpdate();

                                        PreparedStatement ps2 = con2.prepareStatement(
                                                "INSERT INTO transactions(contact,type,amount,time) VALUES(?,?,?,NOW())");
                                        ps2.setLong(1, contact);
                                        ps2.setString(2, "DEPOSIT");
                                        ps2.setDouble(3, amt);
                                        ps2.executeUpdate();

                                        System.out.println("Amount Deposited");
                                        break;
                                    }

                                    // ===== WITHDRAW =====
                                    case 2: {
                                        System.out.print("Enter amount: ");
                                        double amt = sc.nextDouble();

                                        if (amt <= balance) {

                                            balance -= amt;

                                            Connection con2 = DBConnection.getConnection();

                                            PreparedStatement ps1 = con2.prepareStatement(
                                                    "UPDATE users SET balance=? WHERE contact=?");
                                            ps1.setDouble(1, balance);
                                            ps1.setLong(2, contact);
                                            ps1.executeUpdate();

                                            PreparedStatement ps2 = con2.prepareStatement(
                                                    "INSERT INTO transactions(contact,type,amount,time) VALUES(?,?,?,NOW())");
                                            ps2.setLong(1, contact);
                                            ps2.setString(2, "WITHDRAW");
                                            ps2.setDouble(3, amt);
                                            ps2.executeUpdate();

                                            System.out.println("Amount Withdrawn");

                                        } else {
                                            System.out.println("Insufficient Balance");
                                        }

                                        break;
                                    }

                                    // ===== BALANCE =====
                                    case 3:
                                        System.out.println("Balance: " + balance);
                                        break;

                                    // ===== TRANSACTIONS =====
                                    case 4: {
                                        Connection con2 = DBConnection.getConnection();

                                        PreparedStatement ps1 = con2.prepareStatement(
                                                "SELECT * FROM transactions WHERE contact=?");
                                        ps1.setLong(1, contact);

                                        ResultSet trs = ps1.executeQuery();

                                        System.out.println("\n--- TRANSACTIONS ---");
                                        while (trs.next()) {
                                            System.out.println(
                                                    trs.getString("type") + " | "
                                                            + trs.getDouble("amount") + " | "
                                                            + trs.getTimestamp("time"));
                                        }
                                        break;
                                    }

                                    case 5:
                                        System.out.println("Logged out");
                                        continue welcomeLoop;

                                    default:
                                        System.out.println("Invalid Option");
                                }
                            }

                        } else {
                            System.out.println("Invalid Credentials");
                        }

                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    break;
                }

                default:
                    System.out.println("Invalid Choice");
            }
        }
    }
}
