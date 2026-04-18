/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unipanamericana.gestionproyectosestudiantiles.resources;

import com.unipanamericana.gestionproyectosestudiantiles.model.Entregable;
import com.unipanamericana.gestionproyectosestudiantiles.service.EntregableService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/entregables")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EntregableResources {

    private final EntregableService service = new EntregableService();

    @GET
    public Response listar() {
        List<Entregable> lista = service.listarTodos();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        Entregable entregable = service.buscarPorId(id);
        if (entregable == null)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Entregable no encontrado\"}").build();
        return Response.ok(entregable).build();
    }

    @POST
    public Response crear(Entregable entregable) {
        try {
            Entregable nuevo = service.crear(entregable);
            return Response.status(Response.Status.CREATED).entity(nuevo).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Integer id, Entregable entregable) {
        try {
            Entregable actualizado = service.actualizar(id, entregable);
            return Response.ok(actualizado).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response eliminar(@PathParam("id") Integer id) {
        try {
            service.eliminar(id);
            return Response.ok("{\"mensaje\":\"Entregable eliminado correctamente\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }
}
