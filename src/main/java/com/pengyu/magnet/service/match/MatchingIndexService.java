package com.pengyu.magnet.service.match;

import com.pengyu.magnet.dto.MatchingIndexDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MatchingIndexService {
    List<MatchingIndexDTO> findAll(Pageable pageable);

    MatchingIndexDTO find(Long id);
    public long count();
}
