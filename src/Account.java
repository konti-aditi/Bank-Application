public class Account {
    public static void createAccount(java.util.Scanner sc) {
        System.out.println("\nACCOUNT CREATION PAGE");
        System.out.print("NAME: ");
        sc.nextLine();
        String name = sc.nextLine();
        System.out.print("Address: ");
        String address = sc.nextLine();
        System.out.print("Contact: ");
        long contact = sc.nextLong();
        System.out.print("Aadhaar: ");
        long aadhaar = sc.nextLong();
        System.out.print("Pancard: ");
        String pancard = sc.next();
        System.out.print("Initial Amount: ");
        double balance = sc.nextDouble();
        System.out.print("Set PIN: ");
        int pin = sc.nextInt();
        try {
            java.sql.Connection con = DBConnection.getConnection();
            String query = "INSERT INTO users(name,address,contact,aadhaar,pancard,balance,pin) VALUES(?,?,?,?,?,?,?)";
            java.sql.PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setLong(3, contact);
            ps.setLong(4, aadhaar);
            ps.setString(5, pancard);
            ps.setDouble(6, balance);
            ps.setInt(7, pin);
            ps.executeUpdate();
            java.sql.PreparedStatement ps2 = con.prepareStatement(
                "INSERT INTO transactions(contact,type,amount,time) VALUES(?,?,?,NOW())");
            ps2.setLong(1, contact);
            ps2.setString(2, "ACCOUNT CREATED");
            ps2.setDouble(3, balance);
            ps2.executeUpdate();
            System.out.println("ACCOUNT CREATED SUCCESSFULLY");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    }

