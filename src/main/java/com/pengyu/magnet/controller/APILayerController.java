package com.pengyu.magnet.controller;

import com.pengyu.magnet.service.api.APILayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Call external API to parse CV into structured data
 */
@RestController
@RequestMapping("/api/v1/apilayer")
@RequiredArgsConstructor
public class APILayerController {
    private final APILayerService apiLayerService;

    @GetMapping("skills")
    public String fetch(@RequestParam("skill") String skill){
        return apiLayerService.fetchSkills(skill);
    }

    @PostMapping(value = "parse_resume",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String parse( @RequestParam("file") MultipartFile file) throws IOException {
        return apiLayerService.parseResume(file);
    }
}
