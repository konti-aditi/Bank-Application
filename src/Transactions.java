public class Transactions {
    public static void showTransactions(long contact) {
        try {
            java.sql.Connection con2 = DBConnection.getConnection();
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

