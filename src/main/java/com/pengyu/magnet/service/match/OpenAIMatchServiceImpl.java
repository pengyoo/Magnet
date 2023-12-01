package com.pengyu.magnet.service.match;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengyu.magnet.domain.Job;
import com.pengyu.magnet.domain.Resume;
import com.pengyu.magnet.domain.assessment.AnswerSheet;
import com.pengyu.magnet.domain.match.JobInsights;
import com.pengyu.magnet.domain.match.MatchingIndex;
import com.pengyu.magnet.domain.match.ResumeInsights;
import com.pengyu.magnet.dto.*;
import com.pengyu.magnet.exception.ApiException;
import com.pengyu.magnet.exception.ResourceNotFoundException;
import com.pengyu.magnet.langchain4j.MatchAgent;
import com.pengyu.magnet.mapper.JobMapper;
import com.pengyu.magnet.mapper.MatchingIndexMapper;
import com.pengyu.magnet.repository.JobRepository;
import com.pengyu.magnet.repository.ResumeRepository;
import com.pengyu.magnet.repository.UserRepository;
import com.pengyu.magnet.repository.assessment.AnswerSheetRepository;
import com.pengyu.magnet.repository.match.JobRequirementsRepository;
import com.pengyu.magnet.repository.match.MatchingIndexRepository;
import com.pengyu.magnet.repository.match.ResumeInsightsRepository;
import com.pengyu.magnet.service.resume.ResumeServiceImpl;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.structured.StructuredPrompt;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * OpenAI Job and Resume Match Service
 */
@Service
@RequiredArgsConstructor
public class OpenAIMatchServiceImpl implements AIMatchService {
    private final MatchAgent matchAgent;
    private final ObjectMapper objectMapper;
    private final JobRepository jobRepository;
    private final ResumeRepository resumeRepository;

    private final MatchingIndexRepository matchingIndexRepository;
    private final JobInsightsService jobRequirementsService;
    private final ResumeInsightsService resumeInsightsService;
    private final UserRepository userRepository;
    private final JobRequirementsRepository jobRequirementsRepository;
    private final ResumeInsightsRepository resumeInsightsRepository;

    private final AnswerSheetRepository answerSheetRepository;


    // Prompt Template
    @StructuredPrompt({
            "Please extract software development technology skills such as programming language, libraries, concepts, software, methdologies needed for this job from the job description. Every skill should be an independent technology.",
            "Job Description: {{jobDescription}}",
            "Structure your answer in the following way:",
            """
                     [
                     {"skill":"Java"},
                     {"skill","Scrum"},
                     {"skill","..."}
                     ]
                    """
    })

    /**
     * Prompt Template Params
     */
    @AllArgsConstructor
    static class SkillsExtractPrompt {
        private String jobDescription;

    }

    @StructuredPrompt({
            "Please extract the requirements of the position based on the job description. The items includes: degree, major, skills, experience, language. ",
            "degree: Bachelor's degree, Master's degree, Doctor's, Diploma or other",
            "major: for example, Computer Science",
            "skills: software development technology skills such as programming language, libraries, concepts, software, methodologies needed for this job from the job description. Every skill should be an independent technology. Assign a value to weight based on their importance in the job description and the weight should range from 1 to 10.",
            "experience: the requirement of years of work experience, for example 4+ years.",
            "language: for example, Fluent",
            "If there is no specified requirement for any item just leave it empty",
            "Job Description: {{jobDescription}}",
            "Structure your answer in the following way:",
            """
                    {
                      "degree":"...",
                      "major":"...",
                      skills:
                      [
                      {"skill":"Java"},
                      {"skill","Scrum"},
                      {"skill","..."}
                      ],
                      "experience":"...",
                      "language":"..."
                      }
            """
    })
    @AllArgsConstructor
    static class JobInsightsExtractionPrompt {
        private String jobDescription;
    }

    @StructuredPrompt({
            "Please extract the insights of the resume based on the Resume. The items includes: degree, major, skills, experience, language. ",
            "degree: Bachelor's degree, Master's degree, Doctor's, Diploma or other",
            "major: for example, Computer Science",
            "skills: professional skills such as programming language, libraries, concepts, software, methodologies the job seeker has. Every skill should be an independent technology. If the skill is present in education, work experience and project experience of the resume, the weight of the skill will be increased. The maximum value of weight is 10",
            "experience: how long does the job seeker have in related work experience, for example 4+ years.",
            "language: for example, Fluent in English",
            "If there is no specified information for any item just leave it empty",
            "Resume: {{resume}}",
            "Structure your answer in the following way:",
            """
                    {
                      "degree":"...",
                      "major":"...",
                      skills:
                      [
                      {"skill":"Java", weight:"..."},
                      {"skill","Scrum", weight:"..."},
                      {"skill","...", weight:"..."}
                      ],
                      "experience":"...",
                      "language":"..."
                      }
            """
    })
    @AllArgsConstructor
    static class ResumeInsightsExtractionPrompt {
        private String resume;
    }

    @StructuredPrompt({
            "Please calculate the match index between the ResumeInsights and the JobInsights, with the result as a decimal indicating the match percentage. Every field's value should be 1 if there's no specified requirement in JobInsights or the data in the JobInsights exceeds the requirements of the JobInsights.",
            "The calculation should consider the following data:",
            "degree: Degree match index",
            "major: Major match index",
            "skill: Skill match index (as a percentage of matching skills based on the weight of each skill).",
            "experience: Experience match index.",
            "language: Language match index.",
            "overall: Overall match index (overall = (degree + major + language)/3 * 0.2 + skill * 0.4 + experience * 0.4 )).",
            "Structure your answer in the following way:",
            """
               {
                 "degree": "...",
                 "major": "...",
                 "skill": "...",
                 "experience": "...",
                 "language": "...",
                 "overall": "..."
               }
            """,
            "ResumeInsights: {{resumeInsights}}",
            "JobInsights: {{jobInsights}}",
    })
    @AllArgsConstructor
    static class JobResumeMatchingPrompt {
        private String resumeInsights;
        private String jobInsights;
    }

    @StructuredPrompt({
            "You are provided with an \"AnswerList\" containing the following fields: id, questionText, exampleAnswer, answer, and score. Your goal is to score each answer based on the provided exampleAnswer. The scoring should range from 0 to 100. Additionally, calculate the average score for all answers.",
            "Each item in the \"AnswerList\" represents a Q&A pair. For each answer, compare the provided answer with the corresponding exampleAnswer. Assign a score of 100 if they match perfectly, and a score of 0 if there is no match.",
            "Example AnswerList:",
            """
                    [
                      {"id": 151, "questionText": "What is Java?", "exampleAnswer": "Java is a high-level programming language...", "answer": "Java is a high-level programming language..."},
                      {"id": 152, "questionText": "What is Spring Boot?", "exampleAnswer": "Spring Boot is a framework that simplifies Java application development...", "answer": "Spring Boot is a framework that simplifies Java application development..."}
                    ]             
                    """,
            "Example Output:",
            """
                    {
                      "score": "100",
                      "answerScores": [
                        {"id": 151, "score": 100},
                        {"id": 152, "score": 100}
                        ]
                    }
                                        
                    """,
            "Structure your answer in the following way:",
            """
                    {
                      "score": "...",
                      "answerScores": [
                        {"id": "...", "score": "..."},
                        {"id": "...", "score": "..."}
                      ]
                    }
                                        
                    """,
            "AnswerList: {{answerList}}",
    })
    @AllArgsConstructor
    static class TestScorePrompt {
        private String answerList;
    }

    public JobInsights extractJobInsights(Long jobId) {
        try {

            // Get Job info
            Job job = jobRepository
                    .findById(jobId)
                    .orElseThrow(() -> new ResourceNotFoundException("No such job found with id " + jobId));
            JobResponse jobResponse = JobMapper.INSTANCE.mapJobToJobResponse(job);


            // Build prompt template
            JobInsightsExtractionPrompt jobRequirementsExtractionPrompt =
                    new JobInsightsExtractionPrompt(objectMapper.writeValueAsString(jobResponse));

            // Render template
            Prompt prompt = StructuredPromptProcessor.toPrompt(jobRequirementsExtractionPrompt);

            // Call AI API to generate questions
            String json = matchAgent.chat(prompt.toUserMessage().text());

            // Parse return json
            JobInsights jobRequirementsNew = objectMapper.readValue(json, JobInsights.class);

            // Check if jobRequirements already exist
            JobInsights jobRequirements = jobRequirementsService.findByJobId(jobId);
            if(jobRequirements != null) {
                jobRequirementsRepository.delete(jobRequirements);
            }

            // Save to Data
            JobInsights saved = jobRequirementsService.save(jobRequirementsNew, jobId);

            return saved;

        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }

    }


    /**
     * Extract Resume Characteristics using AI
     * @param resumeId
     * @return
     * @throws JsonProcessingException
     */
    public ResumeInsights extractResumeInsights(Long resumeId) {
        try {


            // Get Resume info
            ResumeDTO resume = ResumeServiceImpl
                    .mapResumeToResumeDTO(resumeRepository.findById(resumeId).orElseThrow(() -> new ResourceNotFoundException("No such ResumeInsights found with resumeId " + resumeId)));

            // Build prompt template
            ResumeInsightsExtractionPrompt resumeExtractionPrompt =
                    new ResumeInsightsExtractionPrompt(objectMapper.writeValueAsString(resume));

            // Render template
            Prompt prompt = StructuredPromptProcessor.toPrompt(resumeExtractionPrompt);

            // Call AI API to extract Resume Characteristics
            String json = matchAgent.chat(prompt.toUserMessage().text());

            // Parse return json
            ResumeInsights resumeInsightsNew = objectMapper.readValue(json, ResumeInsights.class);

            // Check if ResumeInsights already exist
            ResumeInsights resumeInsights = resumeInsightsService.findByResumeId(resumeId);
            if(resumeInsights != null) {
                resumeInsightsRepository.delete(resumeInsights);
            }

            // Save to database
            ResumeInsights saved = resumeInsightsService.save(resumeInsightsNew, resumeId);

            return saved;

        } catch (JsonProcessingException e) {
            throw new ApiException(e.getMessage());
        }

    }

    /**
     * Call AI to math job and resume
     * @param jobId
     * @param resumeId
     * @return
     */
    @Override
    public MatchingIndexDTO match(Long jobId, Long resumeId) {
        try {
            // Get Job info
            JobInsights jobInsights = jobRequirementsService.findByJobId(jobId);
            // Get Resume Info
            ResumeInsights resumeInsights = resumeInsightsService.findByResumeId(resumeId);

            if(jobInsights == null){
                throw new ApiException("There is no JobInsights extract for this job");
            }

            if(resumeInsights == null){
                throw new ApiException("There is no ResumeInsights extract for this resume");
            }

            // Build prompt template
            JobResumeMatchingPrompt jobResumeMatchingPrompt =
                    new JobResumeMatchingPrompt(objectMapper.writeValueAsString(resumeInsights).replaceAll("&quot;", ""), objectMapper.writeValueAsString(jobInsights).replaceAll("&quot;", ""));

            // Render template
            Prompt prompt = StructuredPromptProcessor.toPrompt(jobResumeMatchingPrompt);

            // Call AI API to extract Resume Characteristics
            String json = matchAgent.chat(prompt.toUserMessage().text());

            // Parse return json
            MatchingIndex matchingIndex = objectMapper.readValue(json, MatchingIndex.class);

            // Bind MatchingIndex and Job, Resume
            Job job = jobRepository
                    .findById(jobId)
                    .orElseThrow(() -> new ResourceNotFoundException("No such job found with jobId " + jobId));
            matchingIndex.setJob(job);

            Resume resume = resumeRepository
                    .findById(resumeId)
                    .orElseThrow(() -> new ResourceNotFoundException("No such Resume found with resumeId " + resumeId));
            matchingIndex.setResume(resume);

            // Sava to Database
            MatchingIndex saved = matchingIndexRepository.save(matchingIndex);

            // return
            MatchingIndexDTO matchingIndexDTO = MatchingIndexMapper.INSTANCE.mapMatchingIndexToMatchingIndexDTO(saved);
            matchingIndexDTO.setResumeDTO(ResumeServiceImpl.mapResumeToResumeDTO(resume));
            matchingIndexDTO.setJobResponse(JobMapper.INSTANCE.mapJobToJobResponse(job));
            return matchingIndexDTO;
        } catch (JsonProcessingException e) {
            throw new ApiException(e.getMessage());
        }
    }

    public void scoreTest(Long answerSheetId) {
        try {
            AnswerSheet answerSheet = answerSheetRepository
                    .findById(answerSheetId)
                    .orElseThrow(() -> new ResourceNotFoundException("No such Answer Sheet found with ID " + answerSheetId));


            // Build prompt template
            TestScorePrompt testScorePrompt =
                    new TestScorePrompt(objectMapper.writeValueAsString(answerSheet.getAnswerList()).replaceAll("&quot;", ""));

            // Render template
            Prompt prompt = StructuredPromptProcessor.toPrompt(testScorePrompt);

            // Call AI API to extract Resume Characteristics
            String json = matchAgent.chat(prompt.toUserMessage().text());

            // Parse return json
            TestScoreDTO testScoreDTO = objectMapper.readValue(json, TestScoreDTO.class);


            // Update Scores
            answerSheet.setScore(testScoreDTO.getScore());
            for(var answer : answerSheet.getAnswerList()) {
                for(var testAnswerScoreDTO : testScoreDTO.getAnswerScores())
                    if(testAnswerScoreDTO.getId().equals(answer.getId())) {
                        answer.setScore(testAnswerScoreDTO.getScore());
                        continue;
                    }
            }

            answerSheetRepository.save(answerSheet);

        } catch (JsonProcessingException e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Override
    public MatchingIndexDTO find(Long jobId, Long resumeId) {
        MatchingIndex matchingIndex =
                matchingIndexRepository
                        .findByJobIdAndResumeId(jobId, resumeId)
                        .orElseThrow(() -> new ResourceNotFoundException("No such MatchingIndex found with jobId %s and resumeId %s".formatted(jobId, resumeId)));
                ;
        // return
        MatchingIndexDTO matchingIndexDTO = MatchingIndexMapper.INSTANCE.mapMatchingIndexToMatchingIndexDTO(matchingIndex);
        matchingIndexDTO.setResumeDTO(ResumeServiceImpl.mapResumeToResumeDTO(matchingIndex.getResume()));
        matchingIndexDTO.setJobResponse(JobMapper.INSTANCE.mapJobToJobResponse(matchingIndex.getJob()));
        return matchingIndexDTO;
    }


    /**
     * Call AI API, extract skills from job description
     *
     * @param jobId
     * @return
     */
    /*public List<ResumeDTO.SkillDTO> extractSkills(Long jobId) {
        // Get Job info
        Job job = jobRepository
                .findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("No such job found with id " + jobId));

        // Get Current login user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        // Check if current user owns this job
        if (!job.getCompany().getUser().getEmail().equals(user.getEmail())) {
            throw new InsufficientAuthenticationException("Sorry, you can not generate question for other user's job!");
        }

        // Build prompt template
        SkillsExtractPrompt createTestPrompt =
                new SkillsExtractPrompt(job.getDescription());

        // Render template
        Prompt prompt = StructuredPromptProcessor.toPrompt(createTestPrompt);

        // Call AI API to generate questions
        String json = matchAgent.chat(prompt.toUserMessage().text());


        try {
            // Parse return json
            List<ResumeDTO.SkillDTO> skillDTOS = Arrays.asList(objectMapper.readValue(json, ResumeDTO.SkillDTO[].class));

            return skillDTOS;

        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }

    }*/

}
