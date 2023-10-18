package com.pengyu.magnet.service.match;

import com.pengyu.magnet.domain.JobApplication;
import com.pengyu.magnet.domain.Resume;
import com.pengyu.magnet.domain.match.MatchingIndex;
import com.pengyu.magnet.dto.MatchingIndexDTO;
import com.pengyu.magnet.exception.ResourceNotFoundException;
import com.pengyu.magnet.mapper.JobMapper;
import com.pengyu.magnet.mapper.MatchingIndexMapper;
import com.pengyu.magnet.repository.ResumeRepository;
import com.pengyu.magnet.repository.match.MatchingIndexRepository;
import com.pengyu.magnet.service.compnay.JobServiceImpl;
import com.pengyu.magnet.service.resume.ResumeServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * MatchingIndex Service
 */
@Service
@RequiredArgsConstructor
public class MatchingIndexServiceImpl implements MatchingIndexService{

    private final MatchingIndexRepository matchingIndexRepository;
    private final ResumeRepository resumeRepository;

    /**
     * Find All MatchingIndex
     * @param pageable
     * @return
     */
    @Override
    public List<MatchingIndexDTO> findAll(Pageable pageable) {
        return matchingIndexRepository.findAll(pageable).map(matchingIndex -> {
            MatchingIndexDTO matchingIndexDTO = MatchingIndexMapper.INSTANCE.mapMatchingIndexToMatchingIndexDTO(matchingIndex);
            matchingIndexDTO.setResumeDTO(ResumeServiceImpl.mapResumeToResumeDTO(matchingIndex.getResume()));
            matchingIndexDTO.setJobResponse(JobMapper.INSTANCE.mapJobToJobResponse(matchingIndex.getJob()));
            return matchingIndexDTO;
        }).toList();
    }

    /**
     * Find one
     * @param id
     * @return
     */
    @Override
    public MatchingIndexDTO find(Long id) {
        MatchingIndex matchingIndex = matchingIndexRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No such MatchingIndex found with id " + id));
        MatchingIndexDTO matchingIndexDTO = MatchingIndexMapper.INSTANCE
                .mapMatchingIndexToMatchingIndexDTO(matchingIndex);
        matchingIndexDTO.setResumeDTO(ResumeServiceImpl.mapResumeToResumeDTO(matchingIndex.getResume()));
        matchingIndexDTO.setJobResponse(JobMapper.INSTANCE.mapJobToJobResponse(matchingIndex.getJob()));
        return matchingIndexDTO;
    }

    @Override
    public long count() {
        return matchingIndexRepository.count();
    }

    @Override
    public MatchingIndexDTO findByMatchingIndexByJobApplication(JobApplication jobApplication) {
        Resume resume = resumeRepository
                .findByUserId(jobApplication.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("No such resume found with user id " + jobApplication.getUser().getId()));
        MatchingIndex matchingIndex = matchingIndexRepository
                .findByJobIdAndResumeId(jobApplication.getJob().getId(), resume.getId())
                .orElse(null);
        return MatchingIndexMapper.INSTANCE.mapMatchingIndexToMatchingIndexDTO(matchingIndex);
    }
}
