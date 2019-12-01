package com.ryit.talk.po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 *  优惠券表
 * @author samphin
 */
@Document(collection="coupon")
public class Coupon implements Serializable {

	/**
	 * 通过分布式ID生成策略，雪花算法生成 文章ID
	 */
	//id属性是给mongodb用的，用@Id注解修饰
	@Id
	private Long id;

	/**
	 * 优惠券名称
	 */
	private String name;

	/**
	 * 优惠券面值（如果满减券则为满金额，条件值）
	 */
	private Double coin;

	/**
	 * 优惠金额面值（针对满减券，折扣值）
	 */
	private Double discountCoin;

	/**
	 * 优惠券类型:1：免单券，2：满减券
	 */
	private Integer type;

	/**
	 * 有效期起始时间
	 */
	private Date validStartTime;

	/**
	 * 有效期结束时间
	 */
	private Date validEndTime;
}
