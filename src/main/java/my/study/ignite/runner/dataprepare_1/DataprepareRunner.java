package my.study.ignite.runner.dataprepare_1;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteQueue;
import org.apache.ignite.configuration.CollectionConfiguration;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;

import lombok.extern.slf4j.Slf4j;
import my.study.dataprepare.bean.ForexTrade;
import my.study.dataprepare.mapper.ForexTradeMapper;
import my.study.ignite.common.utils.IgniteUtils;

//@Component
@Order(value=1)
@Slf4j
public class DataprepareRunner implements CommandLineRunner {

	@Resource
	private ForexTradeMapper forexTradeMapper;
	@Resource
	private IgniteUtils igniteUtils;
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		log.info("Start DataprepareRunner.");
		Ignite ignite = igniteUtils.getIgniteInstance();
		CollectionConfiguration colCfg = new CollectionConfiguration();
		colCfg.setCollocated(false);
		IgniteQueue<ForexTrade> forexTradeQueue = ignite.queue("forex-trade", 0, colCfg);

		List<ForexTrade> forexTradeList = forexTradeMapper.find(0, 10);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (ForexTrade forexTrade : forexTradeList) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					forexTradeQueue.put(forexTrade);
					log.info("Put trade: " + forexTrade.getDeal());
				}
			}
		}).start();
		
	}

}
