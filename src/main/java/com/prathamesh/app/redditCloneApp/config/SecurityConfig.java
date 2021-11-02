package com.prathamesh.app.redditCloneApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.prathamesh.app.redditCloneApp.security.JwtAuthenticationFilter;

import org.springframework.security.config.BeanIds;

import lombok.AllArgsConstructor;

@EnableWebSecurity
@AllArgsConstructor
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	private final UserDetailsService userDetailsService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Override
	public void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.csrf().disable()
			.authorizeRequests()
			.antMatchers("/api/auth/**")
			.permitAll()
			.antMatchers(HttpMethod.GET, "/api/subreddit")
			.permitAll()
			.antMatchers(HttpMethod.GET, "/api/posts")
            .permitAll()
            .antMatchers(HttpMethod.GET, "/api/posts/**")
            .permitAll()
			.anyRequest()
			.authenticated();
		
		 httpSecurity.addFilterBefore(jwtAuthenticationFilter,
		  UsernamePasswordAuthenticationFilter.class);
		 
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService)
				.passwordEncoder(passwordEncoder());
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception{
		return super.authenticationManagerBean();
	}
}
