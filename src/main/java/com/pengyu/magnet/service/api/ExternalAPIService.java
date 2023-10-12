package com.pengyu.magnet.service.api;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ExternalAPIService {
    String parseResume(MultipartFile file) throws IOException;
    String fetchSkills(String skill);

    public List<String> fetchCountries();
    public String[] fetchCities(String country);
}
