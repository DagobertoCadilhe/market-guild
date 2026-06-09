package com.marketguild.market_service.controller;

import com.marketguild.market_service.dto.ItemDTO;
import com.marketguild.market_service.model.Item;
import com.marketguild.market_service.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    // get find by id, post create, get find all items

    @PostMapping()
    public Item createItem(@RequestBody ItemDTO itemDTO){
        Item newItem = itemService.createItem(itemDTO.getName(), itemDTO.getPrice(), itemDTO.getSellerId());
        return newItem;
    }

    @GetMapping("/{itemId}")
    public Item findItemById (@PathVariable String itemId){
        return itemService.findItemById(itemId);
    }

    @GetMapping("/list")
    public List<Item> findAllItems(){
        return itemService.findAllItems();
    }
}

