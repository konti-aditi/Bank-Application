public class Login {
    public static void login(java.util.Scanner sc) {
        System.out.println("\nLOGIN MODULE");
        System.out.print("Contact: ");
        long userContact = sc.nextLong();
        System.out.print("PIN: ");
        int userPin = sc.nextInt();
        try {
            java.sql.Connection con = DBConnection.getConnection();
            String query = "SELECT * FROM users WHERE contact=? AND pin=?";
            java.sql.PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1, userContact);
            ps.setInt(2, userPin);
            java.sql.ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                double balance = rs.getDouble("balance");
                long contact = rs.getLong("contact");
                int pin = rs.getInt("pin");
                System.out.println("\nLOGIN SUCCESSFUL");
                features:
                while (true) {
                    System.out.println("\n1.DEPOSIT\n2.WITHDRAW\n3.CHECK BALANCE\n4.TRANSACTIONS\n5.LOGOUT");
                    System.out.print("Enter option: ");
                    int opt = sc.nextInt();
                    switch (opt) {
                        case 1:
                            Deposit.deposit(contact, balance, sc);
                            break;
                        case 2:
                            Withdraw.withdraw(contact, balance, sc);
                            break;
                        case 3:
                            Balance.checkBalance(balance);
                            break;
                        case 4:
                            Transactions.showTransactions(contact);
                            break;
                        case 5:
                            System.out.println("Logged out");
                            return;
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
    }
    }

