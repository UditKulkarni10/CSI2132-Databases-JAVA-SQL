package com.company;


	import java.sql.*;
	import java.sql.DriverManager;
	import java.util.*;

	public class Employee_Interface {
		
	    public static void main(String[] args) {
	        String url = "jdbc:postgresql://web0.site.uottawa.ca:15432/group_109";
	        System.out.println("Welcome to the Employee Login");
	        System.out.println("Here you will be able to see all the details about your job.");
	        try {
	            Scanner input = new Scanner(System.in);
	            Connection userConn = DriverManager.getConnection(url, "ukulk082", "ProEvolution2016");
	            System.out.println("Enter your employee ID");
	            int userId = input.nextInt();
	            boolean x = true;

	            PreparedStatement hps = userConn.prepareStatement("select employee_id from dbproject.employees where employee_id = ?");
	            hps.setInt(1, userId);
	            ResultSet hrs = hps.executeQuery();

	            if (hrs.next()) {

	                Statement userInfo = userConn.createStatement();
	                ResultSet welcomeUser = userInfo.executeQuery("select * from dbproject.employees where employee_id = " + userId);

	                //Welcome the user
	                while (welcomeUser.next()) {
	                    String name = welcomeUser.getString("name");
	                    System.out.println("Welcome" + name);
	                }

	                while (x) {

	                    System.out.println("What would you like to do? ");
	                    System.out.println("1.View employee information");
	                    System.out.println("2.View current job information");
	                    System.out.println("3.View listing information");
	                    System.out.println("4.View adventure information");
	                    System.out.println("5.View experience information");
	                    int userChoice = input.nextInt();

	                    if (userChoice == 1) {
	                       ResultSet employeeInfo = userInfo.executeQuery("select * from dbproject.employees where employee_id = " + userId);
	                       
	                       while (employeeInfo.next()) {
	                    	   String name = employeeInfo.getString("name");
	                    	   String address = employeeInfo.getString("address");
	                    	   System.out.println(name + address);
	                       }
	                       
	                       System.out.println("Would you like to continue? Press (0) to exit or (1) to continue.");
	                       int exitChoice = input.nextInt();
	                       
	                       if (exitChoice == 0) {
	                    	  x = false; 
	                       }
	                       
	                       else {
	                       continue;
	                       }
	                       
	                       
	                    }

	                    if (userChoice == 2) {
	                    	 ResultSet employeeInfo = userInfo.executeQuery("select * from dbproject.employees where employee_id = " + userId);
	                    	 
	                         while (employeeInfo.next()) {
	                      	   String position = employeeInfo.getString("position");
	                      	   String salary = employeeInfo.getString("salary");
	                      	   String branch = employeeInfo.getString("branch");
	                      	   System.out.println("Your current position is " + position + ", at the " + branch + " branch, where you're making " + salary +" dollars annually.");
	                         }
	                         
	                         System.out.println("Would you like to continue? Press (0) to exit or (1) to continue.");
	                         int exitChoice = input.nextInt();
	                         
	                         if (exitChoice == 0) {
	                      	  x = false; 
	                         }
	                         
	                         else {
	                         continue;
	                         }
	                    }
	                    
	                    if (userChoice == 3) {
	                    	
	                    	System.out.println("Enter the ID of the listing you'd like to view.");
	                    	int propertyChoice = input.nextInt();
	                    	ResultSet propertyInfo = userInfo.executeQuery("select * from dbproject.property where property_id = " + propertyChoice);
	                    	
	                    	while (propertyInfo.next()) {
	                    		String type = propertyInfo.getString("property_type");
	                    		String price = propertyInfo.getString("price");
	                    		String fees = propertyInfo.getString("fees");
	                    		String max_occupancy = propertyInfo.getString("max_occupancy");
	                    		String min_occupancy = propertyInfo.getString("min_occupancy");
	                    		String amenities = propertyInfo.getString("amenities");
	                    		String owner = propertyInfo.getString("owner_id");
	                    		String dates_booked = propertyInfo.getString("dates_booked");
	                    		String address = propertyInfo.getString("address");
	                    		System.out.println("This property is a "+ type+ " that costs " + price + " dollars per night.");
	                    		System.out.println("It is located at "+address);
	                    		System.out.println("This "+type+ " has "+ fees + " ($cleaning fees, $transaction fees, $restocking fees)");
	                    		System.out.println("This " + type+ " has " + amenities + "(bathrooms, bedrooms, beds, kitchens, internet access?)");
	                    		System.out.println("This " + type+ " has a minimum occupancy of "+min_occupancy+ " and a max occupancy of "+max_occupancy);
	                    		System.out.println("The owner of this property is owner #"+owner);
	                    		System.out.println("This " + type+ " is booked during " +dates_booked);
	                    		
	                    	}
	                    	
	                        System.out.println("Would you like to continue? Press (0) to exit or (1) to continue.");
	                        int exitChoice = input.nextInt();
	                        
	                        if (exitChoice == 0) {
	                     	  x = false; 
	                        }
	                        
	                        else {
	                        continue;
	                        }
	                    }
	                    if (userChoice == 4) {
	                    	
	                    	System.out.println("Enter the ID of the adventure you'd like to view.");
	                    	int adventureChoice = input.nextInt();
	                    	ResultSet adventureInfo = userInfo.executeQuery("select * from dbproject.adventures where adventure_id = " + adventureChoice);
	                    	
	                    	while (adventureInfo.next()) {
	                    		String type = adventureInfo.getString("adventure_type");
	                    		String price = adventureInfo.getString("price");
	                    		String days = adventureInfo.getString("days_long");
	                    		String transport = adventureInfo.getString("transportation");
	                    		String meals = adventureInfo.getString("meals");
	                    		String owner = adventureInfo.getString("owner_id");
	                    		String language = adventureInfo.getString("language");
	                    		String address = adventureInfo.getString("location");
	                    		String group = adventureInfo.getString("group_size");
	                    		System.out.println("This adventure is a "+ type+ " that costs " + price + " dollars.");
	                    		System.out.println("It is located at "+address+ " and goes on for "+days+" days.");
	                    		System.out.println("Transportation is included? " + transport);
	                    		System.out.println("This adventures comes with "+meals+" meals.");
	                    		System.out.println("The minimum group size is" +group);
	                    		System.out.println("This adventure is offered in "+language);
	                    		System.out.println("The owner of this adventure is owner #"+owner);

	                    		
	                    	}
	                    	
	                        System.out.println("Would you like to continue? Press (0) to exit or (1) to continue.");
	                        int exitChoice = input.nextInt();
	                        
	                        if (exitChoice == 0) {
	                     	  x = false; 
	                        }
	                        
	                        else {
	                        continue;
	                        }
	                    }	
	                    
	                    if (userChoice == 5) {
	                    	
	                    	System.out.println("Enter the ID of the experience you'd like to view.");
	                    	int experienceChoice = input.nextInt();
	                    	ResultSet experienceInfo = userInfo.executeQuery("select * from dbproject.experience where experience_id = " + experienceChoice);
	                    	
	                    	while (experienceInfo.next()) {
	                    		String type = experienceInfo.getString("experience_type");
	                    		String price = experienceInfo.getString("price");
	                    		String owner = experienceInfo.getString("owner_id");
	                    		String language = experienceInfo.getString("language");
	                    		String address = experienceInfo.getString("location");
	                    		System.out.println("This experience is a "+ type+ " that costs " + price + " dollars.");
	                    		System.out.println("It is located at "+address);
	                    		System.out.println("This experience is offered in "+language);
	                    		System.out.println("The owner of this experience is owner #"+owner);

	                    		
	                    	}
	                    	
	                        System.out.println("Would you like to continue? Press (0) to exit or (1) to continue.");
	                        int exitChoice = input.nextInt();
	                        
	                        if (exitChoice == 0) {
	                     	  x = false; 
	                        }
	                        
	                        else {
	                        continue;
	                        }
	                    }	
	                }


	            }


	        } catch (SQLException e) {
	            System.out.println("SQL error");
	            e.printStackTrace();
	        }
	    }
	}


