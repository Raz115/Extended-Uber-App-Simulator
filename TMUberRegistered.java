import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TMUberRegistered
{
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountID = 900;
    private static int firstDriverId = 700;

    // Generate a new user account id
    public static String generateUserAccountId(ArrayList<User> current)
    {
        return "" + firstUserAccountID + current.size();
    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current)
    {
        return "" + firstDriverId + current.size();
    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    // The test scripts and test outputs included with the skeleton code use these
    // users and drivers below. You may want to work with these to test your code (i.e. check your output with the
    // sample output provided). 
    public static ArrayList<User> loadPreregisteredUsers(String fileName) throws IOException
    {
        ArrayList<User> users = new ArrayList<>();

        File inputFile = new File(fileName);
        Scanner in = new Scanner(inputFile);

        while(in.hasNextLine())
        {
            String name  = in.nextLine();
            String address = in.nextLine();   
            double wallet = Double.parseDouble(in.nextLine());

            users.add(new User(generateUserAccountId(users), name, address, wallet));
        }
        in.close();
        return users;
    }

    public static ArrayList<Driver> loadPreregisteredDrivers(String fileName) throws IOException
    {
        ArrayList<Driver> drivers= new ArrayList<>();

        File inputFile = new File(fileName);
        Scanner in = new Scanner(inputFile);

        while(in.hasNextLine())
        {
            String name  = in.nextLine();
            String carModle  = in.nextLine();   
            String license = in.nextLine();
            String address = in.nextLine();
            
            drivers.add(new Driver(generateDriverId(drivers), name, carModle, license, address));
        }
        
        in.close();
        return drivers;
    }
}

