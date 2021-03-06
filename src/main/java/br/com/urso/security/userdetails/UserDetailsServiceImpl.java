package br.com.urso.security.userdetails;


import br.com.urso.user.entity.User;
import br.com.urso.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user;
		if(username.contains("@")){
		user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + username));
		}else{
		user = userRepository.findByName(username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		}

		return UserDetailsImpl.build(user);
	}

}
