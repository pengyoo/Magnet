package com.pengyu.magnet.service.assessment;


import com.pengyu.magnet.domain.*;
import com.pengyu.magnet.domain.assessment.TestInvitation;
import com.pengyu.magnet.domain.assessment.TestPaper;
import com.pengyu.magnet.dto.TestInvitationDTO;
import com.pengyu.magnet.exception.ResourceNotFoundException;
import com.pengyu.magnet.mapper.TestInvitationMapper;
import com.pengyu.magnet.mapper.UserMapper;
import com.pengyu.magnet.repository.*;
import com.pengyu.magnet.repository.assessment.TestInvitationRepository;
import com.pengyu.magnet.repository.assessment.TestPaperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TestInvitationServiceImpl implements TestInvitationService {
    private final TestInvitationRepository testInvitationRepository;
    private final TestPaperRepository testPaperRepository;
    private final UserRepository userRepository;
    private final JobApplicationRepository jobApplicationRepository;

    private final CompanyRepository companyRepository;

    private final ResumeRepository resumeRepository;
    private final JobRepository jobRepository;

    @Override
    public TestInvitationDTO invite(Long applicationId) {

        JobApplication jobApplication = jobApplicationRepository
                .findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("No such application found with id " + applicationId));

        User user = jobApplication.getUser();

        TestPaper testPaper = testPaperRepository.findByJobId(jobApplication.getJob().getId());
        if(testPaper == null) {
            throw new ResourceNotFoundException("No such Test Paper found with jobId " + jobApplication.getJob().getId());
        }

        TestInvitation testInvitation = TestInvitation.builder()
                .status(TestInvitation.Status.PENDING)
                .createdAt(LocalDateTime.now())
                .testPaper(testPaper)
                .user(user)
                .build();
        testInvitation = testInvitationRepository.save(testInvitation);
        return mapTestInvitationToTestInvitationResponse(testInvitation);
    }

    /**
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<TestInvitationDTO> findAllByCurrentCompany(Pageable pageable) {
        Company company = findCurrentCompany();
        Page<TestInvitation> page = testInvitationRepository.findAllByCompany(pageable, company);
        return page.map(testInvitation -> mapTestInvitationToTestInvitationResponse(testInvitation));
    }

    @Override
    public void delete(Long id) {
        testInvitationRepository.deleteById(id);
    }

    private Company findCurrentCompany() {
        // Get Current login user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        Company company = companyRepository
                .findByUserId(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("You doesn't create company information."));
        return company;
    }

    public User findCurrentUser(){
        // Get Current login user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);
        return user;
    }

    /**
     * Map TestInvitation to TestInvitationDTO
     * @param testInvitation
     * @return
     */
    public TestInvitationDTO mapTestInvitationToTestInvitationResponse(TestInvitation testInvitation) {
        TestInvitationDTO testInvitationResponse = TestInvitationMapper.INSTANCE.mapTestInvitationToTestInvitationResponse(testInvitation);
        // Set Resume name
        Resume resume = resumeRepository
                .findByUserId(testInvitation.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("No such resume found with user id "+ testInvitation.getUser().getId()));
        testInvitationResponse.setApplicant(resume.getFullName());
        // Set Job Title
        Job job = jobRepository
                .findById(testInvitation.getTestPaper().getJob().getId())
                .orElseThrow(() -> new ResourceNotFoundException("No such job found with id " + testInvitation.getTestPaper().getJob().getId()));
        testInvitationResponse.setJobTitle(job.getTitle());

        return testInvitationResponse;
    }
}
