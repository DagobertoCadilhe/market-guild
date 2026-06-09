package com.marketguild.market_service.service;

import com.marketguild.market_service.model.Item;
import com.marketguild.market_service.repository.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {

    // find by id, create, find all items

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Item findItemById(String itemId){
        Item foundItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: "+ itemId));
        return foundItem;
    }

    public List<Item> findAllItems(){
        return itemRepository.findAll();
    }

    public Item createItem(String name, Double price, Long sellerId){
        Item newItem = new Item();
        newItem.setName(name);
        newItem.setPrice(price);
        newItem.setSellerId(sellerId);

        itemRepository.save(newItem);
        return newItem;
    }



}
