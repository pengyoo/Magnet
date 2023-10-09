package com.pengyu.magnet.controller;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.CompanyRequest;
import com.pengyu.magnet.dto.CompanyResponse;
import com.pengyu.magnet.service.compnay.CompanyService;
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
@RequestMapping("/api/v1/companies")
public class CompanyController {
    private final CompanyService companyService;

    /**
     * Save company info
     * @param companyRequest
     * @return
     */
    @RolesAllowed({CONSTANTS.ROLE_COMPANY, CONSTANTS.ROLE_ADMIN})
    @PostMapping
    public CompanyResponse save(@RequestBody CompanyRequest companyRequest){
        return companyService.save(companyRequest);
    }

    @RolesAllowed({CONSTANTS.ROLE_COMPANY, CONSTANTS.ROLE_ADMIN})
    @PatchMapping("/{id}")
    public CompanyResponse patch(@RequestBody CompanyRequest companyRequest){
        return companyService.save(companyRequest);
    }

    /**
     * Find company by id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CompanyResponse find(@PathVariable Long id){
        return companyService.find(id);
    }

    /**
     * Find companies
     * @param _start
     * @param _end
     * @param sort
     * @param order
     * @return list of CompanyResponse
     */
    @GetMapping()
    public List<CompanyResponse> findAll(@RequestParam(defaultValue = "0", required = false) Integer _start,
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
        String count = String.valueOf(companyService.count());
        response.addHeader("x-total-count", count);
        response.addHeader("Access-Control-Expose-Headers", "x-total-count");

        return companyService.findAll(pageable);
    }

}
