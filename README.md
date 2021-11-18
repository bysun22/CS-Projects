# Project 2

Project 2: Priority Queue and Hash Table

The first two parts are the implementation of a priority queue and the hash table. The next two are to use them together to optimize the general operations on both the data structures in order to model an order-taking system at a restaurant.

Part 0: CustomerOrder.java
This class models an order placed by a customer at a restaurant. Our restaurant only serves one item, and no two customers have the same name. 

Class: CustomerOrder.java

Class Variables

Description

private String name 

Stores the name of the customer 

private int orderPlacedTime

Stores the time at which the order was placed.

private int orderDeliveryTime

Stores the time at which the order is to be delivered.

private int posInQueue

Indicates the position of the customer in the priority queue

Function/ Methods

Description

public CustomerOrder(String name, int orderPlacedTime, int orderDeliverTime)

Constructor to initialize the class object

public String toString()

Returns the object in the String form

public int compareTo(CustomerOrder other)

Used to compare objects.

Returns 1 if the current order has an earlier delivery time. Returns -1 if the other order has an earlier delivery time. If the delivery times are the same, returns 1 if the current order was placed before, else returns -1. No two orders can have the same order time. 

public boolean equals(Object 0)

Returns true if the two objects are equal else returns false.

Getters and Setters:

getName()

getOrderDeliveryTime()

getOrderPLacedTime()

getPosInQueue()

setOrderDeliveryTime(int orderDeliverTime)

setPosInQueue(int posInQueue)

Getters and setters for the class variables. Work as the names suggest.

Part 1: CustomerOrderQueue.java
Array-based max-heap priority queue to process the customers. Customers with an earlier delivery time will be placed higher on the priority queue. That is, the CustomerOrder with the earliest delivery time among all the CustomerOrders will be at index 0 in the array-based max-heap. 

Function/ Methods

Description

public CustomerOrderQueue(int capacity)

Create a new priority queue with the given capacity

public int insert(CustomerOrder c)

Insert the CustomerOrder into the priority queue. Return the final index at which the CustomerOrder was inserted. Return -1 if the priority queue was full.

public CustomerOrder delMax()

Remove and return the CustomerOrder with the highest priority. Return null if the priority queue is empty.

public int size()

Returns the number of customers currently in the CustomerOrderQueue.

public boolean isEmpty()

Returns true if the priority queue is empty, else returns false

public CustomerOrder getMax()

Return but don’t remove the CustomerOrder with the maximum priority.

public customer[] getOrderList()

Return priority queue

Public int getNumOrders()

Return the number of orders in the queue

Part 2: CustomerOrderHash.java
A simple binary heap CQ has good runtime for both insert and delMax, but this alone is not ideal for the restaurant’s queue because sometimes customers who placed an order for a given deliveryTime might need to change the delivery time of their order. They might change the delivery time to an earlier time or for a later one. Likewise, some customers may get tired of waiting and walk out. This requires us to be able to update customers in the queue and remove specific customers from the queue. This means, though, that we need to first find the specific customer in the queue (based on the hash value), and CQs are not built for fast searching. The good news is that with the help of another data structure, we can do this in a reasonably fast time.

A basic hash table that will store the customers for quick searching. The key value is the hashcode, and you should implement a function that maps the name to a hash value (SHA-256 format), as well as modular hashing. At first, you will map the customer’s name to an SHA-256 string representation code. Then, you will map the SHA-256 string representation code to a hashcode using Java's hashCode function, followed by mapping the hashcode to the index of the table. You are required to use the MessageDigest class from java.security package to implement SHA256 hash.

Implement the hash table using separate chaining to handle collisions. However, since we know the capacity of the restaurant, we can set the array size to be that capacity, which will make our expected runtime for put and get and remove all O(1).

Use ArrayList objects to implement the chains. You should only initialize a new list when necessary. Otherwise, leave the array element null. i.e., the hashtable should be an array of ArrayLists.

CustomerOrderHash.java

Function/ Methods

Description

private int getNextPrime(int num)

Get the next prime number greater than or equal to num

private Boolean isPrime(int num)

Determines if a num > 3 is prime or not

public ArrayList<CustomerOrder>[] getArray() 

Returns the array which functions as the hash table 

The following methods must be implemented in CutomerOrderHash. 

Function/ Methods

Description

public CustomerOrderHash(int capacity)

Create a new CutomerOrderHash with the underlying array having the length as p where p is a prime number greater than or equal to the capacity passed in as the parameter.

public void put(CustomerOrder c) throws NoSuchAlgorithmException

put the Customer c into the table; if the Customer already exists in the table, do nothing

public CustomerOrder get(String name) throws NoSuchAlgorithmException

return the Customer with the given name from the table; if the Customer is not in the table, return null

public CustomerOrder remove(String name) throws NoSuchAlgorithmException

remove and return the CustomerOrder with the given name; if the CustomerOrder does not exist in the table, return null

public int size()

Returns the number of customers currently in the CustomerOrderHash.

Import Statements

import java.security.MessageDigest;

import java.security.NoSuchAlgorithmException

import java.util.ArrayList

Part 3: BetterCustomerOrderQueue.java
Now that you have a HashTable for the CustomerOrders, you can store pointers to each CustomerOrder both in the PQ and in the HT. This allows fast searching (by customer name), and once you retrieve the CustomerOrder object, you can access the customer’s position in the PQ and thereby access it quickly, allowing for more advanced operations in the PQ in a reasonably fast time. Make sure to keep track of the position of the CustomerOrder in the queue.

BettterCustomerOrderQueue.java

Function/ Methods

Description

public BetterCustomerOrderQueue(int capacity)

Create a CustomerOrderQueue and CustomerOrderHash with the given capacity

public int insert(CustomerOrder c) throws NoSuchAlgorithmException

Insert the CustomerOrder into both the CustomerOrderQueue and CustomerOrderHash. Return the final index at which the CustomerOrder was inserted. Return -1 if the priority queue was full or the CustomerOrder already existed in the order list.

public CustomerOrder delMax()throws NoSuchAlgorithmException

Remove and return the CustomerOrder with the highest priority. Return null if the priority queue is empty.

public int size()

Returns the number of customers currently in the CustomerOrderQueue.

public boolean isEmpty()

Returns true if the priority queue is empty, else returns false

public CustomerOrder getMax()

Return but don’t remove the CustomerOrder with the maximum priority.

public CustomerOrder get(String name) throws NoSuchAlgorithmException

return the CustomerOrder with the given name

public CustomerOrder remove(String name) throws NoSuchAlgorithmException

remove and return the CustomerOrder with the specified name from the queue; return null if the CustomerOrder isn't in the queue

public void update(String name, int newTime) throws NoSuchAlgorithmException

update the orderDeliveryTime of the Customer with the specified name to newTime

public CustomerOrder[] getOrderList()

Return the CustomerOrderQueue

Public int getNumOrders()

Return the number of orders in the queue

While removing a customerOrder set its posInQueue to -1.

Import Statements

import java.security.NoSuchAlgorithmException

Part 4: OrderSystemModel.java
The OrderSystemModel class models how customers are processed as they place orders. The OrderSystemModel is created with a given capacity indicating the maximum orders that the restaurant can have at a given time.

The following methods must be implemented in OrderSystemModel.java. The program must also keep track of the number of orders that are completed on time, the number of orders that got delayed, and the number of orders that were canceled.
  
A variable called time is also defined to keep track of the time.

OrderSystemModel.java

Function/ Methods

Description

public OrderSystemModel(int capacityThreshold)

Create a new OrderSystemModel with the capacity Threshold.

String process(String name, int orderTime, int deliveryTime) throws NoSuchAlgorithmException

Process a new CustomerOrder with a given name. Try adding the new CustomerOrder to the priority queue. If the priority queue is not full, add the new CustomerOrder and return their name. If the priority queue is full then do the following

if the new CustomerOrder’s orderDeliveryTime is less than that of the current earliest delivery time, we first complete the new order. Return null in this case.

Else complete the order with the earliest delivery time in the queue and add the new Order to the queue. Return the name of the customer whose order was completed in this case

public String completeNextOrder() throws NoSuchAlgorithmException

If the priority queue is empty return null else complete the highest priority order and return the name of the customer whose order was completed.

public String updateOrderTime(String name, int newDeliveryTime) throws NoSuchAlgorithmException

If no CustomerOrder exists for the specified name return null. 

If the newDeliveryTime is less than that of the current earliest delivery time, we first try to complete the order which is being updated. However,if the new deilveryTime is less than the current time we cancel the order. Return name in both these cases. 

Else Update the delivery time of the CustomerOrder with the specified name to newDeliveryTime and return null;

Public CustomerOrder cancelOrder(String name)

Cancel the order with the specified name. Increase the variable ordersCanceled accordingly. 

Note: To complete an order, calculate the delay time and update the variables ordersOnTime, totalDelayTime, and ordersDelayed accordingly. Completing each order takes 1 unit time, hence, increase the time variable by 1.

Import Statements

import java.security.NoSuchAlgorithmException
