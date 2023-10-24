package com.pengyu.magnet.service.assessment;

import com.pengyu.magnet.domain.User;
import com.pengyu.magnet.domain.assessment.*;
import com.pengyu.magnet.dto.AnswerDTO;
import com.pengyu.magnet.dto.AnswerSheetDTO;
import com.pengyu.magnet.exception.ApiException;
import com.pengyu.magnet.exception.ResourceNotFoundException;
import com.pengyu.magnet.mapper.AnswerMapper;
import com.pengyu.magnet.mapper.UserMapper;
import com.pengyu.magnet.repository.UserRepository;
import com.pengyu.magnet.repository.assessment.*;
import com.pengyu.magnet.service.assessment.AnswerSheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Answer Sheet Service: used to collection answer from job seeker
 */

@Service
@RequiredArgsConstructor
public class AnswerSheetServiceImpl implements AnswerSheetService {
    private final AnswerSheetRepository answerSheetRepository;
    private final UserRepository userRepository;
    private final TestPaperRepository testPaperRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    private final TestInvitationRepository testInvitationRepository;

    /**
     * Save Answer Sheet from a user
     * @param answerSheetDTO
     * @return
     */
    @Override
    @Transactional
    public AnswerSheetDTO save(AnswerSheetDTO answerSheetDTO) {
        // Get Current login user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        // Get TestPaper
        TestPaper testPaper = testPaperRepository
                .findById(answerSheetDTO.getPaperId())
                .orElseThrow(() -> new ResourceNotFoundException("No such Test Paper with id " + answerSheetDTO.getPaperId()));

        Optional<AnswerSheet> optional =  answerSheetRepository.findByUserAndTestPaper(user, testPaper);
        if(optional.isPresent()) {
            throw new ApiException("You've already finished this test");
        }

        // Build AnswerSheet
        AnswerSheet answerSheet = AnswerSheet.builder()
                .user(user)
                .testPaper(testPaper)
                .answerList(answerSheetDTO.getAnswers().stream().map(
                        answerDTO -> AnswerMapper.INSTANCE.mapAnswerDTOToAnswer(answerDTO)).collect(Collectors.toList()))
                .createdAt(LocalDateTime.now())
                .build();

        // Bind Answer Sheet for Answer
        for(Answer answer : answerSheet.getAnswerList()) {
            answer.setAnswerSheet(answerSheet);
        }

        // Save
        answerSheet = answerSheetRepository.save(answerSheet);

        //Set Test Invitation Status
        TestInvitation testInvitation = testInvitationRepository
                .findById(answerSheetDTO.getInvitationId())
                .orElseThrow(() -> new ResourceNotFoundException("No such Test Invitation found with id " + answerSheetDTO.getInvitationId()));
        testInvitation.setStatus(TestInvitation.Status.FINISHED);
        testInvitationRepository.save(testInvitation);

        // map to dto
        return mapToAnswerSheetDTO(answerSheet);
    }

    @Override
    public AnswerSheetDTO find(Long id) {
        AnswerSheet answerSheet = answerSheetRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such Answer Sheet found with id " + id));

        AnswerSheetDTO answerSheetDTO =  mapToAnswerSheetDTO(answerSheet);
        answerSheetDTO.setUserResponse(UserMapper.INSTANCE.mapUserToUserResponse(answerSheet.getUser()));
        return answerSheetDTO;
    }

    @Override
    public List<AnswerSheetDTO> findAll(Pageable pageable, Long userId) {
        if(userId != null) {
            // Get Current login user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = userRepository.findByEmail(email);

            List<AnswerSheet> answerSheets = answerSheetRepository.findAllByUser(pageable, user);
            return answerSheets.stream().map(answerSheet -> {
                AnswerSheetDTO answerSheetDTO =  mapToAnswerSheetDTO(answerSheet);
                answerSheetDTO.setUserResponse(UserMapper.INSTANCE.mapUserToUserResponse(user));
                return answerSheetDTO;
            }).collect(Collectors.toList());
        }

        Page<AnswerSheet> answerSheets = answerSheetRepository.findAll(pageable);
        return answerSheets.stream().map(answerSheet -> {
            AnswerSheetDTO answerSheetDTO =  mapToAnswerSheetDTO(answerSheet);
            answerSheetDTO.setUserResponse(UserMapper.INSTANCE.mapUserToUserResponse(answerSheet.getUser()));
            return answerSheetDTO;
        }).collect(Collectors.toList());

    }

    /**
     * Map Answer Sheet to DTO
     * @param answerSheet
     * @return
     */
    private AnswerSheetDTO mapToAnswerSheetDTO(AnswerSheet answerSheet) {
        List<AnswerDTO> answerDTOS = new ArrayList<>();
        if(answerSheet.getAnswerList() != null) {
            for (Answer answer : answerSheet.getAnswerList()) {
                AnswerDTO answerDTO = AnswerDTO.builder()
                        .answer(answer.getAnswer())
                        .questionText(answer.getQuestionText())
                        .id(answer.getId())
                        .build();
                answerDTOS.add(answerDTO);
            }
        }
        AnswerSheetDTO build = AnswerSheetDTO.builder()
                .answers(answerDTOS)
                .paperId(answerSheet.getTestPaper().getId())
                .id(answerSheet.getId())
                .build();

        return build;
    }


    /**
     * Save Answers
     * @param answerDTOs
     * @param answerSheetId
     * @return
     */
    public void saveAnswers(List<AnswerDTO> answerDTOs,Long answerSheetId){
        AnswerSheet answerSheet = answerSheetRepository
                .findById(answerSheetId)
                .orElseThrow(() -> new ResourceNotFoundException("No such Answer Sheet found with id " + answerSheetId));
        for (AnswerDTO answerDTO : answerDTOs){
            Question question = questionRepository
                    .findById(answerDTO.getQuestionId())
                            .orElseThrow(()-> new ResourceNotFoundException("No such question found with id "+ answerDTO.getQuestionId()));
            Answer answer = Answer.builder()
                    .answerSheet(answerSheet)
                    .questionText(answerDTO.getQuestionText())
                    .answer(answerDTO.getAnswer())
                    .id(answerDTO.getId())
                    .build();
            answerRepository.save(answer);
        }
    }

    @Override
    public long count(Long userId) {
        if(userId != null){
            return answerSheetRepository.countByUserId(userId);
        }
        return answerSheetRepository.count();
    }
}

