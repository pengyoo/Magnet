package com.pengyu.magnet.controller;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.CompanyRequest;
import com.pengyu.magnet.dto.CompanyResponse;
import com.pengyu.magnet.service.compnay.CompanyService;
import jakarta.annotation.security.RolesAllowed;
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
    @RolesAllowed(CONSTANTS.ROLE_COMPANY)
    @PostMapping("/save")
    public CompanyResponse save(@RequestBody CompanyRequest companyRequest){
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
     * @param page
     * @param pageSize
     * @param orderBy
     * @param order
     * @return list of CompanyResponse
     */
    @GetMapping()
    public List<CompanyResponse> findAll(@RequestParam(required = false, defaultValue = "0") int page,
                                         @RequestParam(required = false, defaultValue = "10") int pageSize,
                                         @RequestParam(required = false, defaultValue = "id") String orderBy,
                                         @RequestParam(required = false, defaultValue = "desc") String order){
        // process sort factor
        Sort sort = "desc".equals(order) ? Sort.by(orderBy).descending() : Sort.by(orderBy).ascending();
        // create pageable
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        return companyService.findAll(pageable);
    }

}
