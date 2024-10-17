//Raza Hussain Mirza
//501242038

/*
 * 
 * This class simulates a car driver in a simple uber app 
 * 
 * Everything has been done for you except the equals() method
 */
public class Driver
{
  //Variables for Driver
  private String id;
  private String name;
  private String carModel;
  private String licensePlate;
  private double wallet;
  private String type;
  
  //New Driver Variables 
  private TMUberService service;
  private String address;
  private int zone;

  //Stores Drivers Status
  public static enum Status {AVAILABLE, DRIVING};
  private Status status;

  //Constructor for driver
  public Driver(String id, String name, String carModel, String licensePlate, String address)
  {
    this.id = id;
    this.name = name;
    this.carModel = carModel;
    this.licensePlate = licensePlate;
    
    //Set Status to AVAILABLE
    this.status = Status.AVAILABLE;

    //Set wallet to 0 and type empty string
    this.wallet = 0;
    this.type = "";

    this.address = address;
    this.zone = CityMap.getCityZone(address);

  }

  // Print Information about a driver
  public void printInfo()
  {
    System.out.printf("Id: %-3s Name: %-15s Car Model: %-15s License Plate: %-10s Wallet: %2.2f", id, name, carModel, licensePlate, wallet);
    System.out.println();
    System.out.printf("Status: %-10s Address: %-20s Zone: %1d", status, address, zone);
  }
  
  // Getters and Setters
  public TMUberService getService()
  {
    return this.service;
  }
  public void setService(TMUberService x)
  {
    this.service = x;
  }
  public String getAddress()
  {
    return address;
  }
  public void setAddress(String address)
  {
    this.address = address;
  }
  public String getType()
  {
    return type;
  }
  public void setType(String type)
  {
    this.type = type;
  }
  public String getId()
  {
    return id;
  }
  public void setId(String id)
  {
    this.id = id;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public String getCarModel()
  {
    return carModel;
  }
  public void setCarModel(String carModel)
  {
    this.carModel = carModel;
  }
  public String getLicensePlate()
  {
    return licensePlate;
  }
  public void setLicensePlate(String licensePlate)
  {
    this.licensePlate = licensePlate;
  }
  public Status getStatus()
  {
    return status;
  }
  public void setStatus(Status status)
  {
    this.status = status;
  }
  public double getWallet()
  {
    return wallet;
  }
  public void setWallet(double wallet)
  {
    this.wallet = wallet;
  }

  /*
   * Two drivers are equal if they have the same name and license plates.
   * This method is overriding the inherited method in superclass Object
   * 
   * Fill in the code 
   */
  public boolean equals(Object other)
  {
    Driver tempDriver = (Driver) other;

    if (getName().equals(tempDriver.getName()) && getLicensePlate().equals(tempDriver.getLicensePlate()))
    {
      return true;
    }

    return false; 
  }
  
  // A driver earns a fee for every ride or delivery
  public void pay(double fee)
  {
    wallet += fee;
  }
}
