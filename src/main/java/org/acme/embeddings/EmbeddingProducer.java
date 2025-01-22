package org.acme.embeddings;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.OnnxEmbeddingModel;
import dev.langchain4j.model.embedding.onnx.PoolingMode;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.jboss.logging.Logger;

@ApplicationScoped
public class EmbeddingProducer {

    private Path modelPath;
    private Path tokenizerPath;

    @Inject
    DownloaderEmbeddingModel downloaderEmbeddingModel;

    @Inject
    Logger logger;

    @Startup
    void downloadModel() throws IOException {
        /**Path home = Paths.get(System.getProperty("user.home"));
        final EmbeddingLocation embeddingLocation = downloaderEmbeddingModel.download(home);
        this.modelPath = embeddingLocation.model();
        this.tokenizerPath = embeddingLocation.tokenizer();**/
    }

    @Produces
    EmbeddingModel create() {
        //EmbeddingModel embeddingModel = new OnnxEmbeddingModel(modelPath, tokenizerPath, PoolingMode.MEAN);
        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();
        //logger.infof("Model %s with dimension %d.", modelPath.getFileName(), embeddingModel.dimension());

        return embeddingModel;
    }

}
