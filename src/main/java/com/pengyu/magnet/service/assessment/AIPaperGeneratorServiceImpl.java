package com.pengyu.magnet.service.assessment;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengyu.magnet.domain.Job;
import com.pengyu.magnet.domain.User;
import com.pengyu.magnet.domain.assessment.TestPaper;
import com.pengyu.magnet.dto.TestPaperDTO;
import com.pengyu.magnet.dto.TestPaperGenerationRequest;
import com.pengyu.magnet.exception.ApiException;
import com.pengyu.magnet.exception.ResourceNotFoundException;
import com.pengyu.magnet.langchain4j.AssessmentAgent;
import com.pengyu.magnet.repository.JobRepository;
import com.pengyu.magnet.repository.UserRepository;
import com.pengyu.magnet.repository.assessment.TestPaperRepository;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.structured.StructuredPrompt;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserRepository userRepository;
    private final TestPaperRepository testPaperRepository;

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

    public synchronized TestPaperDTO generatePaper(TestPaperGenerationRequest testPaperGenerationRequest){
        // Check if test is already exist
        TestPaper testPaper = testPaperRepository.findByJobId(testPaperGenerationRequest.getJobId());
        if(testPaper != null) {
            throw new ApiException("You've already generated questions for this job.");
        }

        // Get Job info
        Job job = jobRepository
                .findById(testPaperGenerationRequest.getJobId())
                .orElseThrow(()->new ResourceNotFoundException("No such job found with id " + testPaperGenerationRequest.getJobId()));

        // Get Current login user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        // Check if current user owns this job
        if(!job.getCompany().getUser().getEmail().equals(user.getEmail())){
            throw new InsufficientAuthenticationException("Sorry, you can not generate question for other user's job!");
        }

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
