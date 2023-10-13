package com.pengyu.magnet.service.compnay;

import com.pengyu.magnet.dto.CompanyRequest;
import com.pengyu.magnet.dto.CompanyResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyService {
    CompanyResponse save(CompanyRequest companyRequest);
    CompanyResponse find(Long id);
    List<CompanyResponse> findAll(Pageable pageable);

    long count();

    CompanyResponse findCurrentCompany();
}
