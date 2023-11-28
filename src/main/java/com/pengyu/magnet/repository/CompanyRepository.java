package com.pengyu.magnet.repository;

import com.pengyu.magnet.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByUserId(Long id);

    @Query(value = "SELECT COALESCE(COUNT(u.id), 0) AS registrationCount " +
            "FROM (SELECT 1 AS month UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 " +
            "      UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10 " +
            "      UNION SELECT 11 UNION SELECT 12) AS m " +
            "LEFT JOIN company u ON MONTH(u.created_at) = m.month " +
            "GROUP BY m.month " +
            "ORDER BY m.month", nativeQuery = true)
    List<Long> getCompanyCounts();

}
