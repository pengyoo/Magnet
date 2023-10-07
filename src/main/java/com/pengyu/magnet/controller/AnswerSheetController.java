package com.pengyu.magnet.controller;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.AnswerDTO;
import com.pengyu.magnet.dto.AnswerSheetDTO;
import com.pengyu.magnet.dto.CompanyResponse;
import com.pengyu.magnet.dto.TestPaperDTO;
import com.pengyu.magnet.service.AnswerSheetService;
import com.pengyu.magnet.service.TestPaperService;
import jakarta.annotation.security.RolesAllowed;
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
@RequestMapping("/api/v1/assess/answer")
@RequiredArgsConstructor
public class AnswerSheetController {
    private final AnswerSheetService answerSheetService;

    /**
     * Save Answer Sheet
     * @param answerSheetDTO
     * @return
     */
    @PostMapping("/save_sheet")
    @RolesAllowed({CONSTANTS.ROLE_JOB_SEEKER, CONSTANTS.ROLE_ADMIN})
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
     * @param page
     * @param pageSize
     * @param orderBy
     * @param order
     * @return
     */
    @GetMapping
    public List<AnswerSheetDTO> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int pageSize,
                                         @RequestParam(required = false, defaultValue = "id") String orderBy,
                                         @RequestParam(required = false, defaultValue = "desc") String order){
        // process sort factor
        Sort sort = "desc".equals(order) ? Sort.by(orderBy).descending() : Sort.by(orderBy).ascending();
        // create pageable
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return answerSheetService.findAllByCurrentUser(pageable);
    }
}
