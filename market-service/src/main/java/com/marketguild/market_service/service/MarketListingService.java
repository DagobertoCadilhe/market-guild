package com.marketguild.market_service.service;

import com.marketguild.market_service.client.PlayerClient;
import com.marketguild.market_service.dto.PlayerDTO;
import com.marketguild.market_service.event.ItemBoughtEvent;
import com.marketguild.market_service.model.Item;
import com.marketguild.market_service.model.MarketListing;
import com.marketguild.market_service.producer.ItemBoughtProducer;
import com.marketguild.market_service.repository.ItemRepository;
import com.marketguild.market_service.repository.MarketListingRepository;
import feign.FeignException;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MarketListingService {

    private final MarketListingRepository marketListingRepository;
    private final PlayerClient playerClient;
    private final ItemService itemService;
    private final ItemBoughtProducer itemBoughtProducer;

    public MarketListingService(MarketListingRepository marketListingRepository, PlayerClient playerClient, ItemService itemService, ItemBoughtProducer itemBoughtProducer) {
        this.marketListingRepository = marketListingRepository;
        this.playerClient = playerClient;
        this.itemService = itemService;
        this.itemBoughtProducer = itemBoughtProducer;
    }

    public MarketListing createItemListing(Long sellerId, String listedItemId, Double listedPrice){
        MarketListing newListing = new MarketListing();

        Item listedItem = itemService.findItemById(listedItemId);

        newListing.setSellerId(sellerId);
        newListing.setListedItem(listedItem);
        newListing.setListedPrice(listedPrice);

        marketListingRepository.save(newListing);
        return newListing;
    }

    public List<MarketListing> findAllListedItems() {
        return marketListingRepository.findAll();
    }

    public MarketListing findItemById(String itemId){
        MarketListing item = marketListingRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Item not found with id: "+ itemId));

        return item;
    }

    public MarketListing buyListedItem(String itemId, Long buyerId){
        try {
            MarketListing boughtItem = findItemById(itemId);
            PlayerDTO buyer = playerClient.findById(buyerId);

            if (buyer.getBalance() < boughtItem.getListedPrice()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
            }

            ItemBoughtEvent itemBoughtEvent = new ItemBoughtEvent(
                    buyerId, boughtItem.getId(),
                    boughtItem.getListedPrice(),
                    MDC.get("correlationId")
            );

            itemBoughtProducer.publishOrderEvent(itemBoughtEvent);

            marketListingRepository.delete(boughtItem);

            return boughtItem;
        } catch (FeignException.NotFound e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found with id: " + buyerId);
        }
    }
}