package com.pengyu.magnet.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ResumeParserService {
    String parse(MultipartFile file) throws IOException;
}
