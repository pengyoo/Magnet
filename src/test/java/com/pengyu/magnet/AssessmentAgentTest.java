package com.pengyu.magnet;

import com.pengyu.magnet.langchan4j.AssessmentAgent;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.input.structured.StructuredPrompt;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class AssessmentAgentTest {

    @Autowired
    AssessmentAgent agent;
    private String testPaperTemplate = """
            Please generate {{generalNumber}} technical interview questions based on the technical requirements mentioned in the job description, and {{languageNumber}} interview questions about {{language}}. These questions should assess a job seeker's knowledge. Return only pure JSON data as shown in the example below. Set jobId to {{jobId}}, and place the generated questions in the questionList field with standard answers in the standardAnswer field.
            Job Description: {{jobDescription}}
            """;

    @Test
    void testGenerateTestPaper() {

        PromptTemplate promptTemplate = PromptTemplate.from(testPaperTemplate);

        Map<String, Object> variables = new HashMap<>();
        variables.put("generalNumber", "5");
        variables.put("languageNumber", "5");
        variables.put("language", "Java");
        variables.put("jobId", "1");
        variables.put("jobDescription", "When you join Verizon\n" +
                "\n" +
                "Verizon is one of the world's leading providers of technology and communications services, transforming the way we connect around the world. We're a human network that reaches across the globe and works behind the scenes. We anticipate, lead, and believe that listening is where learning begins. In crisis and in celebration, we come together-lifting up our communities and striving to make an impact to move the world forward. If you're fueled by purpose, and powered by persistence, explore a career with us. Here, you'll discover the rigor it takes to make a difference and the fulfillment that comes with living the #NetworkLife.\n" +
                "\n" +
                "When you join Verizon\n" +
                "\n" +
                "Verizon is a leading provider of technology, communications, information and entertainment products, transforming the way we connect across the globe. We're a diverse network of people driven by our ambition and united in our shared purpose to shape a better future. Here, we have the ability to learn and grow at the speed of technology, and the space to create within every role. Together, we are moving the world forward - and you can too. Dream it. Build it. Do it here.\n" +
                "\n" +
                "What you will do\n" +
                "\n" +
                "You will work with our cross functional team in the Connected Device Experience group which is made up of engineers who develop strategic features for our Verizon Connect Reveal. You will have the opportunity to work in the latest .NET stacks, operating in a cloud based infrastructure utilizing all the best practice software development practices that we'll teach you. You'll get to build a modern UI experience for our customers also. You'll become a contributing engineer quite quickly as the team will help you train up to a good level, quite quickly. You'll have exposure to both modern front end and modern back-end technologies and you'll quickly be shipping features to our customers.Work with our team of engineers on latest technologies to develop, automate and deploy video features for customers.\n" +
                "\n" +
                "What we're looking for...\n" +
                "\n" +
                "You'll need to have:\n" +
                "Currently pursuing a Bachelor's or Master's Degree in computer science or related course.\n" +
                "Ability to complete our 6-month internship that takes place in February/March 2024.\n" +
                "Knowledge and/or experience in areas such as Object Oriented Programming, Web Development, Unit Testing, Software Design Patterns, Algorithms and Data Structures.\n" +
                "Thrive to learn, collaborate & share\n" +
                "\n" +
                "Even better if you have:\n" +
                "Knowledge or familiarity with databases, SDLC, and cloud services.\n" +
                "Programming experience with C#, Java, Python or relevant programming languages.\n" +
                "Projects that showcase your work - both University curriculum and personal projects.\n" +
                "A strong work ethic and willingness to learn, with demonstrated experience of that.\n" +
                "The ability to work well when given instruction but can also work autonomously.\n" +
                "Previous work experience, related or unrelated to your degree,or volunteering experience.\n" +
                "Excellent interpersonal skills, you are motivated and have a positive attitude to work.\n" +
                "\n" +
                "Where you'll be working\n" +
                "In this hybrid role, you'll have a defined work location that includes work from home and assigned office days set by your manager.\n" +
                "\n" +
                "Scheduled Weekly Hours\n" +
                "37.5\n" +
                "\n" +
                "Diversity and Inclusion\n" +
                "\n" +
                "We're proud to be an equal opportunity employer. At Verizon, we know that diversity makes us stronger. We are committed to a collaborative, inclusive environment that encourages authenticity and fosters a sense of belonging. We strive for everyone to feel valued, connected, and empowered to reach their potential and contribute their best. Check out our diversity and inclusion page to learn more.");

        Prompt prompt = promptTemplate.apply(variables);

        String agentAnswer = agent.chat(prompt.text());
        System.out.println(agentAnswer);
    }



    @Test
    public void test2(){
        CreateTestPrompt createTestPrompt = new CreateTestPrompt();
        createTestPrompt.generalNumber = 3;
        createTestPrompt.languageNumber = 5;
        createTestPrompt.language = "Java";
        createTestPrompt.jobId = 1;
        createTestPrompt.jobDescription = "When you join Verizon\n" +
                "\n" +
                "Verizon is one of the world's leading providers of technology and communications services, transforming the way we connect around the world. We're a human network that reaches across the globe and works behind the scenes. We anticipate, lead, and believe that listening is where learning begins. In crisis and in celebration, we come together-lifting up our communities and striving to make an impact to move the world forward. If you're fueled by purpose, and powered by persistence, explore a career with us. Here, you'll discover the rigor it takes to make a difference and the fulfillment that comes with living the #NetworkLife.\n" +
                "\n" +
                "When you join Verizon\n" +
                "\n" +
                "Verizon is a leading provider of technology, communications, information and entertainment products, transforming the way we connect across the globe. We're a diverse network of people driven by our ambition and united in our shared purpose to shape a better future. Here, we have the ability to learn and grow at the speed of technology, and the space to create within every role. Together, we are moving the world forward - and you can too. Dream it. Build it. Do it here.\n" +
                "\n" +
                "What you will do\n" +
                "\n" +
                "You will work with our cross functional team in the Connected Device Experience group which is made up of engineers who develop strategic features for our Verizon Connect Reveal. You will have the opportunity to work in the latest .NET stacks, operating in a cloud based infrastructure utilizing all the best practice software development practices that we'll teach you. You'll get to build a modern UI experience for our customers also. You'll become a contributing engineer quite quickly as the team will help you train up to a good level, quite quickly. You'll have exposure to both modern front end and modern back-end technologies and you'll quickly be shipping features to our customers.Work with our team of engineers on latest technologies to develop, automate and deploy video features for customers.\n" +
                "\n" +
                "What we're looking for...\n" +
                "\n" +
                "You'll need to have:\n" +
                "Currently pursuing a Bachelor's or Master's Degree in computer science or related course.\n" +
                "Ability to complete our 6-month internship that takes place in February/March 2024.\n" +
                "Knowledge and/or experience in areas such as Object Oriented Programming, Web Development, Unit Testing, Software Design Patterns, Algorithms and Data Structures.\n" +
                "Thrive to learn, collaborate & share\n" +
                "\n" +
                "Even better if you have:\n" +
                "Knowledge or familiarity with databases, SDLC, and cloud services.\n" +
                "Programming experience with C#, Java, Python or relevant programming languages.\n" +
                "Projects that showcase your work - both University curriculum and personal projects.\n" +
                "A strong work ethic and willingness to learn, with demonstrated experience of that.\n" +
                "The ability to work well when given instruction but can also work autonomously.\n" +
                "Previous work experience, related or unrelated to your degree,or volunteering experience.\n" +
                "Excellent interpersonal skills, you are motivated and have a positive attitude to work.\n" +
                "\n" +
                "Where you'll be working\n" +
                "In this hybrid role, you'll have a defined work location that includes work from home and assigned office days set by your manager.\n" +
                "\n" +
                "Scheduled Weekly Hours\n" +
                "37.5\n" +
                "\n" +
                "Diversity and Inclusion\n" +
                "\n" +
                "We're proud to be an equal opportunity employer. At Verizon, we know that diversity makes us stronger. We are committed to a collaborative, inclusive environment that encourages authenticity and fosters a sense of belonging. We strive for everyone to feel valued, connected, and empowered to reach their potential and contribute their best. Check out our diversity and inclusion page to learn more.";
        Prompt prompt = StructuredPromptProcessor.toPrompt(createTestPrompt);
//        System.out.println(prompt.toUserMessage().text());
        String string = agent.chat(prompt.toUserMessage().text());
        System.out.println(string);
    }

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
    static class CreateTestPrompt {

        private int generalNumber;
        private int languageNumber;
        private String language;
        private String jobDescription;
        private int jobId;
    }
}
