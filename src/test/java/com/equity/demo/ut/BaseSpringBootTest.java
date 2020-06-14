package com.equity.demo.ut;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import com.equity.demo.controller.TransactionsController;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class BaseSpringBootTest {
	protected final static Logger logger = LoggerFactory.getLogger(TransactionsController.class);

	@Before
	public void init() {
		logger.info("开始测试...");
	}

	@After
	public void after() {
		logger.info("测试结束...");
	}
}