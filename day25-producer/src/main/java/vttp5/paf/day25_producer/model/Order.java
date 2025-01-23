package vttp5.paf.day25_producer.model;

import java.util.List;

public class Order {
    
    private int id; // no id until auto incr, so ex

    private String fullName;

    private List<OrderDetail> lineItem;

    public Order() {
    }

    public Order(int id, String fullName, List<OrderDetail> lineItem) {
        this.id = id;
        this.fullName = fullName;
        this.lineItem = lineItem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<OrderDetail> getLineItem() {
        return lineItem;
    }

    public void setLineItem(List<OrderDetail> lineItem) {
        this.lineItem = lineItem;
    }

    
}
