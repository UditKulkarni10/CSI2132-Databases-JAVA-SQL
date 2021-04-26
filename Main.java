import javax.xml.transform.Result;
import java.sql.*;
import java.util.*;

import static java.sql.Types.NULL;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://web0.site.uottawa.ca:15432/group_109";
        // write your code here
        System.out.println("Welcome to the Renterino!");
        System.out.println("Are you a Host(0) or a Guest(1)? ");
        Scanner input = new Scanner(System.in);
        int choice = input.nextInt();
        try{
            Connection conn = DriverManager.getConnection(url, "ukulk082", "ProEvolution2016");
            if (choice == 0) {
                boolean x = true;
                int userName;
                while (x) {
                    System.out.println("Enter your hostID: ");
                    userName = input.nextInt();
                    PreparedStatement hps = conn.prepareStatement("select host_id from dbproject.host where host_id = ?");
                    hps.setInt(1, userName);
                    ResultSet hrs = hps.executeQuery();
                    if (hrs.next()) {
                        System.out.println("Exists");
                        Statement userInfo = conn.createStatement();
                        ResultSet userInfo1 = userInfo.executeQuery("select * from dbproject.host where host_id =" + userName);
                        while (userInfo1.next()) {
                            String name = userInfo1.getString("name");
                            System.out.println("Welcome " + name);
                        }

                        System.out.println("");
                        System.out.println("Here are your current listings...");
                        Statement ust = conn.createStatement();
                        ResultSet hpl = ust.executeQuery("select * from dbproject.property where owner_id = " + userName);
                        ResultSetMetaData hplm = hpl.getMetaData();
                        int colNum = hplm.getColumnCount();
                        while (hpl.next()) {
                            for (int i = 1; i <= colNum; i++) {
                                String name = hplm.getColumnName(i);
                                System.out.print(name + " : " + hpl.getString(i) + " | ");
                            }
                            System.out.println();
                        }
                        boolean y = true;
                        while (y) {
                            System.out.println("What would you like to do?");
                            System.out.println("0. View my listings");
                            System.out.println("1. Upload a listing");
                            System.out.println("2. Delete a listing");
                            System.out.println("3. Exit the application");
                            int upload = input.nextInt();
                            if (upload == 1) {
                                boolean uploadFail = true;
                                while(uploadFail) {

                                    try {
                                        int propertyId = 0;

                                        for (int i = 2000; i <= 10000; i++) {
                                            propertyId = i;
                                            PreparedStatement check = conn.prepareStatement("select * from dbproject.property where property_id = ?");
                                            check.setInt(1, propertyId);
                                            ResultSet checkpID = check.executeQuery();

                                            if (!checkpID.next()) {
                                                break;
                                            }
                                        }

                                        System.out.println("What is the property type?");
                                        input.nextLine();
                                        String pType = input.nextLine();

                                        System.out.println("What is the price you are putting it out for?");
                                        int price = input.nextInt();

                                        System.out.println("What is the maximum occupancy limit?");
                                        int maxOcc = input.nextInt();

                                        System.out.println("What is the minimum occupancy limit?");
                                        int minOcc = input.nextInt();

                                        System.out.println("What are the rules? 2 rules only. Enter stop to end. ");
                                        String[] array = new String[3];
                                        for (int i = 0; i < 3; i++) {
                                            String s = input.nextLine();
                                            if (s.matches("stop")) {
                                                break;
                                            } else {
                                                array[i] = s;
                                            }
                                        }
                                        String rules = "{" + array[1] + "," + array[2] + "}";

                                        System.out.println("What are the amenities?\nIn this order #Bath, #Bedrooms, #Beds, #Kitchen\nEnter -1 when done. ");
                                        int[] amen_array = new int[4];
                                        for (int i = 0; i < 4; i++) {
                                            int s = input.nextInt();
                                            if (s == -1) {
                                                break;
                                            } else {
                                                amen_array[i] = s;
                                            }
                                        }

                                        String amenities = "(" + amen_array[0] + "," + amen_array[1] + "," + amen_array[2] + "," + amen_array[3] + "," + "'t'" + ")";

                                        System.out.println("What are the fees?\nCleaning fees, Transaction fees, restocking fees\nEnter -1 when done. ");
                                        int[] fees_array = new int[3];
                                        for (int i = 0; i < 3; i++) {
                                            int s = input.nextInt();
                                            if (s == -1) {
                                                break;
                                            } else {
                                                fees_array[i] = s;
                                            }
                                        }
                                        String fees = "(" + fees_array[0] + "," + fees_array[1] + "," + fees_array[2] + ")";


                                        String[] addr_array = new String[4];
                                        System.out.println("What is the address? Enter stop when done. ");
                                        int a = input.nextInt();
                                        input.nextLine();
                                        for (int i = 0; i < 4; i++) {
                                            String s = input.nextLine();
                                            if (s.matches("stop")) {
                                                break;
                                            } else {
                                                addr_array[i] = s;
                                            }
                                        }
                                        String address = ("(" + a + "," + "'" + addr_array[0] + "'" + "," + "'" +
                                                addr_array[1] + "'" + "," + "'" + addr_array[2] + "'" + "," + "'" + addr_array[3] + "'" + ")");


                                        Statement insertProp = conn.createStatement();
                                        insertProp.executeUpdate("INSERT INTO dbproject.property VALUES (" + propertyId + "," + "'" + pType + "'" + "," +
                                                price + "," + maxOcc + "," + minOcc + "," + "'" + rules + "'" + "," + "ROW" + amenities + "," + userName +
                                                "," + "NULL" + "," + "ROW" + fees + "," + "ROW" + address + ", '{0}' )");

                                        //Write code for uploading new listing to the database

                                        System.out.println("Your upload was successful");
                                        System.out.println();
                                        System.out.println();
                                        uploadFail = false;

                                    }catch(Exception e){
                                        System.out.println("Invalid input, please try again");
                                    }
                                }
                            }
                            if (upload == 2) {
                                System.out.println("Enter the property id of the listing you would like to delete: ");
                                int propId = input.nextInt();
                                Statement deleteProp = conn.createStatement();
                                deleteProp.execute("DELETE FROM dbproject.property WHERE property_id = " + propId + "AND owner_id = " + userName);

                                //write code for deleting specific listing from the data base

                                System.out.println("The property was deleted successfully");
                                System.out.println();
                                System.out.println();
                            }

                            if (upload == 0) {
                                System.out.println("");
                                System.out.println("Here are your current listings...");
                                Statement ust2 = conn.createStatement();
                                ResultSet hplr = ust2.executeQuery("select * from dbproject.property where owner_id = " + userName);
                                ResultSetMetaData hplmr = hplr.getMetaData();
                                int colNum1 = hplmr.getColumnCount();
                                while (hplr.next()) {
                                    for (int i = 1; i <= colNum1; i++) {
                                        String name = hplmr.getColumnName(i);
                                        System.out.print(name + " : " + hplr.getString(i) + " | ");
                                    }
                                    System.out.println();
                                }
                            }

                            if (upload == 3) {
                                break;
                            }
                        }

                        x = false;

                    } else {
                        System.out.println("Does not exist");

                    }
                }
            }

            //--------------------------------------START OF USER PROGRAM----------------------------------------------
            else{
                System.out.println("You are a Guest");

                boolean userIdFalse = true;

                while(userIdFalse) {
                    System.out.println("Enter your guest ID");
                    int userId = input.nextInt();
                    boolean x = true;

                    PreparedStatement hps = conn.prepareStatement("select guest_id from dbproject.guest where guest_id = ?");
                    hps.setInt(1, userId);
                    ResultSet hrs = hps.executeQuery();

                    if (hrs.next()) {

                        Statement userInfo = conn.createStatement();
                        ResultSet welcomeUser = userInfo.executeQuery("select * from dbproject.guest where guest_id = " + userId);

                        //Welcome the user
                        while (welcomeUser.next()) {
                            String name = welcomeUser.getString("name");
                            System.out.println("Welcome" + name);
                            System.out.println();
                        }

                        while (x) {

                            System.out.println("What would you like to do? ");
                            System.out.println("0: View your booked properties");
                            System.out.println("1.View listings");
                            System.out.println("2.Book a property");
                            System.out.println("3. Exit the application");
                            int userChoice = input.nextInt();

                            //Exit the application
                            if (userChoice == 3) {
                                userIdFalse = false;
                                break;
                            }

                            //View all booked properties

                            if (userChoice == 0) {
                                System.out.println("Here are your booked properties");
                                Statement bookStmt = conn.createStatement();
                                ResultSet dateSet = bookStmt.executeQuery("Select booked_properties FROM dbproject.guest WHERE guest_id = " + userId);

                                while (dateSet.next()) {
                                    Array propArray = dateSet.getArray("booked_properties");
                                    try {
                                        Integer[] guestProp = (Integer[]) propArray.getArray();
                                        for (Integer e : guestProp) {
                                            Statement book = conn.createStatement();
                                            ResultSet propView = book.executeQuery("select property_id, property_type, price, max_occupancy, amenities, reviews, address " +
                                                    "from dbproject.property where property_id = " + e.toString());
                                            ResultSetMetaData propViewTab = propView.getMetaData();
                                            int colNum = propViewTab.getColumnCount();
                                            while (propView.next()) {
                                                for (int i = 1; i <= colNum; i++) {
                                                    String name = propViewTab.getColumnName(i);
                                                    System.out.print(name + " : " + propView.getString(i) + " | ");
                                                }
                                                System.out.println();
                                            }

                                        }
                                    } catch (NullPointerException e) {
                                        System.out.println("You have no bookings");
                                        System.out.println();
                                    }
                                }
                            }

                            //View listings posted by hosts

                            if (userChoice == 1) {
                                System.out.println("How would you like to view the listing? ");
                                System.out.println("1.View all listings");
                                System.out.println("2.Sort listings");

                                int userView = input.nextInt();

                                if (userView == 1) {
                                    System.out.println("Here are all the listings");
                                    System.out.println();
                                    Statement all = conn.createStatement();
                                    ResultSet allView = all.executeQuery("select property_id, property_type, price, max_occupancy, amenities, reviews, address from dbproject.property");
                                    ResultSetMetaData allViewTab = allView.getMetaData();
                                    int colNum = allViewTab.getColumnCount();
                                    while (allView.next()) {
                                        for (int i = 1; i <= colNum; i++) {
                                            String name = allViewTab.getColumnName(i);
                                            System.out.print(name + " : " + allView.getString(i) + " | ");
                                        }
                                        System.out.println();
                                    }
                                }

                                if (userView == 2) {
                                    System.out.println("Here are the sorting parameters");
                                    System.out.println("Enter the city you want the listing in");
                                    String cityChoice = input.next();
                                    String citySort = "((address).city = " + "'" + cityChoice + "')";

//                                System.out.println("Enter the property type");
//                                String propType = input.next();
//                                String propertySort = "property_type = " + "'" + propType + "'";

                                    System.out.println("Enter the maximum price");
                                    int maxPrice = input.nextInt();
                                    System.out.println("Enter the minimum price");
                                    int minPrice = input.nextInt();
                                    String priceSort = "and (price between " + minPrice + " and " + maxPrice + ")";

                                    System.out.println("Enter the dates you want to book the property for...(ddmmyyyy)\nEnter -1 to stop. ");
                                    //ArrayList<Integer> dates = new ArrayList<Integer>();
                                    ArrayList<Integer> dates2 = new ArrayList<Integer>();
                                    String dataArray2 = "{";
                                    boolean y = true;
                                    String separator2 = "";
                                    while (y) {
                                        Integer s = input.nextInt();
                                        if (s == -1) {
                                            y = false;
                                        } else {
                                            dates2.add(s);
                                            dataArray2 += (separator2 + s.toString());
                                            separator2 = ",";
                                        }
                                    }
                                    System.out.println();
                                    dataArray2 += "}";

                                    String dateSort = "and not( " + "'" + dataArray2 + "'" + " && " + "dates_booked" + ")";
                                    
                                    Statement sort2 = conn.createStatement();
                                    ResultSet checkSortedView = sort2.executeQuery("select property_id, property_type, price, max_occupancy, amenities, reviews, address from dbproject.property " +
                                            " where " + citySort + priceSort + dateSort);
                                    
                                    Statement sort = conn.createStatement();
                                    ResultSet sortedView = sort.executeQuery("select property_id, property_type, price, max_occupancy, amenities, reviews, address from dbproject.property " +
                                            " where " + citySort + priceSort + dateSort);
                                    
                                    ResultSetMetaData sortedViewTab = sortedView.getMetaData();
                                    int colNum = sortedViewTab.getColumnCount();

                                    System.out.println();
                                    System.out.println("Here are the properties: ");
                                    if (checkSortedView.next()) {
                                        while (sortedView.next()) {
                                            for (int i = 1; i <= colNum; i++) {
                                                String name = sortedViewTab.getColumnName(i);
                                                System.out.print(name + " : " + sortedView.getString(i) + " | ");
                                            }
                                            System.out.println();
                                        }
                                    } else {
                                        System.out.println("No properties that match your search");
                                    }
                                }


                            }

                            //Book a property

                            if (userChoice == 2) {
                                boolean j = true;
                                while (j) {
                                System.out.println("Enter the property ID of the property you want to book");
                                int propChoice = input.nextInt();
                                PreparedStatement checkProp = conn.prepareStatement("select property_id from dbproject.property where property_id =" + propChoice);
                                ResultSet checked = checkProp.executeQuery();
                                if(checked.next()) {
                                        System.out.println("Enter the dates you want to book the property for...(ddmmyyyy)\nEnter -1 to stop. ");
                                        //ArrayList<Integer> dates = new ArrayList<Integer>();
                                        ArrayList<Integer> dates = new ArrayList<Integer>();
                                        String dataArray = "{";
                                        boolean y = true;
                                        String separator = "";
                                        while (y) {
                                            Integer s = input.nextInt();
                                            if (s == -1) {
                                                y = false;
                                            } else {
                                                dates.add(s);
                                                dataArray += (separator + s.toString());
                                                separator = ",";
                                            }
                                        }

                                        System.out.println();
                                        dataArray += "}";

                                        Statement dateStmt = conn.createStatement();
                                        ResultSet dateSet = dateStmt.executeQuery("Select dates_booked FROM dbproject.property WHERE property_id = " + propChoice);

                                        while (dateSet.next()) {
                                            Array datesArr = dateSet.getArray("dates_booked");
                                            ArrayList<Integer> common = new ArrayList<Integer>();
                                            try {
                                                Integer[] datesZ = (Integer[]) datesArr.getArray();
                                                System.out.println("Here");
                                                for (Integer e : datesZ) {
                                                    for (Integer k : dates) {
                                                        if (e.equals(k)) {
                                                            System.out.println("This property is booked on " + e.toString() + "\n Please change your dates");
                                                            common.add(k);
                                                            j = false;
                                                            break;
                                                        }
                                                    }
                                                }

                                            } catch (NullPointerException e) {
                                                System.out.println("No bookings");
                                            }

                                            if (common.size() > 0) {
                                                System.out.println("Your booking could not be made");
                                                break;
                                            } else {
                                                System.out.println("Your booking is being made....");
                                                Statement update = conn.createStatement();

                                                update.execute("UPDATE dbproject.property set dates_booked = dates_booked || " + "'" + dataArray + "'" + "WHERE property_id = " + propChoice);
                                                update.execute("UPDATE dbproject.guest set booked_properties = booked_properties ||" + "'{" + propChoice + "}'" + "Where guest_id = " + userId);

                                                System.out.println("Your booking is confirmed");
                                                j = false;
                                            }
                                        }
                                        System.out.println();
                                    }else{
                                    System.out.println("This property does not exist");
                                    System.out.println();
                                }
                                }

                            }
                            System.out.println();

                        }
                    } else {
                        System.out.println("Not a valid user");

                    }
                }
            }

            } catch (SQLException e) {
                System.out.println("SQL error");
                e.printStackTrace();
            }
    }
}

