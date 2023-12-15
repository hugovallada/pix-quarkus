package com.github.hugovallada.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.UUID;

public record LinhaDigitavel(
        @Schema(description = "Linha digitavel")
        String linha,

        @Schema(description = "UUID")
        UUID uuid) {
}
