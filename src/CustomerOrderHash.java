/**
 * @author Brian Sun
 * @username bysun
 * @sources https://en.wikipedia.org/wiki/SHA-2
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class CustomerOrderHash {
    private ArrayList[] table;
    private int numOrders;
    private int tableCapacity;

    /**
     *
     * Initilalize a new CustomerOrder array with the argument passed.
     *
     */
    public CustomerOrderHash(int capacity) {
        this.table = new ArrayList[getNextPrime(capacity)];
        this.numOrders = 0;
        this.tableCapacity = capacity;
    }

    /**
     *
     * @return return the CustomerOrder with the given name
     * @return return null if the CustomerOrder is not in the table
     *
     */
    public CustomerOrder get(String name) throws NoSuchAlgorithmException {
        int hashCode = getIndex(name);
        if(this.table[hashCode] == null){
            return null;
        } else {
            for (int i = 0; i < table[hashCode].size(); i++) {
                //Checks if the name in the arraylist is equal to the name provided
                if(((CustomerOrder)this.table[hashCode].get(i)).getName().equals(name)){
                    return (CustomerOrder)this.table[hashCode].get(i);
                }
            }
        }
        return null;
    }

    private int getIndex(String name){
        int hashCode = convertToSHA(name).hashCode();
        hashCode = hashCode % this.table.length;
        if(hashCode % this.table.length < 0){
            hashCode = hashCode + this.table.length;
        }
        return hashCode;
    }

    /**
     *
     * @return put CustomerOrder c into the table
     *
     */
    public void put(CustomerOrder c) throws NoSuchAlgorithmException {
        int hashCode = getIndex(c.getName());

        //Add to Array
        if(this.table[hashCode] == null){
            ArrayList<CustomerOrder> list = new ArrayList<>();
            list.add(c);
            this.table[hashCode] = list;
            this.numOrders++;
        } else {
            for (int i = 0; i < this.table[hashCode].size(); i++) {
                //checks if the object is in the arraylist at that hashcode index
                if(this.table[hashCode].get(i).equals(c)){
                    return;
                }
            }
            //Add to arraylist if it is not in the arraylist at hashcode index
            this.numOrders++;
            this.table[hashCode].add(c);
        }


    }

    private String convertToSHA(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes("UTF-8"));
            return toHexString(hash);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String toHexString(byte[] hash)
    {
        //convert to bytes
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }

        while (hexString.length() < 32) {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    /**
     *
     * @return remove and return the CustomerOrder with the given name;
     * @return return null if CustomerOrder doesn't exist
     *
     */
    public CustomerOrder remove(String name) throws NoSuchAlgorithmException {
        int hashCode = getIndex(name);
        if(this.table[hashCode] == null){
            return null;
        } else {
            for (int i = 0; i < table[hashCode].size(); i++) {
                //Checks if the name in the arraylist is equal to the name provided
                if(((CustomerOrder)this.table[hashCode].get(i)).getName().equals(name)){
                    CustomerOrder removed = (CustomerOrder)this.table[hashCode].get(i);
                    this.table[hashCode].remove(i);
                    this.numOrders--;
                    return removed;
                }
            }
        }
        return null;
    }

    /**
     *
     * @return return the number of Customers in the table
     *
     */
    public int size() {
        return this.numOrders;
    }

    //get the next prime number p >= num
    private int getNextPrime(int num) {
        if (num == 2 || num == 3)
            return num;

        int rem = num % 6;

        switch (rem) {
            case 0:
            case 4:
                num++;
                break;
            case 2:
                num += 3;
                break;
            case 3:
                num += 2;
                break;
        }

        while (!isPrime(num)) {
            if (num % 6 == 5) {
                num += 2;
            } else {
                num += 4;
            }
        }

        return num;
    }

    //determines if a number > 3 is prime
    private boolean isPrime(int num) {
        if (num % 2 == 0) {
            return false;
        }

        int x = 3;

        for (int i = x; i < num; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<CustomerOrder>[] getArray() {
        return this.table;
    }

}
