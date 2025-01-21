package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;
import org.acme.embeddings.DownloaderEmbeddingModel;
import org.acme.embeddings.EmbeddingLocation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@QuarkusTest
public class DownloaderEmbeddingModelTest {

    @Inject
    DownloaderEmbeddingModel downloaderEmbeddingModel;

    @Test
    public void shouldDownloadModel(@TempDir Path tempDir) throws IOException {

        final EmbeddingLocation download = downloaderEmbeddingModel.download(tempDir);

        Assertions.assertThat(download.model()).isRegularFile().isNotEmptyFile();
        Assertions.assertThat(download.tokenizer()).isRegularFile().isNotEmptyFile();
    }

}
