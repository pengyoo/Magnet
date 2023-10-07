package com.pengyu.magnet.mapper;

import com.pengyu.magnet.domain.assessment.TestPaper;
import com.pengyu.magnet.dto.TestPaperDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TestPaperMapper {
    TestPaperMapper INSTANCE = Mappers.getMapper(TestPaperMapper.class);

    TestPaperDTO mapTestPaperToTestPaperDTO(TestPaper testPaper);
    TestPaper mapTestPaperDTOToTestPaper(TestPaperDTO testPaperDTO);
}
