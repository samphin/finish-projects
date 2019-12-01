package com.ryit.talk.dao;

import org.bson.types.ObjectId;
import com.ryit.talk.po.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * MongoRepository实现了的只是最基本的增删改查的功能，要想增加额外的查询方法，
 * 可以按照以下规则定义接口的方法。
 * 自定义查询方法，格式为“findBy+字段名+方法后缀”，方法传进的参数即字段的值，此外还支持分页查询，
 * 通过传进一个Pageable对象，返回Page集合。
 * @author samphin
 *
 */
/**
 * 	查询操作细节
 * 	GreaterThan(大于) 
	findByAgeGreaterThan(int age) 
	{"age" : {"$gt" : age}}
	
	LessThan（小于） 
	findByAgeLessThan(int age) 
	{"age" : {"$lt" : age}}
	
	Between（在...之间） 
	findByAgeBetween(int from, int to) 
	{"age" : {"$gt" : from, "$lt" : to}}
	
	IsNotNull, NotNull（是否非空） 
	findByFirstnameNotNull() 
	{"age" : {"$ne" : null}}
	
	IsNull, Null（是否为空） 
	findByFirstnameNull() 
	{"age" : null}
	
	Like（模糊查询） 
	findByFirstnameLike(String name) 
	{"age" : age} ( age as regex)
	
	(No keyword) findByFirstname(String name) 
	{"age" : name}
	
	Not（不包含） 
	findByFirstnameNot(String name) 
	{"age" : {"$ne" : name}}
	
	Near（查询地理位置相近的） 
	findByLocationNear(Point point) 
	{"location" : {"$near" : [x,y]}}
	
	Within（在地理位置范围内的） 
	findByLocationWithin(Circle circle) 
	{"location" : {"$within" : {"$center" : [ [x, y], distance]}}}
	
	Within（在地理位置范围内的） 
	findByLocationWithin(Box box) 
	{"location" : {"$within" : {"$box" : [ [x1, y1], x2, y2]}}}
 *
 */
public interface CouponRepository extends MongoRepository<Coupon, ObjectId> {

	Coupon findByTitle(String name);
	
	/**
	 * @Query注解描述： 
	 * value是查询的条件，"?0" 是占位符，对应着方法中参数中的第一个参数，
	 * 如果对应的是第二个参数则为"?1"；
	 * fields是指定要返回的字段，其中id是自动返回的，bson中{'name':1}的1代表true，代表返回的意思。 
	 * @param name
	 * @param pageable
	 * @return
	 */
	@Query(value="{'name':?0}",fields="{'name':1,'content':1,'publishDate':1}")
	Page<Coupon> findByTitleLike(String name, Pageable pageable);
	
	/**
	 * 查询标题不为空的
	 * @param pageable
	 * @return
	 */
	@Query(value="{'name':{'$ne':null}}",fields="{'name':1,'content':1,'publishDate':1}")
	public Page<Coupon> findByTitleNotNull(Pageable pageable);
	
}
