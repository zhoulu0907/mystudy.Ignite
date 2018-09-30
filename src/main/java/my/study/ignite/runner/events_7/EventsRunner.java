package my.study.ignite.runner.events_7;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import my.study.dataprepare.bean.ForexTrade;
import my.study.dataprepare.mapper.ForexTradeMapper;
import my.study.ignite.common.Constant;
import my.study.ignite.common.bean.UserTradeInfo;
import my.study.ignite.common.bean.UserTradeInfoKey;
import my.study.ignite.common.utils.IgniteUtils;
import my.study.ignite.runner.events_7.processors.Processor;
import my.study.ignite.runner.events_7.task.ProcessorTask;

@Component
@Order(7)
@Slf4j
public class EventsRunner implements CommandLineRunner, ApplicationContextAware{

	@Resource
	private IgniteUtils igniteUtils;
	
	@Resource
	private ForexTradeMapper forexTradeMapper;
	
	private Map<String, Processor> processorMap;
	
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
			log.info("deal: " + forexTrade.getDeal() + ", profit: " + forexTrade.getProfit());
		}
		cache.putAll(userTradeInfoMap);
		
		//one by one task
		ignite.compute().execute(ProcessorTask.class, "");

//		processorMap.get("processorAddOne").onEvent();
//		processorMap.get("processorAddTwo").onEvent();
//		processorMap.get("processorSnapshot").onEvent();
		
	}

	@Override
	public void setApplicationContext(ApplicationContext cnt) throws BeansException {
		// TODO Auto-generated method stub
		 this.processorMap = cnt.getBeansOfType(Processor.class);
	}

}
