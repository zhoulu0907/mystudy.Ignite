package my.study.ignite.common.utils;

import org.apache.ignite.Ignite;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class IgniteUtils implements ApplicationContextAware{

	private Ignite ignite;
	@Override
	public void setApplicationContext(ApplicationContext cnt) throws BeansException {
		// TODO Auto-generated method stub
		Ignite ignite = cnt.getBean(Ignite.class);
		this.ignite = ignite;
	}
	
	public Ignite getIgniteInstance() {
		return this.ignite;
	}
}
