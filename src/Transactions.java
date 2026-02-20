public class Transactions {
    public static void showTransactions(long contact, java.util.Scanner sc) {
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
                    System.out.println("Invalid PIN. Cannot show transactions.");
                    return;
                }
            } else {
                System.out.println("Account not found.");
                return;
            }
            java.sql.PreparedStatement ps1 = con2.prepareStatement(
                "SELECT * FROM transactions WHERE contact=?");
            ps1.setLong(1, contact);
            java.sql.ResultSet trs = ps1.executeQuery();
            System.out.println("\n--- TRANSACTIONS ---");
            while (trs.next()) {
                System.out.println(
                    trs.getString("type") + " | "
                    + trs.getDouble("amount") + " | "
                    + trs.getTimestamp("time"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    }

