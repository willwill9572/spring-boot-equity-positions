package com.equity.demo.model;

import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

import com.equity.demo.common.Command;

/**
 * @ClassName: Positions
 * @Description: 持仓类
 * @author will
 * @date 2020年6月14日
 * 
 */
public class Positions {
	// 延迟交易队列
	CopyOnWriteArrayList<Order> delayOrderList = new CopyOnWriteArrayList<Order>();
	// 股票状态(-1为取消)
	private int nstate = 0;
	// 初始仓位
	private int openPositions = 0;
	// 当前仓位
	private int currPositions = 0;

	public int getOpenPositions() {
		return openPositions;
	}

	public void setOpenPositions(int openPositions) {
		this.openPositions = openPositions;
	}

	public int getNstate() {
		return nstate;
	}

	public void setNstate(int nstate) {
		this.nstate = nstate;
	}

	public int getCurrPositions() {
		return currPositions;
	}

	public void setCurrPositions(int currPositions) {
		this.currPositions = currPositions;
	}

	public CopyOnWriteArrayList<Order> getDelayOrderList() {
		return delayOrderList;
	}

	public void setDelayOrderList(CopyOnWriteArrayList<Order> delayOrderList) {
		this.delayOrderList = delayOrderList;
	}

	public void addToOrderList(Order order) {

		delayOrderList.add(order);
		Collections.sort(delayOrderList);

	}

	public void setPositions(Order order) {
		// 如果已经是取消状态则直接返回
		if (nstate == -1) {
			return;
		}
		if (order.getCommand().equals(Command.INSERT)) {
			setCurrPositions(currPositions + order.getQuantity());
		}
		if (order.getCommand().equals(Command.UPDATE)) {
			setCurrPositions(order.getQuantity());
		}
		if (order.getCommand().equals(Command.CANCEL)) {
			setNstate(-1);
			setCurrPositions(openPositions);
		}
	}
}
