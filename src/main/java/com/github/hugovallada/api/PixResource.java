package com.github.hugovallada.api;

import com.github.hugovallada.model.LinhaDigitavel;
import com.github.hugovallada.model.Pix;
import com.github.hugovallada.model.Transaction;
import com.github.hugovallada.service.DictService;
import com.github.hugovallada.service.PixService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponseSchema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Path("/v1/pix")
public class PixResource {

    private final DictService dictService;
    private final PixService pixService;

    public PixResource(DictService dictService, PixService pixService) {
        this.dictService = dictService;
        this.pixService = pixService;
    }

    @Operation(description = "Api para pegar um qrcode apartir do uuid")
    @APIResponseSchema(LinhaDigitavel.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Retorno ok"),
            @APIResponse(responseCode = "400", description = "Bad Request"),
            @APIResponse(responseCode = "404", description = "Não encontrado")
    })
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

    @Operation(description = "Api para pegar um qrcode apartir do uuid")
    @APIResponseSchema(Response.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "Retorno ok"),
            @APIResponse(responseCode = "400", description = "Bad Request"),
            @APIResponse(responseCode = "404", description = "Não encontrado")
    })
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("image/png")
    @Path("/qrcode/{uuid}")
    public Response qrCode(@PathParam("uuid") UUID uuid) throws IOException {
        // TODO: Adicionar tratamento de exceções
        return Response.ok(pixService.gerarQrCode(uuid)).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}/aprovar")
    @PATCH
    @Operation(description = "API responsável por aprovar um pagamento PIX")
    @APIResponseSchema(Transaction.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK"),
            @APIResponse(responseCode = "201", description = "Retorno OK com a transação criada."),
            @APIResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @APIResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @APIResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    public Response aprovarPix(@PathParam("uuid") String uuid) {

        return Response.ok(pixService.aprovarTransacao(uuid).get()).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}/reprovar")
    @DELETE
    @Operation(description = "API responsável por reprovar um pagamento PIX")
    @APIResponseSchema(Transaction.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK"),
            @APIResponse(responseCode = "201", description = "Retorno OK com a transação criada."),
            @APIResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @APIResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @APIResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    public Response reprovarPix(@PathParam("uuid") String uuid) {

        return Response.ok(pixService.reprovarTransacao(uuid).get()).build();
    }

    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{uuid}")
    @GET
    @Operation(description = "API responsável por aprovar um pagamento PIX")
    @APIResponseSchema(Transaction.class)
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK"),
            @APIResponse(responseCode = "201", description = "Retorno OK com a transação criada."),
            @APIResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @APIResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @APIResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    public Response buscarPix(@PathParam("uuid") String uuid) {

        return Response.ok(pixService.findById(uuid)).build();
    }


}
