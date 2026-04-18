/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unipanamericana.gestionproyectosestudiantiles.resources;

import com.unipanamericana.gestionproyectosestudiantiles.model.Proyecto;
import com.unipanamericana.gestionproyectosestudiantiles.service.ProyectoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/proyectos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProyectoResources {

    private final ProyectoService service = new ProyectoService();

    @GET
    public Response listar() {
        List<Proyecto> lista = service.listarTodos();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        Proyecto proyecto = service.buscarPorId(id);
        if (proyecto == null)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Proyecto no encontrado\"}").build();
        return Response.ok(proyecto).build();
    }

    @POST
    public Response crear(Proyecto proyecto) {
        try {
            Proyecto nuevo = service.crear(proyecto);
            return Response.status(Response.Status.CREATED).entity(nuevo).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Integer id, Proyecto proyecto) {
        try {
            Proyecto actualizado = service.actualizar(id, proyecto);
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
            return Response.ok("{\"mensaje\":\"Proyecto eliminado correctamente\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }
}
