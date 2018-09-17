package my.study.ignite.common.bean;

import org.apache.ignite.cache.query.annotations.QuerySqlField;

public class UserIndicator {
	private UserIndicatorKey id;

	@QuerySqlField
	private String login;
	
	@QuerySqlField
	private String symbol;
	
	private Double predictVolume;
	
	private long predictHoldTime;

	public UserIndicatorKey getId() {
		return id;
	}

	public void setId(UserIndicatorKey id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public Double getPredictVolume() {
		return predictVolume;
	}

	public void setPredictVolume(Double predictVolume) {
		this.predictVolume = predictVolume;
	}

	public long getPredictHoldTime() {
		return predictHoldTime;
	}

	public void setPredictHoldTime(long predictHoldTime) {
		this.predictHoldTime = predictHoldTime;
	}
	
	
}
