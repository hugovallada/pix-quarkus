package com.github.hugovallada.repository;

import com.github.hugovallada.domain.TransactionConverterApply;
import com.github.hugovallada.model.LinhaDigitavel;
import com.github.hugovallada.model.StatusPix;
import com.github.hugovallada.model.Transaction;
import com.github.hugovallada.model.qrcode.ChavePix;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import jakarta.enterprise.context.ApplicationScoped;
import org.bson.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.github.hugovallada.domain.TransactionConverterApply.*;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.ReturnDocument.AFTER;

@ApplicationScoped
public class TransacaoPixMongoClientRepository implements TransactionRepository {

    public static final String AMERICA_SAO_PAULO = "America/Sao_Paulo";
    private final MongoClient mongoClient;

    public TransacaoPixMongoClientRepository(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    public void adicionar(LinhaDigitavel linhaDigitavel, BigDecimal valor, ChavePix chave) {
        final var document = new Document();
        document.append(ID, linhaDigitavel.uuid());
        document.append(VALOR, valor);
        document.append(TIPO_CHAVE, chave.tipoChave());
        document.append(CHAVE, chave.chave());
        document.append(LINHA, linhaDigitavel.linha());
        document.append(DATA, LocalDateTime.now(ZoneId.of(AMERICA_SAO_PAULO)));
        final var collection = getCollection();
        collection.insertOne(document);
    }

    private MongoCollection<Document> getCollection() {
        return mongoClient.getDatabase("pix").getCollection("pix");
    }

    @Override
    public Optional<Transaction> alterarStatusTransacao(String uuid, StatusPix statusPix) {
        final var optionalDocument = findOne(uuid);
        if (optionalDocument.isEmpty()) {
            return Optional.empty();
        }
        var document = optionalDocument.get();
        var opts = new FindOneAndReplaceOptions().upsert(false).returnDocument(AFTER);
        document.merge(STATUS, statusPix, (a, b) -> b);
        final var replace = getCollection().findOneAndReplace(eq(ID, uuid), document, opts);
        assert replace != null;
        return Optional.of(TransactionConverterApply.apply(replace));
    }

    @Override
    public Optional<Document> findOne(String uuid) {
        final var filter = eq(ID, uuid);
        FindIterable<Document> documents = getCollection().find(filter);
        return StreamSupport.stream(documents.spliterator(), false)
                .findFirst();
    }
}
