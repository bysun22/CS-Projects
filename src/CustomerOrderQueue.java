/**
 * @author Brian Sun,
 * @username bysun,
 * @sources
 */


public class CustomerOrderQueue {
    private CustomerOrder[] orderList;
    private int numOrders;

    /**
     *
     * @return return the priority queue
     *
     */
    public CustomerOrder[] getOrderList() {
        return this.orderList;
    }

    /**
     *
     * @return return the number of orders
     *
     */
    public int getNumOrders() {
        return this.numOrders;
    }

    /**
     * Constructor of the class.
     * @return complete the default Constructor of the class
     *
     * Initilalize a new CustomerOrder array with the argument passed.
     *
     */
    public CustomerOrderQueue(int capacity) {
        this.numOrders = 0;
        this.orderList = new CustomerOrder[capacity];
    }

    /**
     * insert a new customer order into the priority queue.
     *
     * @return return the index at which the customer was inserted
     *
     */
    public int insert(CustomerOrder c) {
        if(size() >= this.orderList.length){
            return -1;
        }
        this.numOrders++;
        this.orderList[size() - 1] = c;
        this.orderList[size() - 1].setPosInQueue(size() - 1);
        heapifyAdd(size() - 1);


        return size()-1;
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
     * @return return the customer removed
     *
     */
    public CustomerOrder delMax() {
        if(isEmpty()){
            return null;
        }
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
     * @return return true if the queue is empty; false else
     *
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     *
     * @return return the customer with the maximum priority
     *
     */
    public CustomerOrder getMax() {
        return this.orderList[0];
    }

}
