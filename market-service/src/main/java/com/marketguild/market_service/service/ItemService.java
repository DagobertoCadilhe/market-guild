package com.marketguild.market_service.service;

import com.marketguild.market_service.client.PlayerClient;
import com.marketguild.market_service.event.ItemBoughtEvent;
import com.marketguild.market_service.model.Item;
import com.marketguild.market_service.producer.ItemBoughtProducer;
import com.marketguild.market_service.repository.ItemRepository;
import feign.FeignException;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ItemService {

    // find by id, create, find all items

    private final PlayerClient playerClient;
    private final ItemRepository itemRepository;
    private final ItemBoughtProducer itemBoughtProducer;

    public ItemService(PlayerClient playerClient, ItemRepository itemRepository, ItemBoughtProducer itemBoughtProducer) {
        this.playerClient = playerClient;
        this.itemRepository = itemRepository;
        this.itemBoughtProducer = itemBoughtProducer;
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

        try {
            playerClient.findById(sellerId);

            Item newItem = new Item();
            newItem.setName(name);
            newItem.setPrice(price);
            newItem.setSellerId(sellerId);

            itemRepository.save(newItem);

            ItemBoughtEvent  itemBoughtEvent = new ItemBoughtEvent(sellerId, newItem.getId(), price, MDC.get("correlationId"));

            itemBoughtProducer.publishOrderEvent(itemBoughtEvent);

            return newItem;
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found with id: " + sellerId);
        }
    }



}
