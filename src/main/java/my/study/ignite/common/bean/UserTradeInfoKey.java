package my.study.ignite.common.bean;

import java.io.Serializable;

import org.apache.ignite.cache.affinity.AffinityKeyMapped;

import lombok.Data;

@Data
public class UserTradeInfoKey implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@AffinityKeyMapped
	private String login;
	private String deal;
	
	public UserTradeInfoKey(String login, String deal) {
		this.login = login;
		this.deal = deal;
	}
		
}
