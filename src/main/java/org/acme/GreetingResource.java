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
}
