# Extended-Uber-App-Simulator

Description:
- This Java project extends the original TMUber System (from Assignment 1), simulating a ride-sharing platform similar to Uber. The extended features introduce new functionalities, including service request queues, zone-based location management, and enhanced file I/O with exception handling.

Features:
- CityMap Enhancements:
    - The city is now divided into four zones.
    - A static method getCityZone(String address) returns the zone based on a given address or -1 if the address is invalid.
- New Commands in TMUberUI:
    - PICKUP: Assigns a service request to a driver.
    - LOADUSERS and LOADDRIVERS: Load user and driver data from files.
    - DRIVETO: Allows drivers to move to a new address.
- Driver Class Modifications:
    - Drivers now track their address and service information.
    - Zone management for drivers is based on their current address.
- Service Request Queues:
    - Four queues (one for each zone) store service requests.
    - Drivers pick up requests based on their zone.
- File I/O and Exception Handling:
    - User and driver data are loaded from text files.
    - Customized exceptions replace error messages for better error handling.
- Maps for User Management:
    - The system now uses a Map<String, User> to store users instead of an array list.

New Methods & Modifications
- TMUberSystemManager:
    - Methods to manage user and driver data.
    - Support for pickup and dropoff actions.
    - Exception handling to replace error messages.

- TMUberUI:
  - Enhanced user interface with new commands for loading data and managing drivers.

General Information:
- Run the program using java TMUberUI.
- Sample test scripts have been provided
- Drivers and users can be loaded in using the "users.txt" & "drivers.txt"
