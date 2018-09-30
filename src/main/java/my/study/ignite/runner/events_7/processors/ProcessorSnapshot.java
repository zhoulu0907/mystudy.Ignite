package my.study.ignite.runner.events_7.processors;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import my.study.ignite.common.bean.UserTradeInfo;
import my.study.ignite.service.UserTradeInfoService;

@Component
@Slf4j
public class ProcessorSnapshot implements Processor {
	
	@Resource
	private UserTradeInfoService userTradeInfoService;

	@Override
	public void onEvent() {
		// TODO Auto-generated method stub
		List<String> logins = userTradeInfoService.getLogins();
		for (String login : logins) {
			List<UserTradeInfo> userTradeInfos = userTradeInfoService.getUserTradeInfos(login);
			double totalProfit = 0;
			for (UserTradeInfo userTradeInfo : userTradeInfos) {
				totalProfit += userTradeInfo.getProfit();
			}
			log.info("login: " + login + ", profit: " + totalProfit);
		}
	}

}
