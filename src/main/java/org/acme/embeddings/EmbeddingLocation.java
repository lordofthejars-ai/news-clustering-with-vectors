package org.acme.embeddings;

import java.nio.file.Path;

public record EmbeddingLocation(Path model, Path tokenizer) {
}
