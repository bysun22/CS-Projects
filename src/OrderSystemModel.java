/**
 *
 * @author Brian Sun,
 * @username bysun,
 * @sources
 */

import java.security.NoSuchAlgorithmException;

public class OrderSystemModel {
    private BetterCustomerOrderQueue orderList;
    private int capacityThreshold;
    private int ordersDelayed;
    private int ordersOnTime;
    private int ordersCanceled;
    private int time;
    private int totalDelayTime;

    public int getOrdersDelayed() {
        return ordersDelayed;
    }

    public int getOrdersOnTime() {
        return ordersOnTime;
    }

    public int getOrdersCanceled() {
        return ordersCanceled;
    }

    public int getTotalDelayTime() {
        return totalDelayTime;
    }

    public BetterCustomerOrderQueue getOrderList() {
        return orderList;
    }

    /**
     * Constructor of the class.
     *
     * Initialize a new OrderSystemModel and OrderSystemModel
     *
     */
    public OrderSystemModel(int capacityThreshold) {
        this.capacityThreshold = capacityThreshold;
        this.orderList = new BetterCustomerOrderQueue(this.capacityThreshold);
        this.ordersDelayed = 0;
        this.ordersOnTime = 0;
        this.ordersCanceled = 0;
        this.time = 0;
        this.totalDelayTime = 0;
    }

    public boolean isFull(){
        if(this.orderList.size() < this.orderList.getOrderList().length){
            return false;
        }
        return true;
    }

    /**
     *
     * @return Process a new CustomerOrder with a given name.
     *
     */
    public String process(String name, int orderTime, int deliveryTime) throws NoSuchAlgorithmException {
        CustomerOrder c = new CustomerOrder(name, orderTime, deliveryTime);
        if(!isFull()){
            this.orderList.insert(c);
            return name;
        } else {
            if(c.getOrderDeliveryTime() < this.orderList.getMax().getOrderDeliveryTime()){
                completeOrder(c);
                return null;
            } else {
                String orderName = completeNextOrder();
                this.orderList.insert(c);
                return orderName;
            }
        }
    }

    /**
     *
     * @return Complete the highest priority order
     *
     */
    public String completeNextOrder() throws NoSuchAlgorithmException {
        if(this.orderList.isEmpty()) {
            return null;
        } else {
            return completeOrder(this.orderList.getMax());
        }
    }

    public String completeOrder(CustomerOrder c) throws NoSuchAlgorithmException {
        if(time - c.getOrderDeliveryTime() > 0){
            this.totalDelayTime = totalDelayTime + (time - c.getOrderDeliveryTime());
            this.ordersDelayed++;
        } else {
            this.ordersOnTime++;
        }

        this.time++;
        this.orderList.remove(c.getName());
        return c.getName();
    }

    /**
     *
     * @return Update the delivery time of the order for the given name
     *
     */
    public String updateOrderTime(String name, int newDeliveryTime) throws NoSuchAlgorithmException {
        CustomerOrder c = this.orderList.get(name);
        if(c == null){
            return null;
        } else {
            if(newDeliveryTime < this.orderList.getMax().getOrderDeliveryTime()){
                completeOrder(c);
                return name;
            } else if (newDeliveryTime < this.time){
                cancelOrder(name);
                return name;
            } else {
                this.orderList.update(name,newDeliveryTime);
                return null;
            }
        }

    }

    /**
     *
     * @return Cancel the order for the given name
     *
     */
    public CustomerOrder cancelOrder(String name) throws NoSuchAlgorithmException {
        this.ordersCanceled++;
        return this.orderList.remove(name);

    }


}
