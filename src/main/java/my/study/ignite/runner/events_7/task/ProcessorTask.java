package my.study.ignite.runner.events_7.task;

import java.util.Collection;
import java.util.List;

import org.apache.ignite.IgniteException;
import org.apache.ignite.compute.ComputeJob;
import org.apache.ignite.compute.ComputeJobResult;
import org.apache.ignite.compute.ComputeTaskSplitAdapter;

public class ProcessorTask extends ComputeTaskSplitAdapter<String, String> {

	@Override
	public String reduce(List<ComputeJobResult> results) throws IgniteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection<? extends ComputeJob> split(int gridSize, String arg) throws IgniteException {
		// TODO Auto-generated method stub
		return null;
	}

}
