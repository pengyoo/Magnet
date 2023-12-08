package com.pengyu.magnet.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    public String upload(String email, MultipartFile file, int type) throws IOException;
    public byte[] get(String email, String imageName);
}
