package com.pengyu.magnet.controller;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.AnswerDTO;
import com.pengyu.magnet.dto.AnswerSheetDTO;
import com.pengyu.magnet.repository.assessment.TestInvitationRepository;
import com.pengyu.magnet.service.assessment.AnswerSheetService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Answer Sheet Controller
 */
@RestController
@RequestMapping("/api/v1/answers")
@RequiredArgsConstructor
public class AnswerSheetController {
    private final AnswerSheetService answerSheetService;



    /**
     * Save Answer Sheet
     * @param answerSheetDTO
     * @return
     */
    @PostMapping("/save_sheet")
    @RolesAllowed({CONSTANTS.ROLE_JOB_SEEKER})
    public AnswerSheetDTO save(@RequestBody AnswerSheetDTO answerSheetDTO){
        return answerSheetService.save(answerSheetDTO);
    }

    /**
     * Save Answers
     * @param answerDTOS
     * @param answerSheetId
     */
    @PostMapping("/save_answers")
    public void saveAll(@RequestBody List<AnswerDTO> answerDTOS, @RequestParam Long answerSheetId){
        answerSheetService.saveAnswers(answerDTOS, answerSheetId);
    }

    /**
     * Find a Answer Sheet
     * @param answerSheetId
     * @return
     */
    @GetMapping("/{answerSheetId}")
    public AnswerSheetDTO find(@PathVariable Long answerSheetId){
        return answerSheetService.find(answerSheetId);
    }

    /**
     * Find All Answer Sheets of current user
     * @param _start
     * @param _end
     * @param sort
     * @param order
     * @return
     */
    @GetMapping
    public List<AnswerSheetDTO> findAll(@RequestParam(defaultValue = "0", required = false) Integer _start,
                                        @RequestParam(defaultValue = "10", required = false) Integer _end,
                                        @RequestParam(defaultValue = "id", required = false) String sort,
                                        @RequestParam(defaultValue = "desc", required = false) String order,
                                        @RequestParam(required = false) Long userId,
                                        HttpServletResponse response){
        // process sort factor
        Sort sortBy = "desc".equals(order) ? Sort.by(sort).descending() : Sort.by(sort).ascending();

        // create pageable
        int pageSize = _end - _start;
        int page = _start / (pageSize - 1);
        Pageable pageable = PageRequest.of(page, pageSize, sortBy);

        // Set Header
        String count = String.valueOf(answerSheetService.count(userId));
        response.addHeader("x-total-count", count);
        response.addHeader("Access-Control-Expose-Headers", "x-total-count");

        return answerSheetService.findAll(pageable, userId);
    }

    @DeleteMapping("/{answerSheetId}")
    @RolesAllowed({CONSTANTS.ROLE_ADMIN})
    public void delete(@PathVariable Long answerSheetId){
        answerSheetService.delete(answerSheetId);
    }

}
