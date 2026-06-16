package com.marketguild.market_service.dto;

public class MarketListingDTO {

    private Long sellerId;
    private String listedItemId;
    private Double listedPrice;


    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getListedItemId() {
        return listedItemId;
    }

    public void setListedItemId(String listedItemId) {
        this.listedItemId = listedItemId;
    }

    public Double getListedPrice() {
        return listedPrice;
    }

    public void setListedPrice(Double listedPrice) {
        this.listedPrice = listedPrice;
    }
}
