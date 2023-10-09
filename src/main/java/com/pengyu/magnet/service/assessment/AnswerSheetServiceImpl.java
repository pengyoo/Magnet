package com.pengyu.magnet.service.assessment;

import com.pengyu.magnet.domain.User;
import com.pengyu.magnet.domain.assessment.Answer;
import com.pengyu.magnet.domain.assessment.AnswerSheet;
import com.pengyu.magnet.domain.assessment.Question;
import com.pengyu.magnet.domain.assessment.TestPaper;
import com.pengyu.magnet.dto.AnswerDTO;
import com.pengyu.magnet.dto.AnswerSheetDTO;
import com.pengyu.magnet.exception.ResourceNotFoundException;
import com.pengyu.magnet.repository.UserRepository;
import com.pengyu.magnet.repository.assessment.AnswerRepository;
import com.pengyu.magnet.repository.assessment.AnswerSheetRepository;
import com.pengyu.magnet.repository.assessment.QuestionRepository;
import com.pengyu.magnet.repository.assessment.TestPaperRepository;
import com.pengyu.magnet.service.assessment.AnswerSheetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

        // Build Answer Sheet
        AnswerSheet answerSheet = AnswerSheet.builder()
                .user(user)
                .testPaper(testPaper)
                .createdAt(LocalDateTime.now())
                .build();



        // Save
        answerSheet = answerSheetRepository.save(answerSheet);

        // map to dto
        return mapToAnswerSheetDTO(answerSheet);
    }

    @Override
    public AnswerSheetDTO find(Long id) {
        AnswerSheet answerSheet = answerSheetRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such Answer Sheet found with id " + id));

        return mapToAnswerSheetDTO(answerSheet);
    }

    @Override
    public List<AnswerSheetDTO> findAllByCurrentUser(Pageable pageable) {
        // Get Current login user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        List<AnswerSheet> answerSheets = answerSheetRepository.findAllByUser(pageable, user);
        return answerSheets.stream().map(answerSheet -> mapToAnswerSheetDTO(answerSheet)).collect(Collectors.toList());
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
                        .questionId(answer.getQuestion().getId())
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
                    .question(question)
                    .id(answerDTO.getId())
                    .build();
            answerRepository.save(answer);
        }
    }
}

