package com.pengyu.magnet.repository;

import com.pengyu.magnet.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    Image findByUserId(Long id);
}

