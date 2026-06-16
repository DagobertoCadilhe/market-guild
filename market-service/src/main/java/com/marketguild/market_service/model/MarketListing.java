package com.marketguild.market_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "listings")
public class MarketListing {

    // sellerId, listedItem, listedPrice

    @Id
    private String id;

    private Long sellerId;
    private Item listedItem;
    private Double listedPrice;

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Item getListedItem() {
        return listedItem;
    }

    public void setListedItem(Item listedItem) {
        this.listedItem = listedItem;
    }

    public Double getListedPrice() {
        return listedPrice;
    }

    public void setListedPrice(Double listedPrice) {
        this.listedPrice = listedPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
