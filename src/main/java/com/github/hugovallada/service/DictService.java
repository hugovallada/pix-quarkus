package com.github.hugovallada.service;

import java.time.LocalDateTime;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.github.hugovallada.model.qrcode.ChavePix;
import com.github.hugovallada.model.qrcode.TipoChave;
import com.github.hugovallada.model.qrcode.TipoPessoa;

import jakarta.enterprise.context.ApplicationScoped;

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

    public ChavePix buscarChave(String chave) {
        return new ChavePix(
                TipoChave.EMAIL, this.chave, ispb, TipoPessoa.FISICA, cpf, nome, LocalDateTime.now());
    }

}
