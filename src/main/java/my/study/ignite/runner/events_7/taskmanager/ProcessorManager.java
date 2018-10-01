package my.study.ignite.runner.events_7.taskmanager;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import my.study.ignite.runner.events_7.processors.Processor;

@Component(value="ProcessorManager")
public class ProcessorManager implements ApplicationContextAware{
	
	private Map<String, Processor> processorMap;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		this.processorMap = applicationContext.getBeansOfType(Processor.class);
	}

	public Processor get(String processorName) {
		// TODO Auto-generated method stub
		return processorMap.get(processorName);
	}

}
