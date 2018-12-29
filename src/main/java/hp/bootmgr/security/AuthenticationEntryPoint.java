package hp.bootmgr.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

public class AuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

	public AuthenticationEntryPoint(String loginUrl) {
		super(loginUrl);
	}
	
	/**
	 * Since we are loading fragments via AJAX, login page will be loaded into HTML element (mostly div tag) in case
	 * of invalidated or expired session. To avoid such situation, treat request for fragments specially and return 
	 * 403 error code instead of redirecting to log in page. Client will catch this error code and will act accordingly
	 * (mostly it will redirect to login page.)   
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		if(request.getRequestURI().contains("fragment/"))
			response.sendError(403, "Forbidden");
		else
			super.commence(request, response, exception);
	}

}
