package com.tagtheagency.alcoholicsrecovered;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecoveredErrorController implements ErrorController{

	@Override
	public String getErrorPath() {
		return "/error";
	}
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
//		return "login";
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	     
	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	     
	        switch (statusCode) {
	        case 404:
	        case 405:
	        case 403:
	        	return "error/404";
	        default:
	        	return "error/generic";
	        	
	        }
//	        if (statusCode == HttpStatus.NOT_FOUND.value()) {
//	            return "error/404";
//	        } else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//	            return "error/500";
//	        }
	    }
	    return "error/generic";
	}

}
