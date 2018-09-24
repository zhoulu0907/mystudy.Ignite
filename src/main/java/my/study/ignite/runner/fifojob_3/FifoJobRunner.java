package my.study.ignite.runner.fifojob_3;

import java.util.Random;

import javax.annotation.Resource;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.lang.IgniteRunnable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.log4j.Log4j;
import my.study.ignite.common.utils.IgniteUtils;
import my.study.ignite.runner.fifojob_3.jobs.PrioritiesJob;
import my.study.ignite.runner.fifojob_3.jobs.RandomJob;

@Component
@Order(2)
@Log4j
public class FifoJobRunner implements CommandLineRunner {

	@Resource
	private IgniteUtils igniteUtils;

	private Random rand = new Random();
	
	private int jobId = 0;
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub

				for (int loop = 0; loop < 10; loop++) {
					Ignite ignite = igniteUtils.getIgniteInstance();
					int sleepTime = rand.nextInt(10);
					jobId++;
					ignite.compute().runAsync(new RandomJob(jobId, sleepTime));
//					ignite.compute().executeAsync(new PrioritiesJob(sleepTime, loop, sleepTime), "");
				}
			}
		}).start();
	}

}
