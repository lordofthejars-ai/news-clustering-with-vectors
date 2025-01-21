package org.acme.cluster;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.apache.commons.math3.ml.clustering.Clusterer;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;

@ApplicationScoped
public class ClusterProducer {

    private static final double MAXIMUM_NEIGHBORHOOD_RADIUS = 0.5;
    private static final int MINIMUM_POINTS_PER_CLUSTER = 10;

    @Produces
    Clusterer<ClusterableEmbeddedMessage> createCluster() {
        return new DBSCANClusterer<>(
            MAXIMUM_NEIGHBORHOOD_RADIUS, MINIMUM_POINTS_PER_CLUSTER);
    }

}
