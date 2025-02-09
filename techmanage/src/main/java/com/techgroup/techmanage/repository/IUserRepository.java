package com.techgroup.techmanage.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.techgroup.techmanage.model.User;

public interface IUserRepository extends JpaRepository<User, Long>
{
	Optional<User> findByEmail(String email);
}
