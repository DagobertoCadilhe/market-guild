package com.marketguild.market_service.repository;

import com.marketguild.market_service.model.MarketListing;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MarketListingRepository extends MongoRepository<MarketListing, String> {
}
