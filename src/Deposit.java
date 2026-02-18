public class Deposit {
    public static void deposit(long contact, double balance, java.util.Scanner sc) {
        System.out.print("Enter amount: ");
        double amt = sc.nextDouble();
        balance += amt;
        try {
            java.sql.Connection con2 = DBConnection.getConnection();
            java.sql.PreparedStatement ps1 = con2.prepareStatement(
                "UPDATE users SET balance=? WHERE contact=?");
            ps1.setDouble(1, balance);
            ps1.setLong(2, contact);
            ps1.executeUpdate();
            java.sql.PreparedStatement ps2 = con2.prepareStatement(
                "INSERT INTO transactions(contact,type,amount,time) VALUES(?,?,?,NOW())");
            ps2.setLong(1, contact);
            ps2.setString(2, "DEPOSIT");
            ps2.setDouble(3, amt);
            ps2.executeUpdate();
            System.out.println("Amount Deposited");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    }

