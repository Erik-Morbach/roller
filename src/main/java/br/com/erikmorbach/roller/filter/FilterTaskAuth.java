package br.com.erikmorbach.roller.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.erikmorbach.roller.user.IUserRepository;
import br.com.erikmorbach.roller.user.UserModel;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter{

	@Autowired
	private IUserRepository userRepository;

	private String[] getCredentials(String auth){
		if(auth==null || auth.length()==0) {
			return null;
		}
		var authEncoded = auth.substring(auth.indexOf(' ')+1);
		byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
		var authStr = new String(authDecoded);

		return authStr.split(":");
	}

	private boolean executeAuth(UserModel user, String[] credentials)
			throws ServletException, IOException {
		if(user!=null){
			var passVerify = BCrypt.verifyer().verify(credentials[1].toCharArray(), user.getPassword());
			return passVerify.verified;
		}
		return false;
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
			
		var servletPath = request.getServletPath();
		if(servletPath.startsWith("/task/")){
			var auth = request.getHeader("Authorization");
			var credentials = this.getCredentials(auth);
			var user = this.userRepository.findByUsername(credentials[0]);
			if(!this.executeAuth(user, credentials)){
				response.sendError(401);
				return;
			}
			System.out.println(user.getId());
			request.setAttribute("userId", user.getId());
		}
		filterChain.doFilter(request, response);
	}
}
