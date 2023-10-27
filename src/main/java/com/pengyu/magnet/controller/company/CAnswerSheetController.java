package com.pengyu.magnet.controller.company;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.AnswerSheetDTO;
import com.pengyu.magnet.dto.TestPaperDTO;
import com.pengyu.magnet.service.assessment.AnswerSheetService;
import com.pengyu.magnet.service.assessment.TestPaperService;
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
@RequestMapping("/api/v1/canswers")
@RequiredArgsConstructor
public class CAnswerSheetController {
    private final AnswerSheetService answerSheetService;

    @GetMapping
    @RolesAllowed({CONSTANTS.ROLE_COMPANY})
    public List<AnswerSheetDTO> findAll(@RequestParam(defaultValue = "0", required = false) Integer _start,
                                        @RequestParam(defaultValue = "10", required = false) Integer _end,
                                        @RequestParam(defaultValue = "id", required = false) String sortBy,
                                        @RequestParam(defaultValue = "desc", required = false) String order,
                                        HttpServletResponse response) {
        Pageable pageable = PageUtil.getPageable(_start, _end, sortBy, order);

        Page<AnswerSheetDTO> answerSheetDTOS = answerSheetService.findAllByCurrentCompany(pageable);

        // Set Header
        String count = String.valueOf(answerSheetDTOS.getTotalElements());
        response.addHeader("x-total-count", count);
        response.addHeader("Access-Control-Expose-Headers", "x-total-count");

        return answerSheetDTOS.getContent();
    }

    @GetMapping("/{id}")
    @RolesAllowed({CONSTANTS.ROLE_COMPANY})
    public AnswerSheetDTO find(@PathVariable Long id){
        return answerSheetService.find(id);
    }
}
