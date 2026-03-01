public class Deposit {
    public static void deposit(long contact, java.util.Scanner sc) {
        System.out.print("Enter your PIN: ");
        int enteredPin = sc.nextInt();
        try {
            java.sql.Connection con2 = DBConnection.getConnection();
            java.sql.PreparedStatement pinStmt = con2.prepareStatement(
                "SELECT pin FROM users WHERE contact=?");
            pinStmt.setLong(1, contact);
            java.sql.ResultSet rs = pinStmt.executeQuery();
            if (rs.next()) {
                int actualPin = rs.getInt("pin");

                if (enteredPin != actualPin) {
                    System.out.println("Invalid PIN. Transaction cancelled.");
                    return;
                }
                
            } else {
                System.out.println("Account not found. Transaction cancelled.");
                return;
            }
            System.out.print("Enter amount: ");
            double amt = sc.nextDouble();

            //  Fetch latest balance form db:

            java.sql.PreparedStatement getBal = con2.prepareStatement(
                    "SELECT balance FROM users WHERE contact=?"
            );
            getBal.setLong(1, contact);
            java.sql.ResultSet balRs = getBal.executeQuery();

            double currentBalance = 0;

            if (balRs.next()) {
                currentBalance = balRs.getDouble("balance");
            }

           // Updating the  amount:
            double newBalance = currentBalance + amt;

            java.sql.PreparedStatement ps1 = con2.prepareStatement(
                "UPDATE users SET balance=? WHERE contact=?");
            ps1.setDouble(1, newBalance);
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

