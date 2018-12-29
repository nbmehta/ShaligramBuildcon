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
import hp.bootmgr.web.services.ResultCodes;
import hp.bootmgr.web.services.responses.Result;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AuthenticationManagementController {

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private ServletContext servletContext;

    private static Logger logger = Logger.getLogger(AuthenticationManagementController.class);

    /**
     * Validates credentials provided by REST Client & if valid, provides token to client
     * which it will use for further communication
     */
    @RequestMapping(value = "/api/authenticate", method = RequestMethod.POST)
    public @ResponseBody
    Result doLogIn(@RequestParam("BulkData") String bulkData, HttpServletResponse response) {

    	ObjectMapper mapper = new ObjectMapper();
    	JsonNode actualObj = null;
		try {
			actualObj = mapper.readTree(bulkData);
		} catch (IOException e1) {
			e1.printStackTrace();
			return new Result("Invalid Request", ResultCodes.LOGIN_FAILURE);
		}
    	
    	String userName = actualObj.get("userName").asText();
    	String password = actualObj.get("password").asText();
    	
    	logger.debug("[REST]: Attempting login for -> " + userName);
        UserDetails details = userDetailService.loadUserByUsername(userName);
        // validate password
        if (details != null && !details.getPassword().equals(password)) {
            logger.debug("[REST]: Invalid username/password");
            try {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid username/password");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new Result("Invalid username or password", ResultCodes.LOGIN_FAILURE);
        }
        // Generate token. ATM, use only username
        String generatedToken = Jwts.builder().setSubject(userName)
                .setIssuedAt(new Date())
                // set token expiration time
                .setExpiration(new Date(System.currentTimeMillis() + Config.TOKEN_EXPIRY_PERIOD))
                .signWith(SignatureAlgorithm.HS256, servletContext.getInitParameter("API_SECRET_KEY"))
                .compact();
        // provide token to user in form of a Http Header
        response.addHeader(Config.AUTH_TOKEN_HEADER_NAME, generatedToken);
        return new Result("Login Success", ResultCodes.LOGIN_SUCCESS_TOKEN_GENERATED);
    }
}
