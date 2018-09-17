package my.study.ignite.common.bean;

import java.io.Serializable;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class UserTradeInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserTradeInfoKey id;
	private String deal;
	@QuerySqlField
	private String login;
	private String type;
	@QuerySqlField
	private String symbol;
	private Double volume;
	private Long holdtime;//s
	private Double profit;
	
	public UserTradeInfoKey getId() {
		return id;
	}
	public void setId(UserTradeInfoKey id) {
		this.id = id;
	}
	public String getDeal() {
		return deal;
	}
	public void setDeal(String deal) {
		this.deal = deal;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	public long getHoldtime() {
		return holdtime;
	}
	public void setHoldtime(long holdtime) {
		this.holdtime = holdtime;
	}
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}

	
}
