package my.study.ignite.runner.events_7.task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.compute.ComputeJob;
import org.apache.ignite.compute.ComputeJobResult;
import org.apache.ignite.compute.ComputeTaskSplitAdapter;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.resources.SpringResource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.mysql.fabric.xmlrpc.base.Array;

import lombok.extern.slf4j.Slf4j;
import my.study.ignite.runner.events_7.processors.Processor;
import my.study.ignite.runner.events_7.taskmanager.ProcessorManager;

@Slf4j
public class ProcessorTask extends ComputeTaskSplitAdapter<String, String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SpringResource(resourceName="ProcessorManager")
	private transient ProcessorManager processorManager;
	
	@Override
	public String reduce(List<ComputeJobResult> results) throws IgniteException {
		// TODO Auto-generated method stub
//		for (ComputeJobResult result : results) {
//			Boolean rst = (Boolean)result.getData();
//			if (rst == false) {
//				return "Failed";
//			}
//		}
		return "Success";
	}

	@Override
	protected Collection<? extends ComputeJob> split(int gridSize, String processorName) throws IgniteException {
		// TODO Auto-generated method stub
		List<ComputeJob> jobs = new ArrayList<>(1);
		
		
		jobs.add(new ComputeJob() {
			
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Object execute() throws IgniteException {
				// TODO Auto-generated method stub
				log.info("start job: " + processorName);
				processorManager.get(processorName).onEvent();
				log.info("finish job: " + processorName);
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
