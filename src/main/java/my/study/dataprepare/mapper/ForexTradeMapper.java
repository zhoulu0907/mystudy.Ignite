package my.study.dataprepare.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import my.study.dataprepare.bean.ForexTrade;

@Mapper
public interface ForexTradeMapper {
	public List<ForexTrade> findAll();

	public List<ForexTrade> find(Integer startIndex, Integer size);
}
