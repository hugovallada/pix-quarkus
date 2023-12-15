package com.github.hugovallada.repository;

import com.github.hugovallada.model.LinhaDigitavel;
import com.github.hugovallada.model.StatusPix;
import com.github.hugovallada.model.Transaction;
import com.github.hugovallada.model.qrcode.ChavePix;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.Optional;

public interface TransactionRepository {

    void adicionar(final LinhaDigitavel linhaDigitavel, final BigDecimal valor, final ChavePix chave);

    Optional<Transaction> alterarStatusTransacao(final String uuid, final StatusPix statusPix);

    Optional<Document> findOne(final String uuid);

}
