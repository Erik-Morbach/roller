package br.com.erikmorbach.roller.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<UserModel, UUID>{
	boolean existsByUsername(String username);
	UserModel findByUsername(String username);
}
