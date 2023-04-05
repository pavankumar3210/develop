package com.teluguskillhub.taskproject.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.teluguskillhub.taskproject.entity.Users;
import com.teluguskillhub.taskproject.exception.UserNotFound;
import com.teluguskillhub.taskproject.repository.UserRepository;
@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	
	  
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
     Users user = userRepository.findByEmail(email).orElseThrow(
    		 ()->new UserNotFound(String.format("User with email: %s is not found", email)));
     Set<String> roles = new HashSet<String>();
     roles.add("ROLE ADMIN");
		return new User(user.getEmail(),user.getPassword(), useAuthorities(roles));
	}
	
	private Collection<? extends GrantedAuthority> useAuthorities(Set<String> roles){
		return roles.stream().map(
				role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
	}

}
