package ksmart38.mybatis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import ksmart38.mybatis.dao.MemberMapper;
import ksmart38.mybatis.domain.Member;

@Service
public class MemberService {
	
	private static final Logger log = LoggerFactory.getLogger(MemberService.class);

	
	/*
	 * // 1.DI 필드 주입방식
	 * 
	 * @Autowired private MemberMapper memberMapper;
	 */
	

	/*
	 * // 2.DI SETTER 메소드 주입방식 private MemberMapper memberMapper2;
	 * 
	 * @Autowired public void setMemberMapper(MemberMapper memberMapper) {
	 * this.memberMapper2 = memberMapper; }
	 */
	  
	
	  // 3.DI 생성자 메소드 주입방식
	  private final MemberMapper memberMapper;
	  
	  //3-1. spring framework 4.3 이후부터 @Autowired 쓰지않아도 주입 가능, 4.3 이하면 써줘야함. public
	  MemberService(MemberMapper memberMapper) { 
		  this.memberMapper = memberMapper;
	  }
		@PostConstruct
		//객체가 생성됐을때 실행해주세요 하는 어노테이션
	public void initialize() {
		log.debug("MemberService ::::::::{}","initialize()");
		System.out.println("=======================");			
		System.out.println("MemberService bean 등록");
		System.out.println("=======================");
	}
	
	public String login(String memberId, String memberPw) {
		String result = "로그인 성공 실패";
		if(memberId != null && !"".equals(memberId) &&
		   memberPw != null && !"".equals(memberPw)) {			
			Member member = memberMapper.getMemberInfoById(memberId);
			if(memberId.equals(member.getMemberId())) {
				if(memberPw.equals(member.getMemberPw())) {
					result = "로그인성공";
				}else {
					result = "비밀번호 불일치";
				}
			}
		}		
		return result;
	}
	
	public Map<String, Object> getLoginHistory(int currentPage) {
		
		// 페이지에 보여줄 행의 갯수
		int rowPerPage = 5;
		
		// login table 행의 시작점
		int startNum = 0;
		// 마지막페이지
		int lastPage = 0;
		//페이지 시작
		int starPageNum = 1;
		//페이지 마지막
		int endPageNum = 10;
		
		
		// 페이지 알고리즘(현재페이지 - 1)*페이지에 보여줄 행의 갯수 =>LIMIT ?,5 (물음표를 구하는 과정)
		startNum = (currentPage-1) * rowPerPage;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startNum", startNum);
		paramMap.put("rowPerPage", rowPerPage);
		
		List<Map<String, Object>> loginHistroy = memberMapper.getLoginHistory(paramMap);
		
		// 로그인 이력 행의 갯수 
		double loginHistoryCount = memberMapper.getLoginHistoryCount();
		
		//마지막 페이지 
		lastPage = (int) Math.ceil(loginHistoryCount/rowPerPage);
		
		//7페이지인 경우 동적페이지 번호 구성
		if(currentPage > 6) {
			starPageNum = currentPage - 5;
			endPageNum = currentPage + 4;
			
			if(endPageNum >= lastPage) {
				starPageNum = lastPage - 9;
				endPageNum = lastPage;
			}
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("loginHistroy", loginHistroy);
		resultMap.put("lastPage", lastPage);
		resultMap.put("startPageNum", starPageNum);
		resultMap.put("endPageNum", endPageNum);
		
		return resultMap;
	}
	
		
		
	public String removeMember(String memberId, String memberPw) {
		String result = "회원삭제 실패";
		
		Member member = memberMapper.getMemberInfoById(memberId);
		
		if(memberPw.equals(member.getMemberPw())){
			if(member.getMemberLevel()==2) {
				memberMapper.removeOrderBySellerId(memberId);
				memberMapper.removeGoods(memberId);
			}
			if(member.getMemberLevel() == 3) {
				memberMapper.removeOrder(memberId);
			}
			
			memberMapper.removeLogin(memberId);
			memberMapper.removeMember(memberId);
			
			result = "회원삭제완료";
		}
		return result;
	}
	
	public int modifyMember(Member member) {
		int result = memberMapper.modifyMember(member);
		return result;
	}
		
	public Member getMemberInfoById(String memberId) {
		return memberMapper.getMemberInfoById(memberId);
	}
		
	public int addMember(Member member) {
		int result = memberMapper.addMember(member);
		return result;
	}

	
	public List<Member> getMemberList(String searchKey, String searchValue){
		
		if(searchKey!=null & searchValue !=null) {
			if(searchKey.equals("memberId")) {
				searchKey = "m_id";
			}else if(searchKey.equals("memberLevel")) {
				searchKey = "level_name";
			}else if(searchKey.equals("memberName")) {
				searchKey = "m_name";
			}else if(searchKey.equals("memberEmail")) {
				searchKey = "m_email";
			}			
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("searchKey", searchKey);
		paramMap.put("searchValue", searchValue);
		List<Member> memberList = memberMapper.getMemberList(paramMap);
		if(memberList != null) {
			
			for(int i = 0 ; i<memberList.size(); i++) {
				int level = memberList.get(i).getMemberLevel();
				if(level == 1) {
					memberList.get(i).setMemberLevelName("관리자");
				}else if(level == 2) {
					memberList.get(i).setMemberLevelName("판매자");
				}else if(level ==3) {
					memberList.get(i).setMemberLevelName("구매자");
				}
			}		
		}
		
		return memberList;
	}
}
