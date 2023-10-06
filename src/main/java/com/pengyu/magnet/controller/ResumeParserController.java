package com.pengyu.magnet.controller;

import com.pengyu.magnet.service.ResumeParserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/resume_parser")
@RequiredArgsConstructor
public class ResumeParserController {
    private final ResumeParserService resumeParserService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String parse( @RequestParam("file") MultipartFile file) throws IOException {
        return resumeParserService.parse(file);
    }
}
