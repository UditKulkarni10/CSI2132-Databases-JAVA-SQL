import java.sql.*;
import java.sql.DriverManager;
import java.util.*;

public class Admin {


    public static void main(String [] args) {
        String url = "jdbc:postgresql://web0.site.uottawa.ca:15432/group_109";
        Scanner input = new Scanner(System.in);
        boolean y = true;
        System.out.println("Welcome Database Administrator");
        System.out.println();

        while (y) {

            try {
                boolean x = true;
                Connection admin = DriverManager.getConnection(url, "ukulk082", "ProEvolution2016");
                Statement update = admin.createStatement();
                while (x) {


                    System.out.println("What would you like to do?");
                    System.out.println("1. Update or delete");
                    System.out.println("2. Run select queries to output tables");
                    System.out.println("3. Exit the program");

                    int choice = input.nextInt();
                    if (choice == 1) {
                        System.out.println("Enter your SQL code. ");
                        input.nextLine();
                        String sql = input.nextLine();
                        Thread.sleep(1000);

                        update.execute(sql);
                        System.out.println();
                        System.out.println("Changes were made successfully to the database");
                    }

                    if (choice == 2) {
                        System.out.println("Enter your sql code");
                        input.nextLine();
                        String sql = input.nextLine();
                        Thread.sleep(1000);

                        ResultSet hplr = update.executeQuery(sql);
                        ResultSetMetaData hplmr = hplr.getMetaData();
                        int colNum1 = hplmr.getColumnCount();
                        System.out.println("Here is your resulting table");
                        System.out.println();
                        while (hplr.next()) {
                            for (int i = 1; i <= colNum1; i++) {
                                String name = hplmr.getColumnName(i);
                                System.out.print(name + " : " + hplr.getString(i) + " | ");
                            }
                            System.out.println();
                        }
                    }

                    if(choice == 3){
                        y = false;
                        break;
                    }
                }


            } catch (SQLException | InterruptedException e) {
                System.out.println();
                System.out.println("There is an error ion your SQL code, here is the traceback please try again...");
                e.printStackTrace();
            }
        }
    }
}
