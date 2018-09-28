package my.study.ignite.common.utils;

import org.apache.ignite.cluster.ClusterNode;
import org.apache.ignite.lang.IgnitePredicate;

public class NodeFilter implements IgnitePredicate<ClusterNode> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String domain;
	
	public NodeFilter(String domain) {
		// TODO Auto-generated constructor stub
		this.domain = domain;
	}

	@Override
	public boolean apply(ClusterNode e) {
		// TODO Auto-generated method stub
		return e.attribute("DOMAIN").equals(domain);
	}

}
