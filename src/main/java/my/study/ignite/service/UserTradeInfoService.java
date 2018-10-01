package my.study.ignite.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.cache.Cache.Entry;

import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.cache.query.SqlQuery;
import org.apache.ignite.internal.processors.query.QueryField;
import org.springframework.stereotype.Service;

import my.study.ignite.common.Constant;
import my.study.ignite.common.bean.UserTradeInfo;
import my.study.ignite.common.bean.UserTradeInfoKey;
import my.study.ignite.common.utils.IgniteUtils;

@Service(value="UserTradeInfoService")
public class UserTradeInfoService {
	
	@Resource
	private IgniteUtils igniteUtils;
	
	public List<UserTradeInfo> getTotalUserTradeInfos() {
		List<UserTradeInfo> userTradeInfos = new ArrayList<UserTradeInfo>();
		
		SqlQuery<UserTradeInfoKey, UserTradeInfo> sql = new SqlQuery<>(UserTradeInfo.class, "login != ?");
		IgniteCache<UserTradeInfoKey, UserTradeInfo> cache = igniteUtils.getIgniteInstance().cache(Constant.CACHE_USER_TRADE);
		try(QueryCursor<Entry<UserTradeInfoKey, UserTradeInfo>> cursor = cache.query(sql.setArgs(""))){
			for (Entry<UserTradeInfoKey, UserTradeInfo> e : cursor) {
				userTradeInfos.add(e.getValue());
			}
		}
		
		return userTradeInfos;
		
	}

	public List<UserTradeInfo> getUserTradeInfos(String login) {
		List<UserTradeInfo> userTradeInfos = new ArrayList<UserTradeInfo>();
		
		SqlQuery<UserTradeInfoKey, UserTradeInfo> sql = new SqlQuery<>(UserTradeInfo.class, "login = ?");
		IgniteCache<UserTradeInfoKey, UserTradeInfo> cache = igniteUtils.getIgniteInstance().cache(Constant.CACHE_USER_TRADE);
		try(QueryCursor<Entry<UserTradeInfoKey, UserTradeInfo>> cursor = cache.query(sql.setArgs(login))){
			for (Entry<UserTradeInfoKey, UserTradeInfo> e : cursor) {
				userTradeInfos.add(e.getValue());
			}
		}
		
		return userTradeInfos;
		
	}

	public void updateUserTradeInfos(Map<UserTradeInfoKey, UserTradeInfo> userTradeInfoMap) {
		// TODO Auto-generated method stub
		IgniteCache<UserTradeInfoKey, UserTradeInfo> cache = igniteUtils.getIgniteInstance().cache(Constant.CACHE_USER_TRADE);
		cache.putAll(userTradeInfoMap);		
	}

	public List<String> getLogins() {
		// TODO Auto-generated method stub
		List<String> logins = new ArrayList<>();
		SqlFieldsQuery sql = new SqlFieldsQuery("select distinct login from UserTradeInfo");
		IgniteCache<UserTradeInfoKey, UserTradeInfo> cache = igniteUtils.getIgniteInstance().cache(Constant.CACHE_USER_TRADE);
		try(QueryCursor<List<?>> cursor = cache.query(sql)){
			for (List<?> e : cursor) {
				logins.add((String) e.get(0));
			}
		}
		return logins;
	}
}
