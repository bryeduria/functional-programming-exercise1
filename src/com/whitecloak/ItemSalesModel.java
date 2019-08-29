package com.whitecloak;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ItemSalesModel {

    String itemType;
    LocalDate orderDate;
    Integer unitsSold;
    BigDecimal unitPrice;

    public ItemSalesModel(String itemType, LocalDate orderDate, Integer unitsSold, BigDecimal unitPrice) {
        this.itemType = itemType;
        this.orderDate = orderDate;
        this.unitsSold = unitsSold;
        this.unitPrice = unitPrice;
    }

    public String getItemType() {
        return itemType;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Integer getUnitsSold() {
        return unitsSold;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

}
