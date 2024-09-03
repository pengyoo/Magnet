package com.pengyu.magnet.service.api;

import com.pengyu.magnet.dto.CountryAPIDTO;
import com.pengyu.magnet.exception.ApiException;
import io.jsonwebtoken.lang.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Call API of APILayer, Use NPL parse CV, get structured CV information
 */
@Service
@RequiredArgsConstructor
public class ExternalAPIServiceImpl implements ExternalAPIService {

    private final RestTemplate restTemplate;

    // API URL
    @Value("${external.apilayer.resume_parser.url}")
    private String resumeParserApiUrl;
    @Value("${external.apilayer.skills.url}")
    private String skillsApiUrl;

    @Value("${external.countries.url}")
    private String countriesApiUrl;
    @Value("${external.cities.url}")
    private String citiesApiUrl;

    // API Key
    @Value("${external.apilayer.key}")
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


    /**
     * Fetch Skills from APILayer
     * @return
     */
    @Override
    public List<String> fetchCountries() {

        // Prepare the request headers
        HttpHeaders headers = new HttpHeaders();
        String url = countriesApiUrl;

        // Create HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Send GET request
        ResponseEntity<CountryAPIDTO[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, CountryAPIDTO[].class);

        List<String> countries = new ArrayList<>();
        for(CountryAPIDTO countryAPIDTO : response.getBody()) {
            countries.add(countryAPIDTO.getName().getCommon());
        }

        // Success, return
        if (response.getStatusCode().is2xxSuccessful()) {
            return countries;
        }

        // Error, throw exception
        throw new ApiException("An error occurred while calling the API");
    }
    @Override
    public String[] fetchCities(String country) {

        // Prepare the request headers
        HttpHeaders headers = new HttpHeaders();
        String url = countriesApiUrl;


        // Create HttpEntity
        HttpEntity<String> requestEntity = new HttpEntity<>(country, headers);

        // Send POST request
        ResponseEntity<String[]> response = restTemplate.postForEntity(citiesApiUrl, requestEntity, String[].class, "data");


        // Success, return
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }

        // Error, throw exception
        throw new ApiException("An error occurred while calling the API");
    }
}
