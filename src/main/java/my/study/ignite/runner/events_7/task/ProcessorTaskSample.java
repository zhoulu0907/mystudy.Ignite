package my.study.ignite.runner.events_7.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ignite.IgniteException;
import org.apache.ignite.compute.ComputeJob;
import org.apache.ignite.compute.ComputeJobResult;
import org.apache.ignite.compute.ComputeTaskSplitAdapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProcessorTaskSample extends ComputeTaskSplitAdapter<String, String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String reduce(List<ComputeJobResult> results) throws IgniteException {
		// TODO Auto-generated method stub
		for (ComputeJobResult result : results) {
			Boolean rst = (Boolean)result.getData();
			if (rst == false) {
				return "Failed";
			}
		}
		return "Success";
	}

	@Override
	protected Collection<? extends ComputeJob> split(int gridSize, String arg) throws IgniteException {
		// TODO Auto-generated method stub
		List<ComputeJob> jobs = new ArrayList<ComputeJob>(1);
		jobs.add(new ComputeJob() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Object execute() throws IgniteException {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(5 * 1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				log.info("finished: " + arg);
				return true;
			}
			
			@Override
			public void cancel() {
				// TODO Auto-generated method stub
				
			}
		});
		return jobs;
	}

}
