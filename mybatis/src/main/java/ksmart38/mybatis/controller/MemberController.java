package ksmart38.mybatis.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ksmart38.mybatis.domain.Member;
import ksmart38.mybatis.service.MemberService;

@Controller
public class MemberController {
	
	//1.필드 주입방식
	@Autowired
	private MemberService memberService;
	
	@PostConstruct
	//객체가 생성됐을때 실행해주세요 하는 어노테이션
	public void initialize() {
		System.out.println("=======================");
		System.out.println("MemberController bean 등록");
		System.out.println("=======================");
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	@PostMapping("/login")
	public String login(@RequestParam(value="memberId", required = false)String memberId
					   ,@RequestParam(value="memberPw", required = false)String memberPw
					   ,HttpSession session) {
		if(memberId != null && !"".equals(memberId) &&
		   memberPw != null && !"".equals(memberPw)) {
			String result = memberService.login(memberId, memberPw);
			if(result.equals("로그인성공")) {
				Member member = memberService.getMemberInfoById(memberId);
				session.setAttribute("SID", member.getMemberId());
				String SLEVEL = "일반회원";		
				if(member.getMemberLevel() == 1) {
					SLEVEL = "관리자";
				}else if(member.getMemberLevel() == 2) {
					SLEVEL = "판매자";
				}else if(member.getMemberLevel() == 3) {
					SLEVEL = "구매자";
				}
				session.setAttribute("SLEVEL", SLEVEL);
				
				session.setAttribute("SNAME", member.getMemberName());
			}
			
			return "redirect:/";	
			
		}
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login/login";
	}
	
	@PostMapping("/removeMember")
	public String removeMember(@RequestParam(value="memberId", required = false) String memberId
			  				  ,@RequestParam(value="memberPw", required = false) String memberPw
			  				  ,RedirectAttributes redirectAttr) {
		
		String result = memberService.removeMember(memberId, memberPw);
		
		System.out.println(result);
		
		if("회원삭제 실패".equals(result)) {
			redirectAttr.addAttribute("memberId", memberId);
			redirectAttr.addAttribute("result", result);
			return "redirect:/removeMember";
		}
		return "redirect:/memberList";
	}
	
	
	@GetMapping("/removeMember")
	public String removeMember(Model model, 
							   @RequestParam(value="memberId", required = false)String memberId,
							   @RequestParam(value="result", required = false)String result){
		model.addAttribute("memberId", memberId);
		model.addAttribute("result", result);
		
		return "member/removeMember";
	}		
	
	@PostMapping("/modifyMember")
	public String modifyMember(Member member) {
		memberService.modifyMember(member);
		return "redirect:/memberList";
	}
	
	@GetMapping("/modifyMember")
	public String modifyMember(Model model, @RequestParam(name="memberId", required = false) String memberId) {
		System.out.println("==============================");
		System.out.println("화면에서 입력받은 값->"+ memberId);
		System.out.println("==============================");
		
		Member member = memberService.getMemberInfoById(memberId);
		System.out.println("회원정보 조회" + member);
		
		model.addAttribute("member", member);
		
		return "member/modifyMember";
	}
	
	@RequestMapping(value = "/idCheck", method = RequestMethod.POST)
	public @ResponseBody boolean idCheck(@RequestParam(value = "memberId", required = false) String memberId) {
		
		boolean checkResult = false;
		
		if(memberId != null && !"".equals(memberId)) {
			Member member = memberService.getMemberInfoById(memberId);
			if(member != null) {
				checkResult = true;
			}
		}
		
		System.out.println(memberId +"<--MemberController.java");		
		return checkResult;
	}
	
	@PostMapping("/addMember")
	public String addMember(Member member) {
		
		System.out.println("==============================");
		System.out.println("커맨드객체를 통해 화면에서 입력받은 값->"+member);
		System.out.println("==============================");
		
		if(member != null && !"".equals(member.getMemberId())) {
			//회원등록
			memberService.addMember(member);			
		}
				
		return "redirect:/memberList";
	}
	
	@RequestMapping(value="/addMember", method = RequestMethod.GET)
	public String addMember() {
		return "member/addMember";
	}
	
	

	@GetMapping("/memberList")
	public String getMemberList(Model model
								,@RequestParam(value="searchKey", required = false)String searchKey
								,@RequestParam(value="searchValue", required = false)String searchValue) {
		
		System.out.println("===============================memberList");
		System.out.println(searchKey);
		System.out.println(searchValue);
		System.out.println("=========================================");
		
		
		List<Member> memberList = memberService.getMemberList(searchKey, searchValue);
		model.addAttribute("memberList", memberList);
		
		
		return "member/memberList";
	}
}
