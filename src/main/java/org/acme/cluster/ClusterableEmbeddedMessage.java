package org.acme.cluster;

import org.acme.news.News;
import org.apache.commons.math3.ml.clustering.Clusterable;

public record ClusterableEmbeddedMessage(News news, double[] embedding) implements Clusterable {
    @Override
    public double[] getPoint() {
        return embedding;
    }
}
