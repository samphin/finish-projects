package com.ryit.order.mongo;

import com.ryit.commons.entity.bson.BusiGoodsComplaint;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BusiGoodsComplaintDao extends MongoRepository<BusiGoodsComplaint, Long> {
}
