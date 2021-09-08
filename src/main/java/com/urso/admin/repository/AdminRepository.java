package com.urso.admin.repository;

import com.urso.admin.entity.AdminMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<AdminMessage,Long> {
}
