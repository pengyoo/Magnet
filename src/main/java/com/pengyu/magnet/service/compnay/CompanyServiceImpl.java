package com.pengyu.magnet.service.compnay;

import com.pengyu.magnet.domain.Company;
import com.pengyu.magnet.domain.User;
import com.pengyu.magnet.dto.CompanyRequest;
import com.pengyu.magnet.dto.CompanyResponse;
import com.pengyu.magnet.exception.ResourceNotFoundException;
import com.pengyu.magnet.mapper.CompanyMapper;
import com.pengyu.magnet.mapper.UserMapper;
import com.pengyu.magnet.repository.CompanyRepository;
import com.pengyu.magnet.repository.UserRepository;
import com.pengyu.magnet.service.compnay.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Company business layer
 */
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    /**
     * Add or Edit company information
     * @param companyRequest
     * @return
     */
    @Override
    public CompanyResponse save(CompanyRequest companyRequest) {

        // Get Current login user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        // Validate if user login
        if(user == null){
            throw new InsufficientAuthenticationException("Please Login before do any operation!");
        }
//        // Validate if user is a company user | @RolesAllowed(CONSTANTS.ROLE_COMPANY) in controller
//        if(!User.Role.COMPANY.name().equals(user.getRole().name())){
//            throw new InsufficientAuthenticationException("Sorry, you are not a company user, you can't do this operation!");
//        }

        //TODO Check if a user already filled the company information

        // Convert to company domain from dto
        Company company = CompanyMapper.INSTANCE.mapCompanyRequestToCompany(companyRequest);

        // Bind User
        company.setUser(user);

        // Save user to database
        company = companyRepository.save(company);

        // Convert to response DTO
        CompanyResponse companyResponse = CompanyMapper.INSTANCE.mapCompanyToCompanyResponse(company);
        companyResponse.setUserData(UserMapper.INSTANCE.mapUserToUserResponse(user));
        return companyResponse;
    }


    /**
     * Find company by id
     * @param id
     * @return
     */
    public CompanyResponse find(Long id){
        Company company = companyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Company doesn't exist with id " + id));
        CompanyResponse companyResponse = CompanyMapper.INSTANCE.mapCompanyToCompanyResponse(company);
        companyResponse.setUserData(UserMapper.INSTANCE.mapUserToUserResponse(company.getUser()));
        return companyResponse;
    }

    /**
     * Find companies by pageable
     * @param pageable
     * @return
     */
    public List<CompanyResponse> findAll(Pageable pageable) {
        Page<Company> companies = companyRepository.findAll(pageable);
        return companies.map(company -> {
            CompanyResponse companyResponse = CompanyMapper.INSTANCE.mapCompanyToCompanyResponse(company);
            companyResponse.setUserData(UserMapper.INSTANCE.mapUserToUserResponse(company.getUser()));
            return companyResponse;
        }).toList();
    }

    @Override
    public long count() {
        return companyRepository.count();
    }
}
