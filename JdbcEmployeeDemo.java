import java.sql.*;
import java.util.Scanner;

public class JdbcEmployeeDemo {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/company";
        String user = "root";        // your MySQL username
        String pass = "Will$mith23";  // your MySQL password

        Scanner sc = new Scanner(System.in);

        try {
            // 1️⃣ Load and register driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2️⃣ Establish connection
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Connected to the Company database successfully!");

            while (true) {
                System.out.println("\n=== Employee Management Menu ===");
                System.out.println("1. Insert Employee");
                System.out.println("2. Display Employees");
                System.out.println("3. Update Employee");
                System.out.println("4. Delete Employee");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        // ➕ INSERT Operation
                        System.out.print("Enter Employee ID: ");
                        int id = sc.nextInt();
                        sc.nextLine(); // consume newline
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Department: ");
                        String dept = sc.nextLine();
                        System.out.print("Enter Salary: ");
                        double salary = sc.nextDouble();

                        String insertQuery = "INSERT INTO employee VALUES (?, ?, ?, ?)";
                        PreparedStatement psInsert = con.prepareStatement(insertQuery);
                        psInsert.setInt(1, id);
                        psInsert.setString(2, name);
                        psInsert.setString(3, dept);
                        psInsert.setDouble(4, salary);

                        int rowsInserted = psInsert.executeUpdate();
                        System.out.println(rowsInserted + " record inserted successfully!");
                        psInsert.close();
                        break;

                    case 2:
                        // 📋 DISPLAY Operation
                        String selectQuery = "SELECT * FROM employee";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(selectQuery);

                        System.out.println("\nID\tName\tDepartment\tSalary");
                        System.out.println("--------------------------------------------");
                        while (rs.next()) {
                            System.out.println(
                                    rs.getInt("id") + "\t" +
                                    rs.getString("name") + "\t" +
                                    rs.getString("department") + "\t" +
                                    rs.getDouble("salary"));
                        }
                        rs.close();
                        stmt.close();
                        break;

                    case 3:
                        // ✏️ UPDATE Operation
                        System.out.print("Enter Employee ID to update: ");
                        int uid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter new Department: ");
                        String newDept = sc.nextLine();
                        System.out.print("Enter new Salary: ");
                        double newSalary = sc.nextDouble();

                        String updateQuery = "UPDATE employee SET department=?, salary=? WHERE id=?";
                        PreparedStatement psUpdate = con.prepareStatement(updateQuery);
                        psUpdate.setString(1, newDept);
                        psUpdate.setDouble(2, newSalary);
                        psUpdate.setInt(3, uid);

                        int rowsUpdated = psUpdate.executeUpdate();
                        System.out.println(rowsUpdated + " record updated successfully!");
                        psUpdate.close();
                        break;

                    case 4:
                        // ❌ DELETE Operation
                        System.out.print("Enter Employee ID to delete: ");
                        int did = sc.nextInt();

                        String deleteQuery = "DELETE FROM employee WHERE id=?";
                        PreparedStatement psDelete = con.prepareStatement(deleteQuery);
                        psDelete.setInt(1, did);

                        int rowsDeleted = psDelete.executeUpdate();
                        System.out.println(rowsDeleted + " record deleted successfully!");
                        psDelete.close();
                        break;

                    case 5:
                        // 🚪 EXIT
                        System.out.println("Exiting program... Goodbye!");
                        con.close();
                        sc.close();
                        System.exit(0);

                    default:
                        System.out.println("❌ Invalid choice! Try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
