package com.pengyu.magnet.service.assessment;

import com.pengyu.magnet.dto.TestInvitationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestInvitationService {
    TestInvitationDTO invite(Long applicationId);

    Page<TestInvitationDTO> findAllByCurrentCompany(Pageable pageable);

    void delete(Long id);

    Page<TestInvitationDTO> findAllByCurrentUser(Pageable pageable);

    TestInvitationDTO findById(Long id);
}
