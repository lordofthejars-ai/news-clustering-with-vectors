package org.acme.cluster;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Instant;
import java.util.List;

import org.acme.visualizer.ClusterViewer;
import org.acme.news.News;
import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.Clusterer;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ClusterService {

    @Inject
    Clusterer<ClusterableEmbeddedMessage> cluster;

    @Inject
    ClusterEmbeddingCalculator clusterEmbeddingCalculator;

    @Inject
    ClusterViewer clusterViewer;

    @Inject
    Logger logger;

    public List<? extends Cluster<ClusterableEmbeddedMessage>> cluster(List<News> news) {

        logger.infof("Number of news to classify %d", news.size());

        final List<ClusterableEmbeddedMessage> points = clusterEmbeddingCalculator.calculate(news);

        Instant start = Instant.now();

        final List<? extends Cluster<ClusterableEmbeddedMessage>> clusters = cluster.cluster(points);

        Instant end = Instant.now();
        logger.infof("Clustered messages in %d clusters in: %dms%n",
            clusters.size(),
            end.toEpochMilli() - start.toEpochMilli());

        return clusters;
    }

}
