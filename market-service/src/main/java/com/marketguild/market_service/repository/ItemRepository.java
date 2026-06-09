package com.marketguild.market_service.repository;

import com.marketguild.market_service.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String> {
}
