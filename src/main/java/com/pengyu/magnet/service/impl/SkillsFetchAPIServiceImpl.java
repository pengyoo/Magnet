package com.pengyu.magnet.service.impl;

import com.pengyu.magnet.exception.ApiException;
import com.pengyu.magnet.repository.UserRepository;
import com.pengyu.magnet.service.ResumeParserAPIService;
import com.pengyu.magnet.service.SkillsFetchAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Call API of APILayer, return Skill List according to a keyword
 */
@Service
@RequiredArgsConstructor
public class SkillsFetchAPIServiceImpl implements SkillsFetchAPIService {

    // API URL
    @Value("${apilayer.skills.url}")
    private String apiUrl;

    // API Key
    @Value("${apilayer.key}")
    private String key;

    @Override
    public String fetch(String skill) {
        RestTemplate restTemplate = new RestTemplate();

        // Prepare the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", key);
        String url = apiUrl+"?q=" + skill;

        // Create HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Send GET request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

        // Success, return
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        // Error, throw exception
        throw new ApiException("An error occurred while calling the API");
    }
}
