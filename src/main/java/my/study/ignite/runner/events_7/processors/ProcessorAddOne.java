package my.study.ignite.runner.events_7.processors;

import my.study.ignite.common.bean.UserTradeInfo;
import my.study.ignite.common.bean.UserTradeInfoKey;
import my.study.ignite.service.UserTradeInfoService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProcessorAddOne implements Processor {
	
	@Resource(name="UserTradeInfoService")
	private UserTradeInfoService userTradeInfoService;
	@Override
	public void onEvent() {
		// TODO Auto-generated method stub
		List<UserTradeInfo> userTradeInfos = userTradeInfoService.getUserTradeInfos("");
		Map<UserTradeInfoKey, UserTradeInfo> userTradeInfoMap = new HashMap<UserTradeInfoKey, UserTradeInfo>(userTradeInfos.size());
		for (UserTradeInfo userTradeInfo : userTradeInfos) {
			double profit = userTradeInfo.getProfit() + 1;
			userTradeInfo.setProfit(profit);
			userTradeInfoMap.put(userTradeInfo.getId(), userTradeInfo);
		}
		userTradeInfoService.updateUserTradeInfos(userTradeInfoMap);
		log.info("profit add 1 by totally.");
	}

}
