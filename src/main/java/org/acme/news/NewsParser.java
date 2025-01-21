package org.acme.news;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jboss.logging.Logger;

@ApplicationScoped
public class NewsParser {

    @Inject
    ObjectMapper objectMapper;

    @Inject
    Logger logger;

    private static final String regex = "\"([^\"]*)\"";

    public List<News> readNews(Path file) {

        List<News> news = new ArrayList<>();

        Instant start = Instant.now();

        String content = null;
        try {
            content = Files.readString(file);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        // Compile the pattern
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            String quotedString = matcher.group();
            news.add(new News(quotedString));
        }

        Instant end = Instant.now();
        logger.infof("Parsing News in: %dms%n", end.toEpochMilli() - start.toEpochMilli());

        return news;
    }

}
