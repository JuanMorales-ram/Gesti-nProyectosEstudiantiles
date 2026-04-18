/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unipanamericana.gestionproyectosestudiantiles.resources;

import com.unipanamericana.gestionproyectosestudiantiles.model.Docente;
import com.unipanamericana.gestionproyectosestudiantiles.service.DocenteService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/docentes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DocenteResources {

    private final DocenteService service = new DocenteService();

    @GET
    public Response listar() {
        List<Docente> lista = service.listarTodos();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Integer id) {
        Docente docente = service.buscarPorId(id);
        if (docente == null)
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"Docente no encontrado\"}").build();
        return Response.ok(docente).build();
    }

    @POST
    public Response crear(Docente docente) {
        try {
            Docente nuevo = service.crear(docente);
            return Response.status(Response.Status.CREATED).entity(nuevo).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response actualizar(@PathParam("id") Integer id, Docente docente) {
        try {
            Docente actualizado = service.actualizar(id, docente);
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
            return Response.ok("{\"mensaje\":\"Docente eliminado correctamente\"}").build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\":\"" + e.getMessage() + "\"}").build();
        }
    }
}
