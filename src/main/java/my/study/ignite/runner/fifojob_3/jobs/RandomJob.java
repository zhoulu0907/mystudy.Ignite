package my.study.ignite.runner.fifojob_3.jobs;

import org.apache.ignite.lang.IgniteRunnable;

import lombok.extern.log4j.Log4j;

@Log4j
public class RandomJob implements IgniteRunnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int jobId;
	private int sleepTime;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(sleepTime * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("JobId: " + jobId + ", Sleep: " + sleepTime + " ms.");
	}
	
	public RandomJob(int jobId, int sleepTime) {
		this.jobId = jobId;
		this.sleepTime = sleepTime;
	}

}
