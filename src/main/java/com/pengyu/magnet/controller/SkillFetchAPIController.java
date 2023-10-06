package com.pengyu.magnet.controller;

import com.pengyu.magnet.service.ResumeParserAPIService;
import com.pengyu.magnet.service.SkillsFetchAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/external/skills")
@RequiredArgsConstructor
public class SkillFetchAPIController {
    private final SkillsFetchAPIService skillsFetchAPIService;

    @GetMapping
    public String fetch(@RequestParam("skill") String skill){
        return skillsFetchAPIService.fetch(skill);
    }
}
