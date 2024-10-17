//Raza Hussain Mirza
//501242038

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.FileNotFoundException;

// Simulation of a Simple Command-line based Uber App 

// This system supports "ride sharing" service and a delivery service

public class TMUberUI
{
  public static void main(String[] args)
  {
    // Create the System Manager - the main system code is in here 

    TMUberSystemManager tmuber = new TMUberSystemManager();
    
    Scanner scanner = new Scanner(System.in);
    System.out.print(">");

    // Process keyboard actions
    while (scanner.hasNextLine())
    {
      String action = scanner.nextLine();

      if (action == null || action.equals("")) 
      {
        System.out.print("\n>");
        continue;
      }
      // Quit the App
      else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
        return;
      // Print all the registered drivers
      else if (action.equalsIgnoreCase("DRIVERS"))  // List all drivers
      {
        tmuber.listAllDrivers(); 
      }
      // Print all the registered users
      else if (action.equalsIgnoreCase("USERS"))  // List all users
      {
        tmuber.listAllUsers(); 
      }
      // Print all current ride requests or delivery requests
      else if (action.equalsIgnoreCase("REQUESTS"))  // List all requests
      {
        tmuber.listAllServiceRequests(); 
      }
      // Register a new driver
      else if (action.equalsIgnoreCase("REGDRIVER")) 
      {
        String name = "";
        System.out.print("Name: ");
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine();
        }
        String carModel = "";
        System.out.print("Car Model: ");
        if (scanner.hasNextLine())
        {
          carModel = scanner.nextLine();
        }
        String license = "";
        System.out.print("Car License: ");
        if (scanner.hasNextLine())
        {
          license = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }

        try
        {
          tmuber.registerNewDriver(name, carModel, license, address);
          System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s Address %-20s", name, carModel, license, address);
        }
        catch(RuntimeException e)
        {
          System.out.println(e.getMessage());
        }
      }
      // Register a new user
      else if (action.equalsIgnoreCase("REGUSER")) 
      {
        String name = "";
        System.out.print("Name: ");
        if (scanner.hasNextLine())
        {
          name = scanner.nextLine();
        }
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
        double wallet = 0.0;
        System.out.print("Wallet: ");
        if (scanner.hasNextDouble())
        {
          wallet = scanner.nextDouble();
          scanner.nextLine(); // consume nl!! Only needed when mixing strings and int/double
        }
        
        try
        {
          tmuber.registerNewUser(name, address, wallet);
          System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);
        }
        catch(RuntimeException e)
        {
          System.out.println(e.getMessage());
        }
      }
      // Request a ride
      else if (action.equalsIgnoreCase("REQRIDE")) 
      {
        // Get the following information from the user (on separate lines)
        // Then use the TMUberSystemManager requestRide() method properly to make a ride request
        // "User Account Id: "      (string)
        // "From Address: "         (string)
        // "To Address: "           (string)

        //Get input for ride
        System.out.print("User Account Id: ");
        String accountId = scanner.nextLine();
        System.out.print("From Address: ");
        String from = scanner.nextLine();
        System.out.print("To Address: ");
        String to = scanner.nextLine();

        //check if input is valid
        try
        {
          tmuber.requestRide(accountId, from, to);
          String name = tmuber.getUser(accountId).getName();
          System.out.println();
          System.out.printf("RIDE for: %-9s From: %-15s To: %-15s", name, from, to);
        }
        catch(RuntimeException e)
        {
          System.out.println(e.getMessage());
        }
      }
      // Request a food delivery
      else if (action.equalsIgnoreCase("REQDLVY")) 
      {
        // Get the following information from the user (on separate lines)
        // Then use the TMUberSystemManager requestDelivery() method properly to make a ride request
        // "User Account Id: "      (string)
        // "From Address: "         (string)
        // "To Address: "           (string)
        // "Restaurant: "           (string)
        // "Food Order #: "         (string)
        
        //Get input
        System.out.print("User Account Id: ");
        String accountId = scanner.nextLine();
        System.out.print("From Address: ");
        String from = scanner.nextLine();
        System.out.print("To Address: ");
        String to = scanner.nextLine();
        System.out.print("Restaurant: ");
        String restaurant = scanner.nextLine();
        System.out.print("Food Order #: ");
        String orderNum = scanner.nextLine();

        //Check if info is valid 
        try
        {
          // if the request is valid, the request is accepted and request is printed
          tmuber.requestDelivery(accountId, from, to, restaurant, orderNum);
          String name=tmuber.getUser(accountId).getName();
          System.out.println();
          System.out.printf("DELIVERY for: %-9s From: %-15s To: %-15s", name, from, to);
        }
        catch(RuntimeException e)
        {
          System.out.println(e.getMessage());
        }
      }
      // Sort users by name
      else if (action.equalsIgnoreCase("SORTBYNAME")) 
      {
        tmuber.sortByUserName();
      }
      // Sort users by number of ride they have had
      else if (action.equalsIgnoreCase("SORTBYWALLET")) 
      {
        tmuber.sortByWallet();
      }
      // Sort current service requests (ride or delivery) by distance
      else if (action.equalsIgnoreCase("SORTBYDIST")) 
      {
        tmuber.sortByDistance();
      }
      // Cancel a current service (ride or delivery) request
      else if (action.equalsIgnoreCase("CANCELREQ")) 
      {
        //Get an zone input
        int zoneNum = -1;        
        System.out.print("Zone #: ");
        if (scanner.hasNextInt())
        {
          zoneNum = scanner.nextInt();
        }
        
        //Get an request number input
        int request = -1;        
        System.out.print("Request #: ");

        if (scanner.hasNextInt())
        {
          request = scanner.nextInt();
          scanner.nextLine(); // consume nl character
        }
        
        //display error if cancelServiceRequest
        try
        {
          tmuber.cancelServiceRequest(zoneNum, request);
          System.out.println("Zone: "+zoneNum+" Service request #"+request+" cancelled");
        }
        catch(RuntimeException e)
        {
          System.out.println(e.getMessage());
        }
      }
      // Drop-off the user or the food delivery to the destination address
      else if (action.equalsIgnoreCase("DROPOFF")) 
      {
        //Get an int input
        String driverId = "";
        System.out.print("DriverId #: ");
        if (scanner.hasNextInt())
        {
          driverId = scanner.nextLine();
        }

        //display error if cancelServiceRequest
        try
        {
          tmuber.dropOff(driverId);
          System.out.println("Driver "+ driverId +" Dropping Off");
        }
        catch(RuntimeException e)
        {
          System.out.println(e.getMessage());
        } 
      }
      // Get the Current Total Revenues
      else if (action.equalsIgnoreCase("REVENUES")) 
      {
        System.out.printf("Total Revenue: %.2f", tmuber.totalRevenue);
      }
      // Unit Test of Valid City Address 
      else if (action.equalsIgnoreCase("ADDR")) 
      {
        //get input 
        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }

        //Print address
        System.out.print(address);
        
        //Check if address is valid 
        if (CityMap.validAddress(address))
          System.out.println("\nValid Address"); 
        else
          System.out.println("\nBad Address"); 
      }
      // Unit Test of CityMap Distance Method
      else if (action.equalsIgnoreCase("DIST")) 
      {
        //Get input
        String from = "";
        System.out.print("From: ");
        if (scanner.hasNextLine())
        {
          from = scanner.nextLine();
        }
        String to = "";
        System.out.print("To: ");
        if (scanner.hasNextLine())
        {
          to = scanner.nextLine();
        }

        //Print info address
        System.out.print("\nFrom: " + from + " To: " + to);
        System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");
      }
      else if (action.equalsIgnoreCase("PICKUP")) 
      {
        System.out.print("Driver ID: ");
        String id = "";
        int zone = -1;
        if(scanner.hasNextLine())
        {
        id=scanner.nextLine();
        }
        
        try
        {
          Driver driver = tmuber.getDriver(id);
          tmuber.pickUp(id);
          zone = CityMap.getCityZone(driver.getAddress());
          
          System.out.println("\nDriver "+id+"\nPicking Up in Zone "+zone);
        }
        catch(RuntimeException e)
        {
          System.out.println(e.getMessage());
        }  
      }
      else if (action.equalsIgnoreCase("LOADUSERS")) 
      {
        String fileName = "";
        System.out.print("User File: ");
        if (scanner.hasNextLine())
        {
          fileName = scanner.nextLine();
        }
                
        try
        {
          tmuber.setUsers(TMUberRegistered.loadPreregisteredUsers(fileName));
          System.out.println("Users Loaded");

        }
        catch(IOException e)
        {
          if(e instanceof FileNotFoundException)
          {
            System.out.println("Users File: "+ fileName +" Not Found");
          }
          else{
            System.out.println("Error");
            System.exit(1);
         }
        }
      }
      else if (action.equalsIgnoreCase("LOADDRIVERS")) 
      {
        String fileName = "";
        System.out.print("Drivers File: ");
        if (scanner.hasNextLine())
        {
          fileName = scanner.nextLine();
        }
        try{
          ArrayList<Driver> driverList=TMUberRegistered.loadPreregisteredDrivers(fileName);
          tmuber.setDrivers(driverList);
          System.out.println("Drivers Loaded"); 
        }
        catch(IOException e)
        {       
          if(e instanceof FileNotFoundException)
          {
            System.out.println("Drivers File: "+ fileName +" Not Found");
          }
          else{
            System.out.println("Error");
            System.exit(1);
          }
        }
      }
      else if (action.equalsIgnoreCase("DRIVETO")) 
      {
        String driverId = "";
        System.out.print("Driver Id: ");
        if (scanner.hasNextLine())
        {
          driverId = scanner.nextLine();
        }

        String address = "";
        System.out.print("Address: ");
        if (scanner.hasNextLine())
        {
          address = scanner.nextLine();
        }
       
        try
        {
          tmuber.driveTo(driverId, address);
          System.out.println("Driver " + driverId + " is now in Zone #: " + CityMap.getCityZone(address));
        }
        catch(RuntimeException e)
        {
          System.out.println(e.getMessage());
        } 
      }
      System.out.print("\n>");
    }
  }
}

