package org.acme.embeddings;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import org.jboss.logging.Logger;

@ApplicationScoped
public class DownloaderEmbeddingModel {

    @Inject
    Logger logger;

    public EmbeddingLocation download(Path baseDir) throws IOException {

        Instant start = Instant.now();

        Path embeddingsDir = baseDir.resolve("embeddings/snowflake");
        Path modelFile = embeddingsDir.resolve("model.onnx");
        Path tokenizerFile = embeddingsDir.resolve("tokenizer.json");

        if (!Files.exists(embeddingsDir)) {

            logger.infof("Model not found locally at %s.", embeddingsDir);

            Files.createDirectories(embeddingsDir);

            String model =
                "https://huggingface.co/Snowflake/snowflake-arctic-embed-m-v2.0/resolve/main/onnx/model_quantized.onnx";
            String tokenizer =
                "https://huggingface.co/Snowflake/snowflake-arctic-embed-m-v2.0/resolve/main/tokenizer.json";

            downloadLargeFile(model, modelFile);
            downloadLargeFile(tokenizer, tokenizerFile);
        }

        Instant end = Instant.now();
        logger.infof("Downloading Models in: %dms%n", end.toEpochMilli() - start.toEpochMilli());

        return new EmbeddingLocation(modelFile, tokenizerFile);
    }

    private void downloadLargeFile(String fileUrl, Path outputFilePath) throws IOException {

        logger.infof("Downloading %s", fileUrl);

        URL url = URI.create(fileUrl).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Check if the connection is successful
        int responseCode = connection.getResponseCode();
        if (responseCode != HttpURLConnection.HTTP_OK) {
            throw new IOException("Failed to download file: HTTP " + responseCode);
        }

        // Input stream to read data from the URL
        try (BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());
             FileOutputStream outputStream = new FileOutputStream(outputFilePath.toAbsolutePath().toFile())) {

            byte[] buffer = new byte[1024 * 1024]; // 1 MB buffer size
            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        connection.disconnect();
    }

}
