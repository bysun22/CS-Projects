/**
 * @author Brian Sun,
 * @username bysun,
 * @sources
 */

import java.security.NoSuchAlgorithmException;


public class BetterCustomerOrderQueue {
    private CustomerOrder[] orderList;
    private CustomerOrderHash table;
    private int numOrders;

    /**
     *
     * Return the CustomerOrderQueue
     *
     */
    public CustomerOrder[] getOrderList() {
        return orderList;
    }

    /**
     *
     * Return the number of orders in the queue
     *
     */
    public int getNumOrders() {
        return numOrders;
    }


    /**
     *
     * Initialize a new CustomerOrderQueue and CustomerOrderHash
     *
     */
    public BetterCustomerOrderQueue(int capacity) {
        this.table = new CustomerOrderHash(capacity);
        this.orderList = new CustomerOrder[capacity];
        this.numOrders = 0;
    }

    /**
     *
     * @return return the index at which the customer was inserted;
     * return -1 if the Customer could not be inserted
     *
     */
    public int insert(CustomerOrder c) throws NoSuchAlgorithmException {
        if(size() >= this.orderList.length){
            return -1;
        }

        //insert queue
        this.numOrders++;
        this.orderList[size() - 1] = c;
        this.orderList[size() - 1].setPosInQueue(size() - 1);
        heapifyAdd(size() - 1);
        //////////////////////////

        //insert table
        this.table.put(c);

        return this.table.get(c.getName()).getPosInQueue();
    }

    private void heapifyAdd(int index){
        if (index != 0) {
            int parentIndex = (index - 1) / 2;
                if (this.orderList[index].compareTo(this.orderList[parentIndex]) == 1) {
                    CustomerOrder tmp = this.orderList[parentIndex];
                    this.orderList[parentIndex] = this.orderList[index];
                    this.orderList[parentIndex].setPosInQueue(parentIndex);
                    this.orderList[index] = tmp;
                    this.orderList[index].setPosInQueue(index);
                    heapifyAdd(parentIndex);
                }
        }
    }

    /**
     *
     * @return return the customer removed
     *
     */
    public CustomerOrder delMax() throws NoSuchAlgorithmException {
        if(isEmpty()){
            return null;
        }

        //remove from table
        this.table.remove(this.orderList[0].getName());

        //remove from queue
        this.orderList[0].setPosInQueue(-1);
        CustomerOrder highest = this.orderList[0];
        CustomerOrder lastEl = this.orderList[size() - 1];
        this.orderList[0] = lastEl;
        this.orderList[0].setPosInQueue(0);
        this.orderList[size()-1] = null;

        this.numOrders--;
        heapifyDel(0);

        return highest;
    }

    private void heapifyDel(int index){

        int largest = index;
        int l = 2 * index + 1;
        int r = 2 * index + 2;

        if (l < size() && this.orderList[l].compareTo(this.orderList[largest]) == 1) {
            largest = l;
        }

        if (r < size() && this.orderList[r].compareTo(this.orderList[largest]) == 1) {
            largest = r;
        }

        // If largest is not root
        if (largest != index) {
            CustomerOrder tmp = this.orderList[index];
            this.orderList[index] = this.orderList[largest];
            this.orderList[index].setPosInQueue(index);
            this.orderList[largest] = tmp;
            this.orderList[largest].setPosInQueue(largest);
            heapifyDel(largest);
        }

    }
    /**
     *
     * @return return the customer with the maximum priority
     *
     */
    public CustomerOrder getMax() {
        return this.orderList[0];
    }

    /**
     *
     * @return return true if the queue is empty; false else
     *
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     *
     * @return return the number of Customers currently in the queue
     *
     */
    public int size() {
        return this.numOrders;
    }

    /**
     *
     * @return return the CustomerOrder with the given name
     *
     */
    public CustomerOrder get(String name) throws NoSuchAlgorithmException {
        return this.table.get(name);
    }

    /**
     *
     * @return remove and return the CustomerOrder with the specified name from the queue;
     * @return return null if the CustomerOrder isn't in the queue
     *
     */



    public CustomerOrder remove(String name) throws NoSuchAlgorithmException {
        if(this.table.get(name) == null){
            return null;
        }
        int index = this.table.get(name).getPosInQueue();
        this.orderList[index].setPosInQueue(-1);
        CustomerOrder delete = this.orderList[index];
        if(index != size()-1) {
            CustomerOrder lastEl = this.orderList[size() - 1];
            this.orderList[index] = lastEl;
            this.orderList[index].setPosInQueue(index);
        }
        this.orderList[size() - 1] = null;

        this.numOrders--;

        if(!isEmpty() && index != size()) {
            heapify(index);
        }


        return delete;
    }

    private void heapify(int index){
        int parentIndex = (index - 1) / 2;

        if (index != 0 && this.orderList[index].compareTo(this.orderList[parentIndex]) == 1){
            heapifyAdd(index);
        } else {
            heapifyDel(index);
        }
    }

    /**
     *
     * @return update the orderDeliveryTime of the Customer with the specified name to newTime
     *
     */
    public void update(String name, int newTime) throws NoSuchAlgorithmException {
        this.table.get(name).setOrderDeliveryTime(newTime);
        int index = this.table.get(name).getPosInQueue();
        this.orderList[index].setOrderDeliveryTime(newTime);

        heapify(index);
    }


}
