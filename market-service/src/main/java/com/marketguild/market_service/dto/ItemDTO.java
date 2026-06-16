package com.marketguild.market_service.dto;

import com.marketguild.market_service.model.ItemType;

public class ItemDTO {

    private String name;
    private ItemType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }
}
