package com.pengyu.magnet.controller;

import com.pengyu.magnet.service.api.ExternalAPIService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Call external API
 */
@RestController
@RequestMapping("/api/v1/external")
@RequiredArgsConstructor
public class ExternalAPIController {
    private final ExternalAPIService externalAPIService;

    @GetMapping("skills")
    public String fetch(@RequestParam(value = "skill", required = false) String skill){
        if(StringUtils.isBlank(skill))
            return "[]";
        return externalAPIService.fetchSkills(skill);
    }

    @PostMapping(value = "parse_resume",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String parse( @RequestParam("file") MultipartFile file) throws IOException {
        return externalAPIService.parseResume(file);
    }

    @GetMapping("/countries")
    public List<String> countries(){
        return externalAPIService.fetchCountries();
    }

    @GetMapping("/cities")
    public String[] countries(@RequestParam String country){
        return externalAPIService.fetchCities(country);
    }
}
