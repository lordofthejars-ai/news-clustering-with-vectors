package org.acme;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.RawString;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.annotation.Blocking;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.visualizer.ClusterSummarization;
import org.acme.cluster.ClusterService;
import org.acme.cluster.ClusterableEmbeddedMessage;
import org.acme.news.News;
import org.acme.news.NewsProducer;
import org.apache.commons.math3.ml.clustering.Cluster;

@Path("/")
public class NewsClusteringResource {

    @Inject
    NewsProducer newsProducer;

    @Inject
    ClusterService clusterService;

    @Inject
    ClusterSummarization clusterSummarization;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance index(RawString data);
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    @Blocking

    public TemplateInstance cluster() {
        final List<News> newsList = newsProducer.readNews();
        final List<? extends Cluster<ClusterableEmbeddedMessage>> clusters = clusterService.cluster(newsList);
        String data = clusterSummarization.createD3Summarize(clusters);
        return Templates.index(new RawString(data));
    }
}
