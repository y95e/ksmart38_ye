package ksmart38.mybatis.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LoginInterceptor implements HandlerInterceptor{

	
	private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		String sessionId    = (String) session.getAttribute("SID");
		String sessionLevel = (String) session.getAttribute("SLEVEL");
		String requestUri   = request.getRequestURI();
		
		log.info("LoginInterceptor --------------------------------- START");
		log.info("sessionId           :::::::::::::::{}", sessionId);
		log.info("sessionLevel        :::::::::::::::{}", sessionLevel);
		
		if(sessionId == null) {
			response.sendRedirect("/login");
			return false;
		}
		
		if("판매자".equals(sessionLevel)) {
			if(requestUri.indexOf("memberList")   >-1 ||
			   requestUri.indexOf("goodsList")    >-1 ||
			   requestUri.indexOf("addMember")    >-1 ||
			   requestUri.indexOf("modifyMember") >-1 ||
			   requestUri.indexOf("deleteMember") >-1 ||
			   requestUri.indexOf("moidfyGoods")  >-1 ||
			   requestUri.indexOf("deleteGdds")   >-1 
			   ) {
				
				//가정: 1.아이디로로그인처리가 완료된후 (memberController ->로그인 시 세션에 메뉴list등록)
				//가정: 2.세션에 등록된 메뉴리스트 -> List변환
				List<String> list = new ArrayList<String>();
				list.add("/addMember");
				
				log.info("test :::{}", list.contains(requestUri));
				
				//현재 요청받은 주소와 사용자 접근 주소와 일치하지않을 때
				if(!list.contains(requestUri)) {
					response.sendRedirect("/");
					return false;
				}
			
			}
		}
		
		if("구매자".equals(sessionLevel)) {
			if("/addGoods".equals(requestUri)) {
				response.sendRedirect("/");
				return false;
			}else if("/goodsList".equals(requestUri)) {
				response.sendRedirect("/");
				return false;
			}
		}
		
		
		
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		log.info("LoginInterceptor --------------------------------- END");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
