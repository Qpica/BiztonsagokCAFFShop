package com.Biztonsagok.CAFFShop;

import com.Biztonsagok.CAFFShop.models.User;
import com.Biztonsagok.CAFFShop.models.UserRole;
import com.Biztonsagok.CAFFShop.models.UserRoleType;
import com.Biztonsagok.CAFFShop.repositories.UserRepository;
import com.Biztonsagok.CAFFShop.repositories.UserRoleRepository;
import com.narcano.jni.CaffParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CaffShopApplication implements CommandLineRunner {
	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Bean
	public CaffParser caffParser() {
		return new CaffParser();
	}

	public static void main(String[] args) {
		SpringApplication.run(CaffShopApplication.class, args);
	}

	@Override
	public void run(String[] args) throws Exception {
		if (userRoleRepository.findByRoleName(UserRoleType.ROLE_ADMINISTRATOR).isEmpty())
			userRoleRepository.save(new UserRole(UserRoleType.ROLE_ADMINISTRATOR));
		if (userRoleRepository.findByRoleName(UserRoleType.ROLE_USER).isEmpty())
			userRoleRepository.save(new UserRole(UserRoleType.ROLE_USER));
		if (!userRepository.existsByUsername("admin")) {
			Set<UserRole> roles = new HashSet<>();
			Optional<UserRole> adminRole = userRoleRepository.findByRoleName(UserRoleType.ROLE_ADMINISTRATOR);
			User admin = new User("admin", encoder.encode("admin"));
			adminRole.ifPresent(roles::add);
			admin.setRoles(roles);
			userRepository.save(admin);
		}
	}

}
