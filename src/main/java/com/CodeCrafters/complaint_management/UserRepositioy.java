package com.CodeCrafters.complaint_management;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositioy extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	
}
