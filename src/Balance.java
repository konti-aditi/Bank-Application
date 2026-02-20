public class Balance {
    public static void checkBalance(long contact, double balance, java.util.Scanner sc) {
        System.out.print("Enter your PIN: ");
        int enteredPin = sc.nextInt();
        try {
            java.sql.Connection con2 = DBConnection.getConnection();
            java.sql.PreparedStatement pinStmt = con2.prepareStatement(
                "SELECT pin, balance FROM users WHERE contact=?");
            pinStmt.setLong(1, contact);
            java.sql.ResultSet rs = pinStmt.executeQuery();
            if (rs.next()) {
                int actualPin = rs.getInt("pin");
                if (enteredPin != actualPin) {
                    System.out.println("Invalid PIN. Cannot show balance.");
                    return;
                }
                double latestBalance = rs.getDouble("balance");
                System.out.println("Balance: " + latestBalance);
            } else {
                System.out.println("Account not found.");
                return;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    }
