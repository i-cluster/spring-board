package com.board.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {

	// private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// URI 호출 시 컨트롤러에 접근 전 실행
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {		
		log.debug("==============================");
		log.debug("============ BEGIN ============");
		log.debug("Request URI =======> " + request.getRequestURI());
		
		return true;
	}
	
	// 컨트롤러 경유 후 화면 접근 전 실행
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.debug("============= END =============");
		log.debug("==============================");
	}
}
