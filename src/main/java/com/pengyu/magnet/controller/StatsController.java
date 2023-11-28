package com.pengyu.magnet.controller;

import com.pengyu.magnet.dto.LinearStatisticsDataResponse;
import com.pengyu.magnet.dto.LinearStatisticsResponse;
import com.pengyu.magnet.repository.UserRepository;
import com.pengyu.magnet.service.JobApplicationService;
import com.pengyu.magnet.service.compnay.CompanyService;
import com.pengyu.magnet.service.compnay.JobService;
import com.pengyu.magnet.service.resume.ResumeService;
import com.pengyu.magnet.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stats")
public class StatsController {

    private final CompanyService companyService;
    private final ResumeService resumeService;
    private final JobService jobService;
    private final JobApplicationService jobApplicationService;
    private final UserService userService;

    @GetMapping
    public List<Map<String,String>> getStats(){
        List<Map<String,String>> list = new ArrayList<>();

        Map<String, String> user = new HashMap<>();
        user.put("title", "user");
        user.put("value", String.valueOf(userService.count()));
        list.add(user);

        Map<String, String> company = new HashMap<>();
        company.put("title", "company");
        company.put("value", String.valueOf(companyService.count()));
        list.add(company);

        Map<String, String> job = new HashMap<>();
        job.put("title", "Job");
        job.put("value", String.valueOf(jobService.count()));
        list.add(job);

        Map<String, String> resume = new HashMap<>();
        resume.put("title", "Resume");
        resume.put("value", String.valueOf(resumeService.count()));
        list.add(resume);

        Map<String, String> application = new HashMap<>();
        application.put("title", "Application");
        application.put("value", String.valueOf(jobApplicationService.count()));
        list.add(application);

        return list;
    }

    @GetMapping("/linear")
    public LinearStatisticsResponse mock() {
        LinearStatisticsDataResponse userCounts =
                new LinearStatisticsDataResponse("User", userService.getRegistrationCounts(), 0.4, "#F03E3E", "#F03E3E");
        LinearStatisticsDataResponse companyCounts =
                new LinearStatisticsDataResponse("Company", companyService.getCompanyCounts(), 0.4, "#AE3EC9", "#AE3EC9");
        LinearStatisticsDataResponse resumeCounts =
                new LinearStatisticsDataResponse("Resume", resumeService.getResumeCounts(), 0.4, "#1C7ED6", "#1C7ED6");
        LinearStatisticsDataResponse jobCounts =
                new LinearStatisticsDataResponse("Job", jobService.getJobCounts(), 0.4, "#F08C00", "#F08C00");
        LinearStatisticsDataResponse applicationCounts =
                new LinearStatisticsDataResponse("Job Application", jobApplicationService.getJobApplicationCounts(), 0.4, "#099268", "#099268");


        LinearStatisticsResponse list = new LinearStatisticsResponse();
        list.getDatasets().add(userCounts);
        list.getDatasets().add(companyCounts);
        list.getDatasets().add(resumeCounts);
        list.getDatasets().add(jobCounts);
        list.getDatasets().add(applicationCounts);
        return list;
    }

}
