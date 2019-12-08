package com.ryit.goods.mongo;

import com.ryit.commons.entity.bson.BusiGoodsComments;
import com.ryit.commons.entity.bson.BusiGoodsCommentsImgs;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusiGoodsCommentsImgsDao extends MongoRepository<BusiGoodsCommentsImgs, Long> {
}
