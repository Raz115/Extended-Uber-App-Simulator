//Raza Hussain Mirza
//501242038

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;
import java.util.List;
import java.util.Iterator;

/*
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager
{
  private Map<String, User> users;
  private ArrayList<User> userList;
  private ArrayList<Driver> drivers;

  private Queue<TMUberService>[] serviceRequests;

  public double totalRevenue; // Total revenues accumulated via rides and deliveries
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  //These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  public TMUberSystemManager()
  {
    users = new TreeMap<String, User>();
    userList=new ArrayList<>();
    drivers = new ArrayList<Driver>();

    for(User a:users.values())
    {
      userList.add(a);
    }

    serviceRequests = (LinkedList<TMUberService>[]) new LinkedList<?>[4]; 
    
    for (int i = 0; i < 4; i++)
    {
      serviceRequests[i] = new LinkedList<TMUberService>();
    }
  
    totalRevenue = 0;
  }

  // General string variable used to store an error message when something is invalid 
  // (e.g. user does not exist, invalid address etc.)  
  // The methods below will set this errMsg string and then return false

  // Given user account id, find user in list of users
  // Return null if not found
  public User getUser(String accountId)
  {
    for(User a: users.values())
    {
      if(a.getAccountId().equals(accountId)){
      return a;
    }
  }
  return null;
}
  // Check for duplicate user
  private boolean userExists(User user)
  {    
    //for each loop
    for(User a: users.values())
    {
      //if user = user
      if(a.equals(user))
      {
        //return true
        return true;
      }
    }
    //return false
    return false;
  }
  
 // Check for duplicate driver
 private boolean driverExists(Driver driver)
 { 
   for(int i = 0; i < drivers.size(); i++)
   {
      //if driver in drivers array list at index i equals given driver, return true
      if (drivers.get(i).equals(driver))
      {
        return true;
      }
   }
   //else return false
   return false;
 }
  
  // Given a user, check if user ride/delivery request already exists in service requests
  private boolean existingRequest(TMUberService req)
  {
    for(int i = 0; i < 4; i++)
   {
      for(TMUberService a:serviceRequests[i])
      {
        //if service in service requests array list at index i has a user that equals the given user, return true
        if (a.equals(req))
        {
          return true;
        }
      }  
   }
   //else return false
    return false;
  }

  // Calculate the cost of a delivery based on distance 
  private double getDeliveryCost(int distance)
  {
    return distance * DELIVERYRATE;
  }

  // Calculate the cost of a ride based on distance 
  private double getRideCost(int distance)
  {
    return distance * RIDERATE;
  }


  // Print Information (printInfo()) about all registered users in the system
  public void listAllUsers()
  {
    System.out.println();
    
    for (int i = 0; i < userList.size(); i++)
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      userList.get(i).printInfo();
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers()
  {
    System.out.println();
    
    for (int i = 0; i < drivers.size(); i++)
    {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      drivers.get(i).printInfo();
      System.out.println();
      System.out.println();
    }
  }

  //Helper method to get driver using driver id
  public Driver getDriver(String driverId){
    for(Driver d:drivers){
      if(d.getId().equals(driverId)){
        return d;
      }
    }
    return null;
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests()
  {
    System.out.println();
    
    for (int i = 0; i < 4; i++)
    {
      int index=0;

      System.out.println("ZONE "+i);
      System.out.println("======");
      System.out.println();

      for(TMUberService a: serviceRequests[i])
      {
        index++;
        System.out.printf("%-1s. %s", index,"-".repeat(50));
        a.printInfo();
        System.out.println();
        System.out.println();
      }
    }
  }

  // Add a new user to the system
  public void registerNewUser(String name, String address, double wallet)
  { 
    //Check for input errors
    if(name == null || name == "")
    {
      throw new InvalidUserNameException("Invalid User Name");
    }
    else if(!CityMap.validAddress(address))
    {
      throw new InvalidAddressException("Invalid Adddress");
    }
    else if(wallet < 0)
    {
      throw new InvalidWalletException("Invalid Money in Wallet");
    }
    
    else 
    {
      String id=TMUberRegistered.generateUserAccountId(userList);
      
      User tempUser = new User(id ,name, address, wallet);
      if(userExists(tempUser))
      {
        throw new UserAlreadyExistsException("User Already Exists in System");
      }
      else
      { 
        //add user to users array list
        users.put(id, tempUser);
        userList.add(tempUser);
      }
    }
  }

  // Add a new driver to the system
  public void registerNewDriver(String name, String carModel, String carLicencePlate, String address)
  {
    //Check for input errors
    if(name == null || name == "")
    {
      throw new InvalidDriverNameException("Invalid Driver Name");
    }
    else if(carModel == null || carModel == "")
    {
      throw new InvalidCarException("Invalid Car Model");
    }
    else if(carLicencePlate == null || carLicencePlate == "")
    {
      throw new InvalidLicenseException("Invalid Car Licence Plate");
      
    }
    else if(address == null || address == "" || !CityMap.validAddress(address))
    {
      throw new InvalidAddressException("Invalid Address");
    }
    else 
    {
      //create driver and check if already exists
      String id=TMUberRegistered.generateDriverId(drivers);
      Driver tempDriver = new Driver(id ,name, carModel, carLicencePlate, address);
      if(driverExists(tempDriver))
      {
        throw new DriverAlreadyExistsException("Driver Already Exists in System");
      }
      else
      { 
        //add driver to drivers array list
        drivers.add(tempDriver);
      }
    }
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public void requestRide(String accountId, String from, String to)
  {
    // Check for valid parameters
	  // Use the account id to find the user object in the list of users
    // Get the distance for this ride
    // Note: distance must be > 1 city block!
    // Find an available driver
    // Create the TMUberRide object
    // Check if existing ride request for this user - only one ride request per user at a time!
    // Change driver status
    // Add the ride request to the list of requests
    // Increment the number of rides for this user
    
    //Check for errors in input
    if(getUser(accountId) == null)
    {
      throw new InvalidUserAccountIdException("User Account Not Found");
    }
    else if(!(CityMap.validAddress(from)) || !(CityMap.validAddress(to)))
    {
      throw new InvalidAddressException("Invalid Address");
    }
    else if(CityMap.getDistance(from, to) < 1)
    {
      throw new InvalidTravelDistanceException("Insufficient Travel Distance");
    }
    else if(getRideCost(CityMap.getDistance(from, to)) > getUser(accountId).getWallet())
    {
      throw new InsufficientFundesException("Insufficient Funds");
    }
    else
    {
      //create TMUberRide using given info adn check if ride already exists
      TMUberRide temp = new TMUberRide(from, to, getUser(accountId), CityMap.getDistance(from, to), getRideCost(CityMap.getDistance(from, to))); 
      if(existingRequest(temp) )
      {
        throw new UserAlreadyHasRideException("User Already Has Ride Request");
      }
      else
      {   
        //Add ride to service request array list
        serviceRequests[CityMap.getCityZone(from)].add(temp);
        
        //Add ride to user
        getUser(accountId).addRide();
      }
    }
  }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId)
  {
    // See the comments above and use them as a guide
    // For deliveries, an existing delivery has the same user, restaurant and food order id
    // Increment the number of deliveries the user has had

    //Check for errors in input
    if(getUser(accountId) == null)
    {
      throw new InvalidUserAccountIdException("User Account Not Found");
    }
    else if(!(CityMap.validAddress(from)) || !(CityMap.validAddress(to)))
    {
      throw new InvalidAddressException("Invalid Address");
    }
    else if(CityMap.getDistance(from, to) < 1)
    {
      throw new InsufficientTravelDistException ("Insufficient Travel Distance");
    }
    else if(getDeliveryCost(CityMap.getDistance(from, to)) > getUser(accountId).getWallet())
    {
      throw new InsuffiecientFundsException ("Insufficient Funds");
    }
    else
    {
      //create TMUberDilivery using given info adn check if ride already exists
      TMUberDelivery temp = new TMUberDelivery(from, to, getUser(accountId), CityMap.getDistance(from, to), getDeliveryCost(CityMap.getDistance(from, to)), restaurant, foodOrderId); 
      if(existingRequest(temp) )
      {
        throw new DuplicateDiliveryException("User Already Has Delivery Request at Restaurant with this Food Order");
      }
      else
      {
        //Add TMUberDilivery to service requests array list 
        serviceRequests[CityMap.getCityZone(from)].add(temp);

        //Add dilivery to user
        getUser(accountId).addDelivery();
      }
    }
  }


  // Cancel an existing service request. 
  // parameter int request is the index in the serviceRequests array list
  public void cancelServiceRequest(int zoneNum, int request)
  {
    // Check if valid request #
    // Remove request from list
    // Also decrement number of rides or number of deliveries for this user
    // since this ride/delivery wasn't completed
    
    //Check for valid input
    if(zoneNum < 0 || zoneNum > 3)
    {
      throw new InvalidAddressException("Invalid Zone");
    }
    else if(request > serviceRequests[zoneNum].size() || request <1)
    {
      throw new InvalidRequestNumException ("Invalid Request #");
    }
    else
    {
      //Copy the queue till the element being remove 
      Queue <TMUberService> tempServices = serviceRequests[zoneNum];
      Queue <TMUberService> temp = new LinkedList<>();
      Iterator <TMUberService> iter = tempServices.iterator();
      int count = 0;
      TMUberService x = null;

      while(iter.hasNext())
      {
        if(count != request-1)
        {
          temp.add(iter.next());
        } 
        else{
          x = iter.next();
        }
        count++;
      }

      //Get the user for the service u want to remove
      User tempUser = x.getUser();

      //If ride decrrement ride from user if dilivery decrement dilivery
      if(x.getServiceType().equals("RIDE"))
      {
        tempUser.decrementRide();
      } 
      else 
      {
        tempUser.decrementDilivery();
      } 
  
      //Remove from service requests
      serviceRequests[zoneNum] = tempServices;
    }
  }
  
  // Drop off a ride or a delivery. This completes a service.
  // parameter request is the index in the serviceRequests array list
  public boolean dropOff(String driverId)
  {
    // See above method for guidance
    // Get the cost for the service and add to total revenues
    // Pay the driver
    // Deduct driver fee from total revenues
    // Change driver status
    // Deduct cost of service from user
    
    
    // Drop off a ride or a delivery. This completes a service.
    // parameter request is the index in the serviceRequests array list
    
    //Check if input is valid
    Driver driver = getDriver(driverId);
    if(driver==null){
      throw new DriverNotFoundException("Invalid Driver ID");
    }
    TMUberService a= driver.getService();
    if(a==null){
      throw new DropoffException("This driver has no dropoff");
    }
    else 
    {
      //Get the user for the service u want to mark as done
      User tempUser = a.getUser();
      
      double costOfService = a.getCost();

      // Deduct cost of service from user
      tempUser.payForService(costOfService);  

      // Pay the driver
      driver.pay(costOfService * PAYRATE);

      // Update total revenue
      totalRevenue += costOfService-costOfService*PAYRATE;
      
      // Change driver status
      driver.setStatus(Driver.Status.AVAILABLE);
      driver.setAddress(a.getTo());
    }
    return true;
  }

  //Pick up method, checks if driver is available, then assigns service to driver
  public void pickUp(String driverId){
    Driver driver = getDriver(driverId);
    if(driver == null)
    {
      throw new DriverNotFoundException("Invalid Driver ID");
    }
    else if(driver.getStatus() == Driver.Status.DRIVING)
    {
      throw new AlreadyDriverException("Driver is already driving");
    }
    
    int zone=CityMap.getCityZone(driver.getAddress());
    
    if(serviceRequests[zone].size()==0)
    {
      throw new NoRequestFound("No Service Request in Zone "+zone);
    }
    else
    {
      TMUberService a=serviceRequests[zone].poll();
      String from=a.getFrom();
      driver.setService(a);
      driver.setAddress(from);
      driver.setStatus(Driver.Status.DRIVING);
    }
  }

  public void driveTo(String driverId,String address){
    Driver driver=getDriver(driverId);
    if(driver==null){
      throw new DriverNotFoundException("Invalid Driver ID");
    }
    else if(!CityMap.validAddress(address)){
      throw new InvalidAddressException("Invalid Address");
    }
    else if(driver.getStatus()==Driver.Status.DRIVING){
      throw new AlreadyDriverException("Driver is not available");
    }
    else{
      driver.setAddress(address);
    }
  }


  // Sort users by name
  // Then list all users
  public void sortByUserName()
  {       
    Collections.sort(userList, new NameComparator());
    listAllUsers();
  }

  // Helper class for method sortByUserName
  private class NameComparator implements Comparator<User>
  {
    //Compare name of user a to user b and return -1,0,1 depending on answer 
    public int compare(User a, User b)
    {
      return a.getName().compareTo(b.getName());
    }
  }

  // Sort users by number amount in wallet
  // Then list all users
  public void sortByWallet()
  {    
    Collections.sort(userList, new UserWalletComparator());
    listAllUsers();
  }
  // Helper class for use by sortByWallet
  private class UserWalletComparator implements Comparator<User>
  {
    //I added this
    public int compare(User a, User b)
    {
      //Compare wallet of user a to user b and return -1,0,1 depending on answer 
    	if (a.getWallet() < b.getWallet()) return -1;
		  if (a.getWallet() > b.getWallet()) return  1;
		  return 0;
    }
  }

  // Sort trips (rides or deliveries) by distance
  // Then list all current service requests
  public void sortByDistance() {
    ArrayList<TMUberService>[] temp = new ArrayList[4];

    for (int zone = 0; zone < 4; zone++) 
    {
        temp[zone] = new ArrayList<>();
        for (TMUberService service : serviceRequests[zone]) 
        {
          temp[zone].add(service);
        }
    }

    for (ArrayList<TMUberService> arrayList : temp) 
    {
        Collections.sort(arrayList);
    }

    for (int zone = 0; zone < 4; zone++) 
    {
      serviceRequests[zone] = new LinkedList<>();
      for (TMUberService service : temp[zone]) 
      {
        serviceRequests[zone].add(service);
      }
    }

    listAllServiceRequests();
  }

  public void setUsers(ArrayList<User> y)
  {
    for(User x: y)
    {
      users.put(x.getAccountId(), x);
      userList.add(x);
    }
  }
  public void setDrivers(ArrayList<Driver> driversx)
  {
    for(Driver a: driversx)
    {
      drivers.add(a);
    }
  }
}
class InvalidUserNameException extends RuntimeException{
  public InvalidUserNameException(){}
  public InvalidUserNameException(String message)
  {
    super(message);
  }
}
class InvalidWalletException extends RuntimeException{
  public InvalidWalletException(){}
  public InvalidWalletException(String message)
  {
    super(message);
  }
}
class UserAlreadyExistsException extends RuntimeException{
  public UserAlreadyExistsException(){}
  public UserAlreadyExistsException(String message)
  {
    super(message);
  }
}
class InvalidDriverNameException extends RuntimeException{
  public InvalidDriverNameException(){}
  public InvalidDriverNameException(String message)
  {
    super(message);
  }
}
class InvalidCarException extends RuntimeException{
  public InvalidCarException(){}
  public InvalidCarException(String message)
  {
    super(message);
  }
}
class InvalidLicenseException extends RuntimeException{
  public InvalidLicenseException(){}
  public InvalidLicenseException(String message)
  {
    super(message);
  }
}
class InvalidAddressException extends RuntimeException{
  public InvalidAddressException(){}
  public InvalidAddressException(String message)
  {
    super(message);
  }
}
class DriverAlreadyExistsException extends RuntimeException{
  public DriverAlreadyExistsException(){}
  public DriverAlreadyExistsException(String message)
  {
    super(message);
  }
}
class InvalidUserAccountIdException extends RuntimeException{
  public InvalidUserAccountIdException(){}
  public InvalidUserAccountIdException(String message)
  {
    super(message);
  }
}
class InvalidTravelDistanceException extends RuntimeException{
  public InvalidTravelDistanceException(){}
  public InvalidTravelDistanceException(String message)
  {
    super(message);
  }
}
class InsufficientFundesException extends RuntimeException{
  public InsufficientFundesException(){}
  public InsufficientFundesException(String message)
  {
    super(message);
  }
}
class NoDriverAvailableException extends RuntimeException{
  public NoDriverAvailableException(){}
  public NoDriverAvailableException(String message)
  {
    super(message);
  }
}
class UserAlreadyHasRideException extends RuntimeException{
  public UserAlreadyHasRideException(){}
  public UserAlreadyHasRideException(String message)
  {
    super(message);
  }
}
class InsufficientTravelDistException extends RuntimeException{
  public InsufficientTravelDistException(){}
  public InsufficientTravelDistException(String message)
  {
    super(message);
  }
}
class InsuffiecientFundsException extends RuntimeException{
  public InsuffiecientFundsException(){}
  public InsuffiecientFundsException(String message)
  {
    super(message);
  }
}
class DuplicateDiliveryException extends RuntimeException{
  public DuplicateDiliveryException(){}
  public DuplicateDiliveryException(String message)
  {
    super(message);
  }
}
class InvalidRequestNumException extends RuntimeException{
  public InvalidRequestNumException(){}
  public InvalidRequestNumException(String message)
  {
    super(message);
  }
}
class DriverNotFoundException extends RuntimeException{
  public DriverNotFoundException(){}
  public DriverNotFoundException(String message)
  {
    super(message);
  }
}
class DropoffException extends RuntimeException{
  public DropoffException(){}
  public DropoffException(String message)
  {
    super(message);
  }
}
class NoRequestFound extends RuntimeException{
  public NoRequestFound(){}
  public NoRequestFound(String message)
  {
    super(message);
  }
}
class AlreadyDriverException extends RuntimeException{
  public AlreadyDriverException(){}
  public AlreadyDriverException(String message)
  {
    super(message);
  }
}