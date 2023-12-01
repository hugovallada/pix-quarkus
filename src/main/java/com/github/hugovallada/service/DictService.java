package com.github.hugovallada.service;

import java.time.LocalDateTime;
import java.util.Objects;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.github.hugovallada.config.RedisCache;
import com.github.hugovallada.model.qrcode.ChavePix;
import com.github.hugovallada.model.qrcode.TipoChave;
import com.github.hugovallada.model.qrcode.TipoPessoa;

import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class DictService {

    @ConfigProperty(name = "pix.chave")
    private String chave;

    @ConfigProperty(name = "pix.ispb")
    private String ispb;

    @ConfigProperty(name = "pix.cpf")
    private String cpf;

    @ConfigProperty(name = "pix.nome")
    private String nome;

    private RedisCache redisCache;

    public DictService(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    
    public ChavePix buscarChave(String chave) {
        return new ChavePix(
                TipoChave.EMAIL, this.chave, ispb, TipoPessoa.FISICA, cpf, nome, LocalDateTime.now());
    }

    public ChavePix buscarDetalhesChave(String key) {
        var chaveK = buscarChaveCache(key);
        if (Objects.isNull(chaveK)) {
            chaveK = buscarChave(key);
            redisCache.set(key, chaveK);
        }
        return chaveK;
    }

    public ChavePix buscarChaveCache(String key) {
        var chave = redisCache.get(key);
        Log.infof("Chave encontrada no cache %s", chave);
        return chave;
    }

}
