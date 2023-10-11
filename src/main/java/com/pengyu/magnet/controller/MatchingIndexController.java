package com.pengyu.magnet.controller;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.MatchingIndexDTO;
import com.pengyu.magnet.service.match.MatchingIndexService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/matches")
public class MatchingIndexController {

    private final MatchingIndexService matchingIndexService;
    @GetMapping("/{id}")
    public MatchingIndexDTO findById(@PathVariable Long id) {
        return matchingIndexService.find(id);
    }

    @GetMapping
    @RolesAllowed({CONSTANTS.ROLE_ADMIN})
    public List<MatchingIndexDTO> findAll(@RequestParam(defaultValue = "0", required = false) Integer _start,
                                          @RequestParam(defaultValue = "10", required = false) Integer _end,
                                          @RequestParam(defaultValue = "id", required = false) String sort,
                                          @RequestParam(defaultValue = "desc", required = false) String order,
                                          HttpServletResponse response){
        // process sort factor
        Sort sortBy = "desc".equals(order) ? Sort.by(sort).descending() : Sort.by(sort).ascending();

        // create pageable
        int pageSize = _end - _start;
        int page = _start / (pageSize - 1);
        Pageable pageable = PageRequest.of(page, pageSize, sortBy);

        // Set Header
        String count = String.valueOf(matchingIndexService.count());
        response.addHeader("x-total-count", count);
        response.addHeader("Access-Control-Expose-Headers", "x-total-count");

        return matchingIndexService.findAll(pageable);
    }
}
