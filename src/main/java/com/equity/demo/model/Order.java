package com.equity.demo.model;

import java.io.Serializable;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;


/**  
* @ClassName: Order  
* @Description: 订单类
* @author will  
* @date 2020年6月14日  
*    
*/  
public class Order implements Comparable<Order>,Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long transactionID;
	
	@NotBlank(message = "tradeID不能为空")
	@DecimalMin("1")
	private Integer tradeID;
	
	@NotBlank(message = "version不能为空")
	@DecimalMin("1")
	private Integer version;
	
	@NotBlank(message = "quantity不能为空")
	private Integer quantity;
	
	@NotBlank(message = "securityCode不能为空")
	private String securityCode;
	
	@NotBlank(message = "command不能为空")
	private String command;
	
	@NotBlank(message = "tradeMark不能为空")
	private String tradeMark;
	
	public Order() {
		
	}
	
	public Order(int tradeID, int version, int quantity, String securityCode, String command,
			String tradeMark) {
		super();
		this.tradeID = tradeID;
		this.version = version;
		this.quantity = quantity;
		this.securityCode = securityCode;
		this.command = command;
		this.tradeMark = tradeMark;
	}
	
	public Long getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(Long transactionID) {
		this.transactionID = transactionID;
	}

	public Integer getTradeID() {
		return tradeID;
	}

	public void setTradeID(Integer tradeID) {
		this.tradeID = tradeID;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getTradeMark() {
		return tradeMark;
	}

	public void setTradeMark(String tradeMark) {
		this.tradeMark = tradeMark;
	}

	@Override
	public String toString() {
		return "Order [transactionID=" + transactionID + ", tradeID=" + tradeID + ", version=" + version + ", quantity="
				+ quantity + ", securityCode=" + securityCode + ", command=" + command
				+ ", tradeMark=" + tradeMark + "]";
	}
	
	@Override
	public int compareTo(Order ord) {
		if (this.tradeID == ord.tradeID) {
			return this.version - ord.version;
		} else {
			return this.tradeID - ord.tradeID;
		}
	}
}