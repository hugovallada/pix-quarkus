package com.github.hugovallada.service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.github.hugovallada.model.LinhaDigitavel;
import com.github.hugovallada.model.qrcode.ChavePix;
import com.github.hugovallada.model.qrcode.DadosEnvioPix;
import com.github.hugovallada.model.qrcode.QRCodePix;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PixService {

    public static final String QRCODE_PATH = "/tmp/qrcode";

    public LinhaDigitavel gerarLinhaDigitavel(final ChavePix chavePix, BigDecimal valor, String cidadeRemetente) {
        final var qrCode = new QRCodePix(new DadosEnvioPix(chavePix.nome(), chavePix.chave(), valor, cidadeRemetente));
        final var uuid = UUID.randomUUID();
        final var imagePath = QRCODE_PATH + uuid.toString() + ".png";
        qrCode.save(Path.of(imagePath));
        // TODO: Implementar Cache
        final var qrCodeString = qrCode.toString();
        return new LinhaDigitavel(qrCodeString, uuid);
    }

    public BufferedInputStream gerarQrCode(final UUID uuid) throws IOException {
        // TODO: Recuperar da chave
        var imagePath = QRCODE_PATH + uuid.toString() + ".png";
        try {
            return new BufferedInputStream(new FileInputStream(imagePath));
        } finally {
            Files.delete(Paths.get(imagePath));
        }
    }

}
