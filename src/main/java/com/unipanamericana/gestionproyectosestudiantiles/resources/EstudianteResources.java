/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unipanamericana.gestionproyectosestudiantiles.resources;

import com.unipanamericana.gestionproyectosestudiantiles.model.Estudiante;
import com.unipanamericana.gestionproyectosestudiantiles.service.EstudianteService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/estudiantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EstudianteResources {

    private final EstudianteService service = new EstudianteService();

    @GET
    public Response listar() {
        List<Estudiante> lista = service.listarTodos();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        Estudiante estudiante = service.buscarPorId(id);
        if (estudiante == null)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Estudiante no encontrado\"}").build();
        return Response.ok(estudiante).build();
    }

    @POST
    public Response crear(Estudiante estudiante) {
        try {
            Estudiante nuevo = service.crear(estudiante);
            return Response.status(Response.Status.CREATED).entity(nuevo).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Integer id, Estudiante estudiante) {
        try {
            Estudiante actualizado = service.actualizar(id, estudiante);
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
            return Response.ok("{\"mensaje\":\"Estudiante eliminado correctamente\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }
}