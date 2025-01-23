package org.acme.visualizer;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import org.acme.ai.SummarizerService;
import org.acme.cluster.ClusterableEmbeddedMessage;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ClusterSummarization {

    @Inject
    Logger logger;

    @Inject
    SummarizerService summarizerService;

    public String createD3Summarize(List<? extends Cluster<ClusterableEmbeddedMessage>> clusters) {

        StringBuilder dataTemplate = new StringBuilder();

        for (final Cluster<ClusterableEmbeddedMessage> cluster : clusters) {
            final List<ClusterableEmbeddedMessage> clusterPoints = cluster.getPoints();

            String appendedTitles = clusterPoints.stream()
                    .map(c -> c.news().title())
                    .collect(Collectors.joining("\n"));

            Instant start = Instant.now();

            String clusterSummary = summarizerService.summarize(appendedTitles);

            Instant end = Instant.now();
            logger.infof("Summaries generated in: %dms%n",
                    end.toEpochMilli() - start.toEpochMilli());

            dataTemplate
                    .append("{name: \"")
                    .append(clusterSummary
                            .replace("\"", "\\\"")
                            .replace("\n", " "))
                    .append("\", value: ")
                    .append(clusterPoints.size())
                    .append("},\n    ");

        }

        return dataTemplate.toString().trim();

    }

}
