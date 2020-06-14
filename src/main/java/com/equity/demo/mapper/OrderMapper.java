package com.equity.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.equity.demo.model.Order;


/**  
* @ClassName: OrderMapper  
* @Description: 数据库操作Mapper 
* @author will  
* @date 2020年6月14日  
*    
*/  
public interface OrderMapper {
	
	@Select("SELECT * FROM t_order")
	@Results({
		@Result(property = "tradeID",  column = "trade_id"),
		@Result(property = "version",  column = "version"),
		@Result(property = "quantity",  column = "quantity"),
		@Result(property = "securityCode",  column = "security_code"),
		@Result(property = "command",   column = "command"),
		@Result(property = "tradeMark", column = "trade_mark")
	})
	List<Order> getAll();
	
	@Insert("INSERT INTO t_order(trade_id,version,quantity,security_code,command,trade_mark) VALUES(#{tradeID}, #{version}, #{quantity}, #{securityCode}, #{command}, #{tradeMark})")
	void insert(Order order);

}