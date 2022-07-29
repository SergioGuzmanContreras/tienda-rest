package mx.com.openwebinars.tienda.component;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;


@Component
public class ErrorAttributes extends DefaultErrorAttributes{

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		var allErrorAttributes = super.getErrorAttributes(webRequest, options);
		var errorAttibutes = new HashMap<String, Object>();		
		var statusCode = (int) allErrorAttributes.get("status");
		errorAttibutes.put("status", HttpStatus.valueOf(statusCode));
		errorAttibutes.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss")));		
		var throwable = getError(webRequest);
		if(throwable instanceof ResponseStatusException) {
			var response = (ResponseStatusException) throwable;
			errorAttibutes.put("message", response.getReason() == null ? "" : response.getReason());
		} else 
			if( throwable.getCause() != null)
				errorAttibutes.put("message", throwable.getCause().getMessage() == null ? "" : throwable.getCause().getMessage());

		return errorAttibutes;
	}
	
}
