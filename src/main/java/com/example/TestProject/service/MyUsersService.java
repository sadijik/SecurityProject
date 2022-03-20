package com.example.TestProject.service;


import com.example.TestProject.entity.Users;
import com.example.TestProject.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class MyUsersService implements UserDetailsService {
	private final UsersRepository usersRepository;

	@Autowired
	public MyUsersService(UsersRepository usersRepository) {
		this.usersRepository = usersRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users byUsername = usersRepository.findByUsername(username);

		if (byUsername == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return byUsername;
	}
}
