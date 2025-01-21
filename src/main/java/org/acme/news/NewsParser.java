package org.acme.news;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.List;
import org.jboss.logging.Logger;

@ApplicationScoped
public class NewsParser {

    @Inject
    ObjectMapper objectMapper;

    @Inject
    Logger logger;

    public List<News> readNews(InputStream inputStream) throws IOException {

        Instant start = Instant.now();
        List<News> news =  objectMapper.readValue(inputStream, new TypeReference<>() {
        });

        Instant end = Instant.now();
        logger.infof("Parsing News in: %dms%n", end.toEpochMilli() - start.toEpochMilli());

        return news;
    }

}
