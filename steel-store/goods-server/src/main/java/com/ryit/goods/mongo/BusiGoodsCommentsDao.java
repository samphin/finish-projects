package com.ryit.goods.mongo;

import com.ryit.commons.entity.bson.BusiGoodsComments;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusiGoodsCommentsDao extends MongoRepository<BusiGoodsComments, Long> {
}
