package com.pengyu.magnet.service;

import com.pengyu.magnet.domain.Image;
import com.pengyu.magnet.domain.User;
import com.pengyu.magnet.repository.ImageRepository;
import com.pengyu.magnet.repository.UserRepository;
import com.pengyu.magnet.s3.S3Buckets;
import com.pengyu.magnet.s3.S3Service;
import com.pengyu.magnet.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final S3Service s3Service;
    private final S3Buckets s3Buckets;

    private final ImageRepository imageRepository;

    private final UserRepository userRepository;
    @Override
    public String upload(String email, MultipartFile file, int type) throws IOException {
        User user = userRepository.findByEmail(email);
        if(user == null)
            throw new InsufficientAuthenticationException("You need to login first!");
        String imageName = UUID.randomUUID().toString();
        s3Service.putObject(
                s3Buckets.getImages(),
                "images/%s/%s".formatted(email, imageName),
                file.getBytes()
        );

        Image image = new Image(imageName, user,type);
        imageRepository.save(image);

        user.setHeadShotName(imageName);
        userRepository.save(user);

        return imageName;

    }

    @Override
    public byte[] get(String email, String imageName) {
        return s3Service.getObject(
                s3Buckets.getImages(),
                "images/%s/%s".formatted(email, imageName)
        );
    }
}
