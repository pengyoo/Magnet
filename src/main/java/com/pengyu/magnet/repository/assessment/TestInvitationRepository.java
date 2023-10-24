
package com.pengyu.magnet.repository.assessment;

import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.domain.User;
import com.pengyu.magnet.domain.assessment.TestInvitation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TestInvitationRepository extends JpaRepository<TestInvitation, Long> {



    @Query("SELECT ti FROM TestInvitation ti JOIN FETCH ti.testPaper tp JOIN FETCH tp.job j JOIN FETCH j.company where j.company = :company")
    Page<TestInvitation> findAllByCompany(Pageable pageable, Company company);

    Page<TestInvitation> findAllByUser(User user, Pageable pageable);
}
