package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.acme.news.News;
import org.acme.news.NewsParser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class NewsParserTest {

    @Inject
    NewsParser newsParser;

    @Test
    public void shouldReadNews() throws IOException {

        final Path path = Paths.get("src/main/resources/news/news-titles.txt");
        final List<News> newsList = newsParser.readNews(path);
        Assertions.assertThat(newsList).hasSize(642);
    }

}
