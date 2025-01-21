package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;
import org.acme.ai.ClusterSummarization;
import org.acme.cluster.ClusterService;
import org.acme.cluster.ClusterableEmbeddedMessage;
import org.acme.news.News;
import org.acme.news.NewsProducer;
import org.apache.commons.math3.ml.clustering.Cluster;
import smile.manifold.TSNE;
import smile.plot.swing.ScatterPlot;

@Path("/hello")
public class GreetingResource {

    @Inject
    NewsProducer newsProducer;

    @Inject
    ClusterService clusterService;

    @Inject
    ClusterSummarization clusterSummarization;

    @GET
    @Path("/cluster")
    public void cluster() {
        final List<News> newsList = newsProducer.readNews();
        final List<? extends Cluster<ClusterableEmbeddedMessage>> clusters = clusterService.cluster(newsList);
        clusterSummarization.summarize(clusters);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() throws InterruptedException, InvocationTargetException {

        // Generate synthetic data: 100 samples, each with 700 dimensions
        int numSamples = 100;
        int originalDimension = 700;
        int reducedDimension = 3;

        double[][] data = generateRandomData(numSamples, originalDimension);

        // Apply t-SNE to reduce dimensionality
        System.out.println("Running t-SNE to reduce dimensionality...");
        TSNE tsne = new TSNE(data, reducedDimension, 30, 200, 1000);
        double[][] reducedData = tsne.coordinates;

        // Print a few reduced points
        System.out.println("First 5 points after reduction:");
        for (int i = 0; i < Math.min(5, reducedData.length); i++) {
            System.out.printf("Point %d: [%f, %f, %f]%n", i, reducedData[i][0], reducedData[i][1], reducedData[i][2]);
        }

        // Visualize the 3D reduced data as a scatter plot (Smile supports 2D visualization)
        System.out.println("Plotting 2D projection of t-SNE output...");
        var canvas = ScatterPlot.of(reducedData).canvas();
        canvas.window();

        return "Hello from Quarkus REST";
    }

    private static double[][] generateRandomData(int numSamples, int dimensions) {
        Random random = new Random();
        double[][] data = new double[numSamples][dimensions];

        for (int i = 0; i < numSamples; i++) {
            for (int j = 0; j < dimensions; j++) {
                data[i][j] = random.nextDouble();
            }
        }

        return data;
    }
}
