package com.pengyu.magnet.controller.company;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.TestInvitationDTO;
import com.pengyu.magnet.service.assessment.TestInvitationService;
import com.pengyu.magnet.utils.PageUtil;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cinvitations")
public class CTestInvitationController {
    private final TestInvitationService testInvitationService;

    @PostMapping("/{applicationId}")
    @RolesAllowed({CONSTANTS.ROLE_COMPANY})
    public TestInvitationDTO invite(@PathVariable Long applicationId) {
        return testInvitationService.invite(applicationId);
    }

    @GetMapping
    @RolesAllowed({CONSTANTS.ROLE_COMPANY})
    public List<TestInvitationDTO> findAll(@RequestParam(defaultValue = "0", required = false) Integer _start,
                                           @RequestParam(defaultValue = "10", required = false) Integer _end,
                                           @RequestParam(defaultValue = "id", required = false) String sortBy,
                                           @RequestParam(defaultValue = "desc", required = false) String order,
                                           HttpServletResponse response) {


        Pageable pageable = PageUtil.getPageable(_start, _end, sortBy, order);

        // Set Header
        Page<TestInvitationDTO> page = testInvitationService.findAllByCurrentCompany(pageable);
        response.addHeader("x-total-count", String.valueOf(page.getTotalElements()));
        response.addHeader("Access-Control-Expose-Headers", "x-total-count");

        return page.getContent();

    }

    @DeleteMapping("/{id}")
    @RolesAllowed({CONSTANTS.ROLE_COMPANY})
    public void delete(@PathVariable Long id) {
        testInvitationService.delete(id);
    }
}
