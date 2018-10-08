package my.study.dataprepare.bean;

import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class ForexTrade implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String deal;
	private String login;
	private String type;
	private String symbol;
	private Double volume;
	private Timestamp opentime;
	private Timestamp closetime;
	private Double profit;

	
}
