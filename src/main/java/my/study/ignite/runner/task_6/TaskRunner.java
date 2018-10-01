package my.study.ignite.runner.task_6;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.compute.ComputeTaskFuture;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import my.study.dataprepare.bean.ForexTrade;
import my.study.dataprepare.mapper.ForexTradeMapper;
import my.study.ignite.common.Constant;
import my.study.ignite.common.bean.UserTradeInfo;
import my.study.ignite.common.bean.UserTradeInfoKey;
import my.study.ignite.common.utils.IgniteUtils;
import my.study.ignite.runner.task_6.tasks.ProfitTransTask;

//@Component
@Order(6)
@Slf4j
public class TaskRunner implements CommandLineRunner {

	@Resource
	private IgniteUtils igniteUtils;
	
	@Resource
	private ForexTradeMapper forexTradeMapper;
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Ignite ignite = igniteUtils.getIgniteInstance();
		IgniteCache<UserTradeInfoKey, UserTradeInfo> cache = ignite.cache(Constant.CACHE_USER_TRADE);
		List<ForexTrade> forexTradeList = forexTradeMapper.find(0, 10);
		//save data
		int dealId = 1;
		Map<UserTradeInfoKey, UserTradeInfo> userTradeInfoMap = new HashMap<UserTradeInfoKey, UserTradeInfo>();
		for (ForexTrade forexTrade : forexTradeList) {
			UserTradeInfoKey userTradeInfoKey = new UserTradeInfoKey(forexTrade.getLogin(), "" + dealId++);
			UserTradeInfo userTradeInfo = new UserTradeInfo(userTradeInfoKey);
			userTradeInfo.setSymbol(forexTrade.getSymbol());
			userTradeInfo.setType(forexTrade.getType());
			userTradeInfo.setVolume(forexTrade.getVolume());
			userTradeInfo.setProfit(forexTrade.getProfit());
			userTradeInfo.setHoldtime(forexTrade.getClosetime().getTime() 
					- forexTrade.getOpentime().getTime());
			userTradeInfoMap.put(userTradeInfoKey, userTradeInfo);
			log.info("deal: " + userTradeInfo.getDeal() + ", profit: " + forexTrade.getProfit());
		}
		cache.putAll(userTradeInfoMap);
		
		log.info("Start calculation.");
		//calculation task
//		Map<UserTradeInfoKey, UserTradeInfo> rstMap = ignite.compute().execute(ProfitTransTask.class, userTradeInfoMap);
		for (Entry<UserTradeInfoKey, UserTradeInfo> e : userTradeInfoMap.entrySet()) {
			Map<UserTradeInfoKey, UserTradeInfo> jobMap = new HashMap<>(1);
			jobMap.put(e.getKey(), e.getValue());
			log.info("start deal: " + e.getValue().getDeal());
			ComputeTaskFuture<Map<UserTradeInfoKey, UserTradeInfo>> rst = 
					ignite.compute().executeAsync(ProfitTransTask.class, jobMap);
			Map<UserTradeInfoKey, UserTradeInfo> rstMap = rst.get();
			for (Entry<UserTradeInfoKey, UserTradeInfo> rstE : rstMap.entrySet()) {
				log.info("deal: " + rstE.getValue().getDeal() + ", profit: " + rstE.getValue().getProfit());
			}
		}
//		ComputeTaskFuture<Map<UserTradeInfoKey, UserTradeInfo>> rst = 
//				ignite.compute().executeAsync(ProfitTransTask.class, userTradeInfoMap);
//		Map<UserTradeInfoKey, UserTradeInfo> rstMap = rst.get();
//		for (Entry<UserTradeInfoKey, UserTradeInfo> e : rstMap.entrySet()) {
//			log.info("deal: " + e.getValue().getDeal() + ", profit: " + e.getValue().getProfit());
//		}
	}

}
