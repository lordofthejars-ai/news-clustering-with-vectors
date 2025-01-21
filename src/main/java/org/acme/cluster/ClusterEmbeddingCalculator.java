package org.acme.cluster;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.acme.news.News;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ClusterEmbeddingCalculator {

    @Inject
    EmbeddingModel embeddingModel;

    @Inject
    Logger logger;

    public List<ClusterableEmbeddedMessage> calculate(List<News> newsList) {

        Instant start = Instant.now();

        List<ClusterableEmbeddedMessage> clusterableEmbeddedMessageList = new ArrayList<>();

        for(News news : newsList) {
            final TextSegment textSegment = TextSegment.from(news.title());
            final Embedding content = embeddingModel.embed(textSegment).content();
            ClusterableEmbeddedMessage clusterableEmbeddedMessage =
                new ClusterableEmbeddedMessage(news,
                    content.vectorAsList().stream().mapToDouble(Float::doubleValue).toArray()
                );

            clusterableEmbeddedMessageList.add(clusterableEmbeddedMessage);
        }

        Instant end = Instant.now();
        logger.infof("Embedding Vectors calculator in: %dms%n", end.toEpochMilli() - start.toEpochMilli());

        return clusterableEmbeddedMessageList;
    }

}
