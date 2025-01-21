package org.acme.news;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class NewsProducer {

    private static final List<String> newsFiles = List.of("src/main/resources/news/news-titles.txt", "src/main/resources/news/news2-titles.txt");

    @Inject
    NewsParser newsParser;

    public List<News> readNews() {
        return newsFiles.stream()
            .map(Paths::get)
            .map(p -> newsParser.readNews(p))
            .flatMap(List::stream)
            .toList();
    }

}
