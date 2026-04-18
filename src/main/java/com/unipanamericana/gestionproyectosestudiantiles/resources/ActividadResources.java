/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unipanamericana.gestionproyectosestudiantiles.resources;

import com.unipanamericana.gestionproyectosestudiantiles.model.Actividad;
import com.unipanamericana.gestionproyectosestudiantiles.service.ActividadService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/actividades")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ActividadResources {

    private final ActividadService service = new ActividadService();

    @GET
    public Response listar() {
        List<Actividad> lista = service.listarTodos();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        Actividad actividad = service.buscarPorId(id);
        if (actividad == null)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Actividad no encontrada\"}").build();
        return Response.ok(actividad).build();
    }

    @POST
    public Response crear(Actividad actividad) {
        try {
            Actividad nueva = service.crear(actividad);
            return Response.status(Response.Status.CREATED).entity(nueva).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Integer id, Actividad actividad) {
        try {
            Actividad actualizada = service.actualizar(id, actividad);
            return Response.ok(actualizada).build();
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
            return Response.ok("{\"mensaje\":\"Actividad eliminada correctamente\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }
}