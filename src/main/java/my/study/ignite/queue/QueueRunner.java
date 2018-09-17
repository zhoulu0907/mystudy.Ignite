package my.study.ignite.queue;

import javax.annotation.Resource;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteQueue;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import my.study.dataprepare.bean.ForexTrade;
import my.study.ignite.common.utils.IgniteUtils;

@Component
@Order(value=2)
@Slf4j
public class QueueRunner implements CommandLineRunner {

	@Resource
	private IgniteUtils igniteUtils;
	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		log.info("Start QueueRunner.");
		Ignite ignite = igniteUtils.getIgniteInstance();
		IgniteQueue<ForexTrade> forexTradeQueue = ignite.queue("forex-trade", 0, null);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ForexTrade forexTrade = forexTradeQueue.take();
					log.info("Get forexTrade: " + forexTrade.getDeal());
				}
			}
		}).start();
	}

}
