package com.pengyu.magnet.langchan4j;


import dev.langchain4j.service.SystemMessage;

public interface MatchAgent {

    @SystemMessage({
            "You are a recruitment assistant helping the recruiter match the job seekers and positions."
    })
    String chat(String userMessage);
}
