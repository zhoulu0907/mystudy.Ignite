package my.study.ignite.runner.message_4_5;

import javax.annotation.Resource;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteMessaging;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import my.study.ignite.common.utils.IgniteUtils;

//@Component
@Order(4)
@Slf4j
public class MessageSender implements CommandLineRunner {

	@Resource
	private IgniteUtils igniteUtils;
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Ignite ignite = igniteUtils.getIgniteInstance();
				IgniteMessaging rmtMsg = ignite.message(ignite.cluster());
				
				for (int loop = 0; loop < 10; loop++) {
					rmtMsg.sendOrdered("HelloTopic", Integer.toString(loop), 0);
					log.info("Send msg: " + loop);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}).start();
	}

}
