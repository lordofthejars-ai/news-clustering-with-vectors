package org.acme.news;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class NewsProducer {

    private static final List<String> newsFiles = List.of("/news/bbc-news.json", "/news/cnn.json", "/news/fox-news.json");

    @Inject
    NewsParser newsParser;

    public List<News> readNews() {
        return newsFiles.stream()
            .map(NewsProducer.class::getResourceAsStream)
            .map(inputStream -> {
                try {
                    return newsParser.readNews(inputStream);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            })
            .flatMap(List::stream)
            .toList();
    }

}
