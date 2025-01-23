package org.acme.ai;

import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface SummarizerService {

    @SystemMessage("""
         Summarize the following list of news headlines in one simple description.
         Don't give a full sentence saying the headlines are about a topic,
         just give the topic directly in 7 words or less,
         without mentioning the messages are news, be concise .
        """)
    String summarize(String appendedMessages);

}
