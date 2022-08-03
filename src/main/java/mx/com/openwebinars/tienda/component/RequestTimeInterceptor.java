package mx.com.openwebinars.tienda.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("requestTimeInterceptor")
public class RequestTimeInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object hadler) throws Exception{
		request.setAttribute("startTime", System.currentTimeMillis());
		return true;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
		var startTime = (Long) request.getAttribute("startTime");
		log.info("<== ### Request url: {} <-> TOTAL TIME: {} msg", request.getRequestURL(), (System.currentTimeMillis() - startTime));
	}

}