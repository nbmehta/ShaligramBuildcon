/*
 * Copyright 2016 Harsh Panchal <panchal.harsh18@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hp.bootmgr.web.services.authentication;

import hp.bootmgr.authentication.provider.UserDetailService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

	private static Logger logger = Logger.getLogger(AuthenticationTokenProcessingFilter.class);

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;

        // Exclude login URL
        if(req.getRequestURI().endsWith("/api/authenticate")) {
            chain.doFilter(req, response);
            return;
        }

        // Client must send token in header
        String authHeader = req.getHeader(Config.AUTH_TOKEN_HEADER_NAME);
        if (authHeader == null) {
            logger.error("[REST]: Authentication header was null...");
            throw new ServletException("Missing or invalid Authorization header.");
        }

        // Parse token, fetch user and reload Security Context
        try {
            String SECRET_KEY = getServletContext().getInitParameter("API_SECRET_KEY");
            Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authHeader);
            Claims claim = claims.getBody();
            String userName = claim.getSubject();
            logger.debug("[REST]: Token of user -> " + userName + " expires: " + claim.getExpiration());
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, userDetailService.loadUserByUsername(userName).getPassword());
            token.setDetails(new WebAuthenticationDetails(req));
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (SignatureException e) {
            logger.debug("[REST]: Invalid token");
            throw new ServletException("Invalid token.");
        }
		chain.doFilter(req, response);

        // clear security context now because we are going for Stateless Web Services
        SecurityContextHolder.getContext().setAuthentication(null);
	}
}
