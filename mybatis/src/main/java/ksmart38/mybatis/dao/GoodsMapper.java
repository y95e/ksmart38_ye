package ksmart38.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import ksmart38.mybatis.domain.Goods;

@Mapper
public interface GoodsMapper {
	
	public int addGoods(Goods goods);

	public List<Goods> getGoodsList(String searchKey, String searchValue);
}
