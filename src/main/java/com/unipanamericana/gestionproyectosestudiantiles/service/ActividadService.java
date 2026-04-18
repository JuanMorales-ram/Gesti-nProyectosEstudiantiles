/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unipanamericana.gestionproyectosestudiantiles.service;

import com.unipanamericana.gestionproyectosestudiantiles.model.Actividad;
import com.unipanamericana.gestionproyectosestudiantiles.model.Proyecto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class ActividadService {

    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public List<Actividad> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT a FROM Actividad a", Actividad.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Actividad buscarPorId(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Actividad.class, id);
        } finally {
            em.close();
        }
    }

    public Actividad crear(Actividad actividad) {
        if (actividad.getNombre() == null || actividad.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre de la actividad es obligatorio");
        if (actividad.getFechaEntrega() == null)
            throw new IllegalArgumentException("La fecha de entrega es obligatoria");
        if (actividad.getProyecto() == null || actividad.getProyecto().getIdProyecto() == null)
            throw new IllegalArgumentException("El proyecto es obligatorio");

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Proyecto proyecto = em.find(Proyecto.class, actividad.getProyecto().getIdProyecto());
            if (proyecto == null) throw new IllegalArgumentException("Proyecto no encontrado");
            actividad.setProyecto(proyecto);
            em.persist(actividad);
            em.getTransaction().commit();
            return actividad;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Actividad actualizar(Integer id, Actividad datos) {
        EntityManager em = emf.createEntityManager();
        try {
            Actividad actividad = em.find(Actividad.class, id);
            if (actividad == null) throw new IllegalArgumentException("Actividad no encontrada");

            em.getTransaction().begin();
            if (datos.getNombre() != null && !datos.getNombre().isBlank())
                actividad.setNombre(datos.getNombre());
            if (datos.getDescripcion() != null)
                actividad.setDescripcion(datos.getDescripcion());
            if (datos.getFechaEntrega() != null)
                actividad.setFechaEntrega(datos.getFechaEntrega());
            em.getTransaction().commit();
            return actividad;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void eliminar(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            Actividad actividad = em.find(Actividad.class, id);
            if (actividad == null) throw new IllegalArgumentException("Actividad no encontrada");
            em.getTransaction().begin();
            em.remove(actividad);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
