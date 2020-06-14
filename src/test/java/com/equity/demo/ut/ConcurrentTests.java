package com.equity.demo.ut;

import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.equity.demo.service.TransactionsService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConcurrentTests {
	@Autowired
	public TransactionsService transactionsService;

	@Test
	public void contextLoads() {
	}

	// 引入 ContiPerf 进行性能测试
	@Rule
	public ContiPerfRule contiPerfRule = new ContiPerfRule();

	@Test
	// 10个线程 执行10次
	@PerfTest(invocations = 100, threads = 10)
	public void test() {

	}

	@Test
	public void test2() throws Exception {
		ExecutorService executorService = Executors.newCachedThreadPool();
		final Semaphore semaphore = new Semaphore(200);
		final CountDownLatch countDownLatch = new CountDownLatch(500);
		long l = System.currentTimeMillis();
		for (int i = 0; i < 200; i++) {
			final int count = i;
			executorService.execute(() -> {
				try {
					semaphore.acquire();
				    //业务代码
					semaphore.release();
				} catch (Exception e) {
				
				}
				countDownLatch.countDown();
			});
		}
		countDownLatch.await();
		long a = System.currentTimeMillis();
		System.out.println(a - l);

		executorService.shutdown();

		// log.info("size:{}" , map.size());
	}
}
