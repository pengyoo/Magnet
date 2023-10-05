package com.pengyu.magnet.repository;

import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
