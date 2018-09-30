package my.study.ignite.runner.task_6.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.ignite.IgniteException;
import org.apache.ignite.compute.ComputeJob;
import org.apache.ignite.compute.ComputeJobResult;
import org.apache.ignite.compute.ComputeTaskSplitAdapter;

import lombok.extern.slf4j.Slf4j;
import my.study.ignite.common.bean.UserTradeInfo;
import my.study.ignite.common.bean.UserTradeInfoKey;

@Slf4j
public class ProfitTransTask extends 
	ComputeTaskSplitAdapter<Map<UserTradeInfoKey, UserTradeInfo>, Map<UserTradeInfoKey, UserTradeInfo>> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Random rand = new Random();

	@Override
	public Map<UserTradeInfoKey, UserTradeInfo> reduce(List<ComputeJobResult> results) throws IgniteException {
		// TODO Auto-generated method stub
		Map<UserTradeInfoKey, UserTradeInfo> userTradeInfoMap = new HashMap<UserTradeInfoKey, UserTradeInfo>();
		for (ComputeJobResult rst : results) {
			UserTradeInfo userTradeInfo = rst.getData();
			userTradeInfoMap.put(userTradeInfo.getId(), userTradeInfo);
		}
		return userTradeInfoMap;
	}

	@Override
	protected Collection<? extends ComputeJob> split(int gridSize, Map<UserTradeInfoKey, UserTradeInfo> arg)
			throws IgniteException {
		// TODO Auto-generated method stub
		List<ComputeJob> jobs = new ArrayList<ComputeJob>(arg.size());
		
		for (Entry<UserTradeInfoKey, UserTradeInfo> e : arg.entrySet()) {
			jobs.add(new ComputeJob() {
				
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public Object execute() throws IgniteException {
					// TODO Auto-generated method stub
					UserTradeInfo userTradeInfo = e.getValue();
					userTradeInfo.setProfit(userTradeInfo.getProfit() * 10);
					int sleepTime = rand.nextInt(10);
					try {
						Thread.sleep(sleepTime * 1000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					log.info("finished deal: " + userTradeInfo.getDeal() + ", use: " + sleepTime + " s.");
					return userTradeInfo;
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
