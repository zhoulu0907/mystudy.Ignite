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

/**
 * @author Administrator
 * @验证结论：FifoQueueCollisionSpi权限很高，直接限定所有job，无论是同步线程，异步线程，自定义线程的所有线程数。如果设置为1，所有线程都要以FIFO执行。
 */
//@Component
@Order(3)
public class FifoJobRunner implements CommandLineRunner {

	@Resource
	private IgniteUtils igniteUtils;

	private Random rand = new Random();
	
	private int AsynJobId = 0;
	private int synJobId = 0;
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
					synJobId++;
					ignite.compute().run(new RandomJob("SynJob", synJobId, sleepTime));
//					ignite.compute().executeAsync(new PrioritiesJob(sleepTime, loop, sleepTime), "");
				}
			}
			
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub

				for (int loop = 0; loop < 10; loop++) {
					Ignite ignite = igniteUtils.getIgniteInstance();
					int sleepTime = rand.nextInt(10);
					AsynJobId++;
					ignite.compute().runAsync(new RandomJob("FIFO", AsynJobId, sleepTime));
//					ignite.compute().executeAsync(new PrioritiesJob(sleepTime, loop, sleepTime), "");
				}
			}
		}).start();
	}

}
