package org.acme.visualizer;

import jakarta.enterprise.context.ApplicationScoped;
import org.acme.cluster.ClusterableEmbeddedMessage;
import org.apache.commons.math3.ml.clustering.Cluster;
import smile.manifold.TSNE;
import smile.plot.swing.Point;
import smile.plot.swing.ScatterPlot;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ClusterViewer {

    public void showCluster(List<? extends Cluster<ClusterableEmbeddedMessage>> clusters) throws InterruptedException, InvocationTargetException {

        List<Point> pointsList = new ArrayList<>();
        int numberOfClusters = clusters.size();

        for(int i=0; i< numberOfClusters; i++) {
            Cluster<ClusterableEmbeddedMessage> clusterableEmbeddedMessage = clusters.get(i);
            double[][] points = clusterableEmbeddedMessage.getPoints()
                    .stream()
                    .map(ClusterableEmbeddedMessage::getPoint)
                    .toArray(double[][]::new);

            TSNE tsne = new TSNE(points, 2, 60, 600, 3000);
            double[][] reducedData = tsne.coordinates;
            Point p = Point.of(reducedData, getColor(i, numberOfClusters));
            pointsList.add(p);
        }




    }

    private Color getColor(int cluster, int numberOfClusters) {
        return Color.getHSBColor((float) cluster / numberOfClusters, 0.8f, 0.8f);
    }

}
