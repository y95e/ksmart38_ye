package ksmart38.mybatis.controller;

import java.util.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ksmart38.mybatis.dao.MemberMapper;
import ksmart38.mybatis.domain.Goods;
import ksmart38.mybatis.domain.Member;
import ksmart38.mybatis.service.GoodsService;



@Controller
public class GoodsController {
	
	//1.필드 주입방식
		@Autowired
		private GoodsService goodsService;
		@Autowired
		private MemberMapper memberMapper;
		
		@PostConstruct
		//객체가 생성됐을때 실행해주세요 하는 어노테이션
		public void initialize() {
			System.out.println("=======================");
			System.out.println("MemberController bean 등록");
			System.out.println("=======================");
		}
	@PostMapping("/addGoods")
	public String addGoods(Goods goods) {
		
		goodsService.addGoods(goods);		
		
		return "redirect:/goodsList";
	}
	
	@GetMapping("/addGoods")
	public String addGoods(HttpSession session, Model model) {
		String memberLevel = (String) session.getAttribute("SLEVEL");
		if(memberLevel != null && "관리자".equals(memberLevel)) {
			System.out.println("확인");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("searchKey", "m_level");
			paramMap.put("searchValue", "판매자");
			System.out.println(paramMap.get("searchKey")+"-------------------------------------sdasd");
			System.out.println(paramMap.get("searchValue")+"=====================================asdsad");
			System.out.println(memberMapper.getMemberList(paramMap));
			List<Member> sellerList = memberMapper.getMemberList(paramMap);
			System.out.println(sellerList);
			model.addAttribute("sellerList", sellerList);
		}
		return "goods/addGoods";
	};
		
	@GetMapping("/goodsList")
	public String getGoodsList(Model model
							   ,@RequestParam(value="searchKey", required = false)String searchKey
							   ,@RequestParam(value = "searchValue", required = false)String searchValue) {
		List<Goods> goodsList = goodsService.getGoodsList(searchKey,searchValue);
		model.addAttribute("goodsList", goodsList);
		return "goods/goodsList";
	}
}
