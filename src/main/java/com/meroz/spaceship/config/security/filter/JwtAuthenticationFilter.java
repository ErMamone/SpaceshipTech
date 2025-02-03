package com.meroz.spaceship.config.security.filter;

import com.meroz.spaceship.config.security.service.JwtService;
import com.meroz.spaceship.entities.User;
import com.meroz.spaceship.exception.GeneralSecurityException;
import com.meroz.spaceship.service.UserService;
import com.meroz.spaceship.utils.enums.ErrorsEnum;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;

	private final UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String authorizationHeader = request.getHeader("Authorization");
		if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}

		String jwt = authorizationHeader.split(" ")[1];

		String username = jwtService.extractUsername(jwt);

		User user = userService.findOneByUsername(username)
				.orElseThrow(() -> new GeneralSecurityException(ErrorsEnum.NOT_FOUND, new Object[]{username}));

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				username, null, user.getAuthorities()
		);
		
		authToken.setDetails(new WebAuthenticationDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authToken);

		filterChain.doFilter(request, response);
	}
}
