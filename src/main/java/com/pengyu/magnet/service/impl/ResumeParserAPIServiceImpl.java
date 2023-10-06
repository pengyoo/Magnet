package com.pengyu.magnet.service.impl;

import com.pengyu.magnet.exception.ApiException;
import com.pengyu.magnet.repository.UserRepository;
import com.pengyu.magnet.service.ResumeParserAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Call API of APILayer, Use NPL parse CV, get structured CV information
 */
@Service
@RequiredArgsConstructor
public class ResumeParserAPIServiceImpl implements ResumeParserAPIService {
    private final UserRepository userRepository;

    // API URL
    @Value("${apilayer.resume.parser.url}")
    private String apiUrl;

    // API Key
    @Value("${apilayer.key}")
    private String key;

    /**
     * Parse a CV File
     * @param file
     * @return structured CV json data
     * @throws IOException
     */
    @Override
    public String parse(MultipartFile file) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        // Prepare the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set("apikey", key);

        // Read the file as bytes
        byte[] fileBytes = file.getBytes();

        // Create HttpEntity
        HttpEntity<byte[]> requestEntity = new HttpEntity<>(fileBytes, headers);

        // Send POST request
        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, requestEntity, String.class);

        // Success, return
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        // Error, throw exception
        throw new ApiException("An error occurred while calling the API");
    }
}
