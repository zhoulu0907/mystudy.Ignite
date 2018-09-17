package my.study.ignite.common.bean;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;

public class UserIndicatorKey {

	@AffinityKeyMapped
	private String login;
	
	private String symbol;

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
	
}
