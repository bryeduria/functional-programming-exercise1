package com.whitecloak;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ItemSalesModel {

    private String itemType;
    private LocalDate orderDate;
    private Integer unitsSold;
    private BigDecimal unitPrice;

    public ItemSalesModel(String itemType, LocalDate orderDate, Integer unitsSold, BigDecimal unitPrice) {
        this.setItemType(itemType);
        this.setOrderDate(orderDate);
        this.setUnitsSold(unitsSold);
        this.setUnitPrice(unitPrice);
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

    public BigDecimal getProductTotal (Integer unitsSold, BigDecimal unitPrice) { return getUnitPrice().multiply(BigDecimal.valueOf(getUnitsSold())); }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setUnitsSold(Integer unitsSold) {
        this.unitsSold = unitsSold;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
