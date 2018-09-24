/**
 * 
 */
package my.study.ignite.runner.fifojob_3.jobs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ignite.IgniteException;
import org.apache.ignite.compute.ComputeJob;
import org.apache.ignite.compute.ComputeJobResult;
import org.apache.ignite.compute.ComputeTaskSession;
import org.apache.ignite.compute.ComputeTaskSplitAdapter;
import org.apache.ignite.resources.TaskSessionResource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Mloong
 *
 */
@Slf4j
public class PrioritiesJob extends ComputeTaskSplitAdapter<Object, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@TaskSessionResource
	private ComputeTaskSession taskSes = null; 
	
	private int priority;
	private int jobId;
	private int sleepTime;
	
	public PrioritiesJob(int priority, int jobId, int sleepTime) {
		this.priority = priority;
		this.jobId = jobId;
		this.sleepTime = sleepTime;
	}

	@Override
	public Object reduce(List<ComputeJobResult> results) throws IgniteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection<? extends ComputeJob> split(int gridSize, Object arg) throws IgniteException {
		// TODO Auto-generated method stub
//		this.taskSes.setAttribute("grid.task.priority", this.priority);
		
		List<ComputeJob> jobs = new ArrayList<>(gridSize);
		
		for (int i = 0; i < gridSize; i++) {
			jobs.add(new ComputeJob() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public Object execute() throws IgniteException {
					// TODO Auto-generated method stub
					log.info("JobId: " + jobId 
							+ ", Sleep: " + sleepTime + " ms.");
					return true;
				}
				
				@Override
				public void cancel() {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		return jobs;
	}

}
