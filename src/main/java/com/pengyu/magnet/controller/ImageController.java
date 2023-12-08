package com.pengyu.magnet.controller;

import com.pengyu.magnet.config.CONSTANTS;
import com.pengyu.magnet.dto.ImageResponse;
import com.pengyu.magnet.service.ImageService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @RolesAllowed({CONSTANTS.ROLE_COMPANY, CONSTANTS.ROLE_JOB_SEEKER, CONSTANTS.ROLE_ADMIN})
    public ImageResponse postImageUpload(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request) throws IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String imageName = imageService.upload(username, file,0);
        String url = request.getContextPath() + "/images/%s/%s".formatted(username, imageName);
        return new ImageResponse(username, imageName, url);
    }

    @GetMapping(
            value = "{email}/{imageName}",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public byte[] getHeadShot(
            @PathVariable("email") String email,
            @PathVariable("imageName") String imageName) {
        return imageService.get(email, imageName);
    }
}
