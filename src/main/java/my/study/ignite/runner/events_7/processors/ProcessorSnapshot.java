package my.study.ignite.runner.events_7.processors;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCountDownLatch;
import org.apache.ignite.lang.IgniteRunnable;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import my.study.ignite.common.bean.UserTradeInfo;
import my.study.ignite.common.utils.IgniteUtils;
import my.study.ignite.service.UserTradeInfoService;

@Component
@Slf4j
public class ProcessorSnapshot implements Processor {
	
	@Resource
	private UserTradeInfoService userTradeInfoService;
	
	@Resource
	private IgniteUtils igniteUtils;

	@Override
	public void onEvent() {
		// TODO Auto-generated method stub
		List<String> logins = userTradeInfoService.getLogins();
		Ignite ignite = igniteUtils.getIgniteInstance();
		IgniteCountDownLatch cntDwnLatch = ignite.countDownLatch("snapshot", logins.size(), true, true);
		for (String login : logins) {
			ignite.compute().runAsync(new IgniteRunnable() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public void run() {
					// TODO Auto-generated method stub
					List<UserTradeInfo> userTradeInfos = userTradeInfoService.getUserTradeInfos(login);
					double totalProfit = 0;
					for (UserTradeInfo userTradeInfo : userTradeInfos) {
						totalProfit += userTradeInfo.getProfit();
					}
					log.info("login: " + login + ", profit: " + totalProfit + ", trades: " + userTradeInfos.size());
					cntDwnLatch.countDown();
				}
			});
		}
		cntDwnLatch.await();
	}

//	@Override
//	public void onEvent() {
//		// TODO Auto-generated method stub
//		List<String> logins = userTradeInfoService.getLogins();
//		for (String login : logins) {
//			List<UserTradeInfo> userTradeInfos = userTradeInfoService.getUserTradeInfos(login);
//			double totalProfit = 0;
//			for (UserTradeInfo userTradeInfo : userTradeInfos) {
//				totalProfit += userTradeInfo.getProfit();
//			}
//			log.info("login: " + login + ", profit: " + totalProfit);
//		}
//	}

}
