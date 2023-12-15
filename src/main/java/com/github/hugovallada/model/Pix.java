package com.github.hugovallada.model;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.math.BigDecimal;

public record Pix(
        @Schema(description = "Chave cadastrada do recebedor")
        String chave,

        @Schema(description = "Valor do pix")
        BigDecimal valor,

        @Schema(description = "Cidade emitente do recebedor")
        String cidadeRemetente
) {
}
