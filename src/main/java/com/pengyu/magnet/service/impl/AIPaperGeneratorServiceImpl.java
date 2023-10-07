package com.pengyu.magnet.service.impl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengyu.magnet.domain.Job;
import com.pengyu.magnet.dto.TestPaperDTO;
import com.pengyu.magnet.dto.TestPaperGenerationRequest;
import com.pengyu.magnet.exception.ApiException;
import com.pengyu.magnet.exception.ResourceNotFoundException;
import com.pengyu.magnet.langchan4j.AssessmentAgent;
import com.pengyu.magnet.repository.JobRepository;
import com.pengyu.magnet.service.AIPaperGeneratorService;
import com.pengyu.magnet.service.TestPaperService;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.structured.StructuredPrompt;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * AI Test Paper Generator Service
 */
@Service
@RequiredArgsConstructor
public class AIPaperGeneratorServiceImpl implements AIPaperGeneratorService {
    private final AssessmentAgent assessmentAgent;

    private final ObjectMapper objectMapper;

    private final TestPaperService testPaperService;

    private final JobRepository jobRepository;

    // Prompt Template
    @StructuredPrompt({
            "Generate {{languageNumber}} questions about {{language}}, and {{generalNumber}} technical interview questions based on the technical requirements mentioned in the job description. ",
            "These questions should assess a job seeker's knowledge.",
            "Job Description: {{jobDescription}}",
            "Structure your answer in the following way:",

            """
                      {
                      "type": "TECHNOLOGY",
                      "jobId": {{jobId}},
                      "questionList": [
                        {
                          "question": "...",
                          "type": "FREE_TEXT",
                          "standardAnswer": "..."
                        }
                      ]
                    }
                    """
    })

    /**
     * Prompt Template Params
     */
    @AllArgsConstructor
    static class CreateTestPrompt {


        private int generalNumber;
        private int languageNumber;
        private String language;
        private Long jobId;
        private String jobDescription;

    }

    /**
     * Call AI API, generate questions and save it to database
     * @param testPaperGenerationRequest
     * @return
     */
    public TestPaperDTO generatePaper(TestPaperGenerationRequest testPaperGenerationRequest){
        // Get Job info
        Job job = jobRepository
                .findById(testPaperGenerationRequest.getJobId())
                .orElseThrow(()->new ResourceNotFoundException("No such job found with id " + testPaperGenerationRequest.getJobId()));

        // Build prompt template
        CreateTestPrompt createTestPrompt =
                new CreateTestPrompt(testPaperGenerationRequest.getGeneralNumber(),
                        testPaperGenerationRequest.getLanguageNumber(),
                        testPaperGenerationRequest.getLanguage(),
                        job.getId(),
                        job.getDescription());

        // Render template
        Prompt prompt = StructuredPromptProcessor.toPrompt(createTestPrompt);

        // Call AI API to generate questions
        String json = assessmentAgent.chat(prompt.toUserMessage().text());


        try {
            // Parse return json
            var testPaperDTO =  objectMapper.readValue(json, TestPaperDTO.class);

            // Save it to database
            return testPaperService.save(testPaperDTO);
        } catch (Exception e){
            throw new ApiException(e.getMessage());
        }

    }
}
