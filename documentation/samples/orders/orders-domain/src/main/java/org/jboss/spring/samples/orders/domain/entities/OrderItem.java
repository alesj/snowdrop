package org.jboss.spring.samples.orders.domain.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;

/**
 * @author: marius
 */
@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItem {

    @Id
    @Column(name = "ORDER_ITEM_ID")
    private long id;

    @Column(name = "ORDER_ITEM_NAME")
    private String name;

    @Column(name = "ORDER_ITEM_QUANTITY")
    private double quantity;

    @Column(name = "ORDER_ITEM_PRICE_PER_UNIT")
    private double pricePerUnit;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
