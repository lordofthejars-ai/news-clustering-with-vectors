package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import java.io.IOException;
import java.io.InputStream;
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

        final InputStream resourceAsStream = NewsParser.class.getResourceAsStream("/news/cnn.json");
        final List<News> newsList = newsParser.readNews(resourceAsStream);
        Assertions.assertThat(newsList).hasSize(99);
    }

}
