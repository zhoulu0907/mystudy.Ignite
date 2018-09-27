package my.study.ignite.runner.message_4_5;

import java.util.UUID;

import javax.annotation.Resource;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteMessaging;
import org.apache.ignite.lang.IgniteBiPredicate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import my.study.ignite.common.utils.IgniteUtils;

//@Component
@Order(5)
@Slf4j
public class MessageReceiver implements CommandLineRunner {

	@Resource
	private IgniteUtils igniteUtils;
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		Ignite ignite = igniteUtils.getIgniteInstance();
		IgniteMessaging locMsg = ignite.message(ignite.cluster());
		locMsg.remoteListen("HelloTopic", (nodeId, msg)->{
			log.info("Receive ordered message:" + msg + ", from: " + nodeId);
			return true;
		});
	}

}
