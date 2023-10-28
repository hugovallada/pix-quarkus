package com.github.hugovallada.model.qrcode;

import java.time.LocalDateTime;

public record ChavePix(
        TipoChave tipoChave,
        String chave,
        String ispb,
        TipoPessoa tipoPessoa,
        String cpfCnpj,
        String nome,
        LocalDateTime dataHoraCriacao) {
}
