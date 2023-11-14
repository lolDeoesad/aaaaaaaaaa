package com.project.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.project.bank.filter.JwtFilter;
import com.project.bank.security.AuthEntryPoint;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Autowired
	private AuthEntryPoint authEntryPoint;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.cors();
		
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests()

			.antMatchers(HttpMethod.GET, 	"/", "/hasUser").permitAll()
			.antMatchers(HttpMethod.POST, 	"/login").permitAll()

			// WEBUSER, CUSTOMER, MANAGER, ADMIN

			.antMatchers(HttpMethod.GET, 	"/user").authenticated()
			.antMatchers(HttpMethod.POST, 	"/user").permitAll()
			.antMatchers(HttpMethod.PUT, 	"/user").authenticated()
			.antMatchers(HttpMethod.DELETE, "/user").authenticated()

			.antMatchers(HttpMethod.GET, 	"/approval").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST, 	"/approval").hasRole("ADMIN")

			.antMatchers(HttpMethod.GET, 	"/account/*").hasAnyRole("CUSTOMER", "MANAGER")
			.antMatchers(HttpMethod.POST, 	"/account").hasRole("CUSTOMER")
			.antMatchers(HttpMethod.POST, 	"/account/*").hasAnyRole("MANAGER")
			.antMatchers(HttpMethod.DELETE, "/account/*").hasAnyRole("CUSTOMER")
			.antMatchers(HttpMethod.DELETE, "/account/*/*").hasAnyRole("MANAGER")

			.antMatchers(HttpMethod.GET, 	"/transaction/*").hasAnyRole("CUSTOMER", "MANAGER")
			.antMatchers(HttpMethod.POST, 	"/transaction/*").hasAnyRole("CUSTOMER", "MANAGER")
			.antMatchers(HttpMethod.PUT, 	"/transaction/*").hasAnyRole("CUSTOMER")

			.antMatchers(HttpMethod.GET, 	"/qna").permitAll()
			.antMatchers(HttpMethod.GET, 	"/qna/*").hasRole("ADMIN")
			.antMatchers(HttpMethod.POST, 	"/qna").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, 	"/qna/*").hasRole("ADMIN")
			.antMatchers(HttpMethod.DELETE, "/qna/*").hasRole("ADMIN")

//			.anyRequest().authenticated()
			.anyRequest().denyAll()
			
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(authEntryPoint)
			.and()
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	@Bean
	CorsConfigurationSource configurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("http://localhost:3000");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}