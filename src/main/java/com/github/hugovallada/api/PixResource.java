package com.github.hugovallada.api;

import java.util.Objects;

import com.github.hugovallada.model.Pix;
import com.github.hugovallada.service.DictService;
import com.github.hugovallada.service.PixService;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/pix")
public class PixResource {

    private final DictService dictService;
    private final PixService pixService;

    public PixResource(DictService dictService, PixService pixService) {
        this.dictService = dictService;
        this.pixService = pixService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/linha")
    public Response gerarLinhaPixCola(final Pix pix) {
        final var chavePix = dictService.buscarChave(pix.chave());
        if (Objects.nonNull(chavePix)) {
            return Response.ok(pixService.gerarLinhaDigitavel(chavePix, pix.valor(), pix.cidadeRemetente())).build();
        }
        return null;
    }

}