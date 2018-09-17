package my.study.ignite.init;

import javax.annotation.Resource;

import org.apache.ignite.Ignite;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import my.study.dataprepare.bean.ForexTrade;
import my.study.ignite.common.Constant;
import my.study.ignite.common.bean.UserTradeInfo;
import my.study.ignite.common.bean.UserTradeInfoKey;
import my.study.ignite.common.utils.IgniteUtils;

@Component
@Order(value = 0)
@Slf4j
public class IgniteInit implements CommandLineRunner {

	@Resource
	private IgniteUtils igniteUtils;
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		log.info("start igint init.");
		initCache();
	}
	private void initCache() {
		// TODO Auto-generated method stub
		Ignite ignite = igniteUtils.getIgniteInstance();
		
		//init user trade cache
		CacheConfiguration<UserTradeInfoKey, UserTradeInfo> userTradeCfg = 
				new CacheConfiguration<UserTradeInfoKey, UserTradeInfo>();
		userTradeCfg.setName(Constant.CACHE_USER_TRADE);
		userTradeCfg.setIndexedTypes(UserTradeInfoKey.class, UserTradeInfo.class);
		ignite.getOrCreateCache(userTradeCfg);
		
		
	}

}
