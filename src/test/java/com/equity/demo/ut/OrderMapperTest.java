package com.equity.demo.ut;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.equity.demo.mapper.OrderMapper;
import com.equity.demo.model.Order;

@RunWith(SpringRunner.class)
@Transactional//回滚所有增删改
@SpringBootTest
@AutoConfigureMockMvc//注入一个MockMvc实例
public class OrderMapperTest {
	
    private final static Logger logger = LoggerFactory.getLogger(OrderMapperTest.class);

	@Autowired
	private OrderMapper orderMapper;

	@Test
	public void testInsert() throws Exception {
		orderMapper.insert(new Order(1, 1, 50,"REL","INSERT","Buy"));
		orderMapper.insert(new Order(2, 1, 40,"ITC","INSERT","Sell"));
		orderMapper.insert(new Order(3, 1, 70,"INF","INSERT","Buy"));
		orderMapper.insert(new Order(1, 2, 60,"REL","UPDATE","Buy"));
		orderMapper.insert(new Order(2, 2, 30,"ITC","CANCEL","Buy"));
		orderMapper.insert(new Order(4, 1, 20,"INF","INSERT","Sell"));

		Assert.assertEquals(6, orderMapper.getAll().size());
	}

	@Test
	public void testQuery() throws Exception {
		List<Order> order = orderMapper.getAll();
		logger.info(order.toString());
	}
}