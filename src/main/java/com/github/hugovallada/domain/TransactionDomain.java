package com.github.hugovallada.domain;

import com.github.hugovallada.model.LinhaDigitavel;
import com.github.hugovallada.model.Transaction;
import com.github.hugovallada.model.qrcode.ChavePix;
import com.github.hugovallada.repository.TransacaoPixMongoClientRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.Optional;

import static com.github.hugovallada.model.StatusPix.*;

@ApplicationScoped
public class TransactionDomain {

    private final TransacaoPixMongoClientRepository repository;

    public TransactionDomain(TransacaoPixMongoClientRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void adicionarTransacao(final LinhaDigitavel linhaDigitavel, final BigDecimal valor, final ChavePix chave) {
        repository.adicionar(linhaDigitavel, valor, chave);
    }

    public Optional<Transaction> aprovarTransacao(final String uuid) {
        return repository.alterarStatusTransacao(uuid, APPROVED);
    }

    public Optional<Transaction> reprovarTransacao(final String uuid) {
        return repository.alterarStatusTransacao(uuid, REPROVED);
    }

    public Optional<Transaction> iniciarProcessamento(final String uuid) {
        return repository.alterarStatusTransacao(uuid, IN_PROCESS);
    }

    public Optional<Transaction> findById(final String uuid) {
        Optional<Document> optionalDocument = repository.findOne(uuid);
        return optionalDocument.map(TransactionConverterApply::apply);
    }


}
