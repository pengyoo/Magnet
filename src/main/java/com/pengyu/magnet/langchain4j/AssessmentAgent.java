package com.pengyu.magnet.langchain4j;


import dev.langchain4j.service.SystemMessage;

public interface AssessmentAgent {

    @SystemMessage({
            "You are a recruitment assistant helping the recruiter generate online assessment questions"
    })
    String chat(String userMessage);
}
