package com.equity.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.equity.demo.model.Order;
import com.equity.demo.service.TransactionsService;

/**
 * @ClassName: TransactionsController
 * @Description: 交易处理 Controller类
 * @author will
 * @date 2020年6月14日
 * 
 */
@RestController
public class TransactionsController {

	private final static Logger logger = LoggerFactory.getLogger(TransactionsController.class);

	@Autowired
	private TransactionsService transactionsService;

	/**
	 * 
	 * @Title: getRTPositions 
	 * @Description: 实时持仓行情 
	 * @param @return 参数
	 * @return List<String> 返回类型 
	 * @throws
	 */
	@RequestMapping("/getRTPositions")
	public List<String> getRTPositions() {

		try {
			return transactionsService.getRTPositions();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return null;
	}

	/**
	 * 
	 * @Title: insert 
	 * @Description: 订单处理 
	 * @param Order 
	 * @return String 返回类型
	 * @throws
	 */
	@RequestMapping("/order")
	@ResponseBody
	public String insert(Order Order) {
		//参数检查
		if (Order.getTradeID() == null || 
			Order.getVersion() == null || 
			Order.getSecurityCode() == null || 
			Order.getCommand() == null || 
			Order.getQuantity()== null ||
			Order.getTradeMark() == null
			) {
			return "下单出错，参数不能为空";
		}
		//下单
		try {
			transactionsService.insert(Order);
		} catch (Exception e) {
			logger.error(e.toString());
			return "下单失败，异常信息: " + e.toString();
		}
		return "下单成功";
	}
}
