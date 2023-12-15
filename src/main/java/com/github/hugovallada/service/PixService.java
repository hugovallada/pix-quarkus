package com.github.hugovallada.service;

import com.github.hugovallada.domain.TransactionDomain;
import com.github.hugovallada.model.LinhaDigitavel;
import com.github.hugovallada.model.Transaction;
import com.github.hugovallada.model.qrcode.ChavePix;
import com.github.hugovallada.model.qrcode.DadosEnvioPix;
import com.github.hugovallada.model.qrcode.QRCodePix;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class PixService {

    public static final String QRCODE_PATH = "/tmp/qrcode";

    private final TransactionDomain transactionDomain;

    public PixService(TransactionDomain transactionDomain) {
        this.transactionDomain = transactionDomain;
    }

    public LinhaDigitavel gerarLinhaDigitavel(final ChavePix chavePix, BigDecimal valor, String cidadeRemetente) {
        final var qrCode = new QRCodePix(new DadosEnvioPix(chavePix.nome(), chavePix.chave(), valor, cidadeRemetente));
        final var uuid = UUID.randomUUID();
        final var imagePath = QRCODE_PATH + uuid + ".png";
        qrCode.save(Path.of(imagePath));
        final var qrCodeString = qrCode.toString();
        return new LinhaDigitavel(qrCodeString, uuid);
    }

    public BufferedInputStream gerarQrCode(final UUID uuid) throws IOException {
        var imagePath = QRCODE_PATH + uuid.toString() + ".png";
        try {
            return new BufferedInputStream(new FileInputStream(imagePath));
        } finally {
            Files.delete(Paths.get(imagePath));
        }
    }

    public Optional<Transaction> aprovarTransacao(final String uuid) {
        return transactionDomain.aprovarTransacao(uuid);
    }

    public Optional<Transaction> reprovarTransacao(final String uuid) {
        return transactionDomain.reprovarTransacao(uuid);
    }

    public Optional<Transaction> findById(final String uuid) {
        return transactionDomain.findById(uuid);
    }

}
