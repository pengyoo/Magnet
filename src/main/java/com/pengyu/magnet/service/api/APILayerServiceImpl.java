package com.pengyu.magnet.service.api;

import com.pengyu.magnet.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Call API of APILayer, Use NPL parse CV, get structured CV information
 */
@Service
@RequiredArgsConstructor
public class APILayerServiceImpl implements APILayerService {

    private final RestTemplate restTemplate;

    // API URL
    @Value("${apilayer.resume.parser.url}")
    private String resumeParserApiUrl;
    @Value("${apilayer.skills.url}")
    private String skillsApiUrl;

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
    public String parseResume(MultipartFile file) throws IOException {

        // Prepare the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set("apikey", key);

        // Read the file as bytes
        byte[] fileBytes = file.getBytes();

        // Create HttpEntity
        HttpEntity<byte[]> requestEntity = new HttpEntity<>(fileBytes, headers);

        // Send POST request
        ResponseEntity<String> response = restTemplate.postForEntity(resumeParserApiUrl, requestEntity, String.class);

        // Success, return
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        // Error, throw exception
        throw new ApiException("An error occurred while calling the API");
    }


    /**
     * Fetch Skills from APILayer
     * @param skill
     * @return
     */
    @Override
    public String fetchSkills(String skill) {

        // Prepare the request headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("apikey", key);
        String url = skillsApiUrl +"?q=" + skill;

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
