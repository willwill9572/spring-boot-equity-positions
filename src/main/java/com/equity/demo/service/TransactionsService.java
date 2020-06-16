package com.equity.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.equity.demo.mapper.OrderMapper;
import com.equity.demo.model.Order;
import com.equity.demo.model.Positions;

/**  
* @ClassName: TransactionsService  
* @Description: 交易核心处理Service   
* @author will  
* @date 2020年6月14日  
*    
*/   
   
@Service
public class TransactionsService {

	private final static Logger logger = LoggerFactory.getLogger(TransactionsService.class);

	@Autowired
	private OrderMapper orderMapper;

	private ConcurrentHashMap<String, Positions> realTimePositionsMap = new ConcurrentHashMap<String, Positions>();

	public ConcurrentHashMap<String, Positions> getRealTimePositionsMap() {
		return realTimePositionsMap;
	}

	/**
	 * 
	* @Title: init  
	* @Description: 初始化持仓数据
	* @return void    返回类型  
	* @throws
	 */
	@PostConstruct
	public void init() {
		realTimePositionsMap.put("REL", new Positions());
		realTimePositionsMap.put("ITC", new Positions());
		realTimePositionsMap.put("INF", new Positions());
	}

	/**
	 * 
	* @Title: setSign  
	* @Description: 标记正负号，如果是卖单则是计算时应该是负数
	* @param @param order    参数  
	* @return void    返回类型  
	* @throws
	 */
	public void setSign(Order order) {

		if (order.getTradeMark().equals("Sell")) {
			order.setQuantity(0 - order.getQuantity());
		}
	}

    /**
     * 
    * @Title: getNRTPositions  
    * @Description: 实时持仓行情 需要将当前持仓和延迟队列的值合并计算
    * @param @return
    * @param @throws Exception    参数  
    * @return List<String>    返回类型  
    * @throws
     */
	public List<String> getRTPositions() throws Exception {
		List<String> positList = new ArrayList<String>();
		String strValue = "";
		for (Entry<String, Positions> entry : realTimePositionsMap.entrySet()) {
			//获取延迟队列
			Positions positions = entry.getValue();
			CopyOnWriteArrayList<Order> delayOrderList = positions.getDelayOrderList();
			//持仓合并计算
			for (int i = 0; i < delayOrderList.size(); i++) {
				positions.setPositions(delayOrderList.get(i));
			}
			for (int i = delayOrderList.size() - 1; i >= 0; i--) {
				delayOrderList.remove(i);
			}
			strValue = Integer.toString(entry.getValue().getCurrPositions());
			if(entry.getValue().getCurrPositions() > 0) {
				strValue = "+" + strValue;
			} 
			positList.add("股票名称:" + entry.getKey() + ", 当前仓位:" + strValue);
		}
		return positList;
	}

	public List<Order> getAllOrders() throws Exception {

		return orderMapper.getAll();
	}

	/**
	 * 
	* @Title: PositionsCal  
	* @Description: 持仓计算函数  
	* @param @param order    参数  
	* @return void    返回类型  
	* @throws
	 */
	public void PositionsCal(Order order) {
		try {
			// 取得当前股票
			String strKey = order.getSecurityCode();
			Positions positions = realTimePositionsMap.get(strKey);
			
			//只锁当前合约的对象
			synchronized (positions) {
				// 后面版本如果先到的话，加入延迟订单队列
				if(order.getVersion() > 1) {
					positions.addToOrderList(order);	
				}
				// 获取持仓并实时计算
				positions.setPositions(order);
				// 写入缓存
				realTimePositionsMap.put(order.getSecurityCode(), positions);
			}
		} catch (Exception e) {
			logger.error(e.toString());
		} 
	}

	/**
	 * 
	* @Title: insert  
	* @Description: 订单处理 
	* @param @param order    参数  
	* @return void    返回类型  
	* @throws
	 */
	@Transactional
	public void insert(Order order) {
		// 正负号处理
		setSign(order);
		// 持仓计算
		PositionsCal(order);
		// 写数据库
		orderMapper.insert(order);
	}

}
