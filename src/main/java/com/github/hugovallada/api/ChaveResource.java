package com.github.hugovallada.api;

import com.github.hugovallada.service.DictService;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1/chaves")
public class ChaveResource {

    private final DictService dictService;

    public ChaveResource(DictService dictService) {
        this.dictService = dictService;
    }

    @GET
    @Path("/{chave}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscar(@PathParam("chave") String key) {
        return Response.ok(dictService.buscarDetalhesChave(key)).build();
    }

}
