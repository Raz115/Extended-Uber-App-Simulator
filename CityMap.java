//Raza Hussain Mirza
//501242038

import java.util.Arrays;
import java.util.Scanner;

// The city consists of a grid of 9 X 9 City Blocks

// Streets are east-west (1st street to 9th street)
// Avenues are north-south (1st avenue to 9th avenue)

// Example 1 of Interpreting an address:  "34 4th Street"
// A valid address *always* has 3 parts.
// Part 1: Street/Avenue residence numbers are always 2 digits (e.g. 34).
// Part 2: Must be 'n'th or 1st or 2nd or 3rd (e.g. where n => 1...9)
// Part 3: Must be "Street" or "Avenue" (case insensitive)

// Use the first digit of the residence number (e.g. 3 of the number 34) to determine the avenue.
// For distance calculation you need to identify the the specific city block - in this example 
// it is city block (3, 4) (3rd avenue and 4th street)

// Example 2 of Interpreting an address:  "51 7th Avenue"
// Use the first digit of the residence number (i.e. 5 of the number 51) to determine street.
// For distance calculation you need to identify the the specific city block - 
// in this example it is city block (7, 5) (7th avenue and 5th street)
//
// Distance in city blocks between (3, 4) and (7, 5) is then == 5 city blocks
// i.e. (7 - 3) + (5 - 4) 

public class CityMap
{
  // Checks for string consisting of all digits
  // An easier solution would use String method matches()
  private static boolean allDigits(String s)
  {
    for (int i = 0; i < s.length(); i++)
      if (!Character.isDigit(s.charAt(i)))
        return false;
    return  true;
  }

  // Get all parts of address string
  // An easier solution would use String method split()
  // Other solutions are possible - you may replace this code if you wish
  private static String[] getParts(String address)
  {
    String parts[] = new String[3];
    
    if (address == null || address.length() == 0)
    {
      parts = new String[0];
      return parts;
    }
    int numParts = 0;
    Scanner sc = new Scanner(address);
    while (sc.hasNext())
    {
      if (numParts >= 3)
        parts = Arrays.copyOf(parts, parts.length+1);

      parts[numParts] = sc.next();
      numParts++;
    }
    if (numParts == 1)
      parts = Arrays.copyOf(parts, 1);
    else if (numParts == 2)
      parts = Arrays.copyOf(parts, 2);
    return parts;
  }

  // Checks for a valid address
  public static boolean validAddress(String address)
  {
    // Fill in the code
    // Make use of the helper methods above if you wish
    // There are quite a few error conditions to check for 
    // e.g. number of parts != 3

    //Stores each parts of address in seperate index
    String [] tempArray = getParts(address);
    
    //Stores all strigns for 2nd part of address used to ensrue 'th','st' and 'nd' is used
    String [] order = {"1st", "2nd", "3rd", "4th", "5th", "6th", "7th", "8th", "9th"};
    
    //Stores if index1 is corret
    boolean isIndex1Correct = false;

    //Check if temp array has 3 parts
    if(tempArray.length == 3)
    {
      //Check first part of address
      if(allDigits(tempArray[0]) && tempArray[0].length() == 2)
      {
        //Check second part of address
        if(tempArray[1].length() == 3)
        {
          for(String x:order)
          {
            if(tempArray[1].equals(x))
            {
              isIndex1Correct = true;
            }
            else
            {
              continue;
            } 
          }
          //if second part is correct
          if(isIndex1Correct == true)
          {
            //check 3rd part of address
            if(tempArray[2].equalsIgnoreCase(("street")) || tempArray[2].equalsIgnoreCase(("avenue")) )
            {
              return true;
            }  
          }
        }
      }
    }
      return false;
  }

  // Computes the city block coordinates from an address string
  // returns an int array of size 2. e.g. [3, 4] 
  // where 3 is the avenue and 4 the street
  // See comments at the top for a more detailed explanation
  public static int[] getCityBlock(String address)
  {
    int[] block = {-1, -1};

    // Fill in the code
    
    //Check if address is valid
    if(validAddress(address))
    {
      //Split address into string aaray bassed of spaces
      String [] addressArr = address.split(" ");
      
      //if address is in street notation set index 0 to first part and 1 to second part of address
      if(addressArr[2].equalsIgnoreCase("street"))
      {
        block[0] = Integer.parseInt(addressArr[0].substring(0,1));
        block[1] =  Integer.parseInt(addressArr[1].substring(0,1)); 
      }
      
      //if address is in avenue notation set index 1 to first part and 0 to second part of address
      else
      {
        block[1] = Integer.parseInt(addressArr[0].substring(0,1));
        block[0] =  Integer.parseInt(addressArr[1].substring(0,1));
      }
    }
    return block;
  }
  
  // Calculates the distance in city blocks between the 'from' address and 'to' address
  // Hint: be careful not to generate negative distances

  // This skeleton version generates a random distance
  // If you do not want to attempt this method, you may use this default code
  public static int getDistance(String from, String to)
  { 
    //Initalize distance
    int dist = 0; 

    //find dstance on x axis and y axis then add the two values
    dist = Math.abs(getCityBlock(from)[0] - getCityBlock(to)[0]);
    dist += Math.abs(getCityBlock(from)[1] - getCityBlock(to)[1]);

    return dist;
    
  }
  //Returns corresonding zone bassed off of given address
  public static int getCityZone(String address)
  {
    //place address in int array where the first index is the street address and the second is the avenue
    int[] addressArray = getCityBlock(address);

    //return corresponding zone number
    if(addressArray[0] >=6 && addressArray[0] <=9)
    {
     if(addressArray[1]>=1 && addressArray[1]<=5)
     {
      return 2;
     } 
     else{
      return 1;
     }
    }
    else if (addressArray[0] >=1 && addressArray[0] <=5)
    {
     if(addressArray[1]>=1 && addressArray[1]<=5)
     {
      return 3;
     } 
     else{
      return 0;
     }
    }
    
    //return -1 if invallid
    else{
      return -1;
    }
    
  }
}
