package com.marketguild.market_service.controller;

import com.marketguild.market_service.dto.BuyItemDTO;
import com.marketguild.market_service.dto.MarketListingDTO;
import com.marketguild.market_service.model.MarketListing;
import com.marketguild.market_service.service.MarketListingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listing")
public class MarketListingController {

    private final MarketListingService marketListingService;

    public MarketListingController(MarketListingService marketListingService) {
        this.marketListingService = marketListingService;
    }

    @PostMapping()
    public MarketListing createListing(@RequestBody MarketListingDTO listingDTO){
        MarketListing listing = marketListingService.createItemListing(
                listingDTO.getSellerId(),
                listingDTO.getListedItemId(),
                listingDTO.getListedPrice()
        );

        return listing;
    }

    @GetMapping("/active")
    public List<MarketListing> findAllActiveLisitings (){
        return marketListingService.findAllListedItems();
    }

    @GetMapping("/active/{listingId}")
    public MarketListing findActiveListingById (@PathVariable String listingId){
        return marketListingService.findItemById(listingId);
    }

    @PostMapping("/buy")
    public MarketListing buyListedItem (@RequestBody BuyItemDTO buyItemDTO){
        return marketListingService.buyListedItem(buyItemDTO.getItemId(), buyItemDTO.getBuyerId());
    }
}
