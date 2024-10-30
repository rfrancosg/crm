package com.rfrancos.crm.repository;

import com.rfrancos.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
