package com.equity.demo.ut;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.equity.demo.model.Order;
import com.equity.demo.service.TransactionsService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc // 注入一个MockMvc实例
public class TransServiceTest {

	private final static Logger logger = LoggerFactory.getLogger(OrderMapperTest.class);

	@Autowired
	private TransactionsService transactionsService;

	@Test
	public void testPositionsCal() throws Exception {
		List<Order> ordList = new ArrayList<Order>();
		Order ord1 = new Order(1, 1, 50, "REL", "INSERT", "Buy");
		Order ord2 = new Order(2, 1, 40, "ITC", "INSERT", "Sell");
		Order ord3 = new Order(3, 1, 70, "INF", "INSERT", "Buy");
		Order ord4 = new Order(1, 2, 60, "REL", "UPDATE", "Buy");
		Order ord5 = new Order(2, 2, 30, "ITC", "CANCEL", "Buy");
		Order ord6 = new Order(4, 1, 20, "INF", "INSERT", "Sell");
		ordList.add(ord1);
		ordList.add(ord2);
		ordList.add(ord3);
		ordList.add(ord4);
		ordList.add(ord5);
		ordList.add(ord6);

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(6, 6, 0L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>());

		for (int i = 0; i < 6; i++) {
			int n = i;
			threadPoolExecutor.submit(new Runnable() {
				@Override
				public void run() {
					transactionsService.insert(ordList.get(n));
				}
			});
		}
		Thread.sleep(3000);
		threadPoolExecutor.shutdown();
		try {
			if (!threadPoolExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
				// 超时的时候向线程池中所有的线程发出中断(interrupted)。
				threadPoolExecutor.shutdownNow();
			}
			logger.info("AwaitTermination Finished");
		} catch (InterruptedException ignore) {
			threadPoolExecutor.shutdownNow();
		}

		logger.info("------" + transactionsService.getRTPositions() + "------");
		threadPoolExecutor.shutdown();

	}

}
