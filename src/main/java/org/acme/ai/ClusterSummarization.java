package org.acme.ai;

import jakarta.enterprise.context.ApplicationScoped;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import org.acme.cluster.ClusterableEmbeddedMessage;
import org.apache.commons.math3.ml.clustering.Cluster;

@ApplicationScoped
public class ClusterSummarization {


    @Inject
    ClusterViewer clusterViewer;

    public void summarize(List<? extends Cluster<ClusterableEmbeddedMessage>> clusters) {

        for (int i = 0; i < clusters.size(); i++) {
            final Cluster<ClusterableEmbeddedMessage> cluster = clusters.get(i);

            final List<ClusterableEmbeddedMessage> clusterPoints = cluster.getPoints();

            String appendedMessages = clusterPoints.stream()
                .map(clusteredMsg ->
                    clusteredMsg.news().title()
                )
                .collect(Collectors.joining(","));

            System.out.println("*************** Cluster " + i + "***************************");
            System.out.println(appendedMessages);
            System.out.println("************************************************************");

        }

        try {
            clusterViewer.showCluster(clusters);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

}
