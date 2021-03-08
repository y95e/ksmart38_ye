package ksmart38.mybatis.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksmart38.mybatis.dao.GoodsMapper;
import ksmart38.mybatis.domain.Goods;

@Service
public class GoodsService {
	
	@Autowired
	private GoodsMapper goodsMpapper;
	
	public int addGoods(Goods goods) {		
		return goodsMpapper.addGoods(goods);
	}
	
	public List<Goods> getGoodsList(String searchKey, String searchValue){
		
		if(searchKey!=null & searchValue != null) {
			if(searchKey.equals("goodsCode")) {
				searchKey = "g_code";
			}else if(searchKey.equals("goodsName")) {
				searchKey = "g_name";
			}else if(searchKey.equals("goodsPrice")) {
				searchKey = "g_price";
			}else if(searchKey.equals("goodsSellerId")) {
				searchKey = "g_seller_id";
			}
		}
		
		List<Goods> goodsList = goodsMpapper.getGoodsList(searchKey,searchValue);
		
		return goodsList;
	}
}
