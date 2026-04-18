/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unipanamericana.gestionproyectosestudiantiles.service;

import com.unipanamericana.gestionproyectosestudiantiles.model.Docente;
import com.unipanamericana.gestionproyectosestudiantiles.model.Proyecto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class ProyectoService {

    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public List<Proyecto> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Proyecto p", Proyecto.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Proyecto buscarPorId(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Proyecto.class, id);
        } finally {
            em.close();
        }
    }

    public Proyecto crear(Proyecto proyecto) {
        if (proyecto.getNombre() == null || proyecto.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre del proyecto es obligatorio");
        if (proyecto.getFechaInicio() == null)
            throw new IllegalArgumentException("La fecha de inicio es obligatoria");
        if (proyecto.getFechaFin() == null)
            throw new IllegalArgumentException("La fecha de fin es obligatoria");
        if (proyecto.getDocente() == null || proyecto.getDocente().getIdDocente() == null)
            throw new IllegalArgumentException("El docente es obligatorio");

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Docente docente = em.find(Docente.class, proyecto.getDocente().getIdDocente());
            if (docente == null) throw new IllegalArgumentException("Docente no encontrado");
            proyecto.setDocente(docente);
            em.persist(proyecto);
            em.getTransaction().commit();
            return proyecto;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Proyecto actualizar(Integer id, Proyecto datos) {
        EntityManager em = emf.createEntityManager();
        try {
            Proyecto proyecto = em.find(Proyecto.class, id);
            if (proyecto == null) throw new IllegalArgumentException("Proyecto no encontrado");

            em.getTransaction().begin();
            if (datos.getNombre() != null && !datos.getNombre().isBlank())
                proyecto.setNombre(datos.getNombre());
            if (datos.getDescripcion() != null)
                proyecto.setDescripcion(datos.getDescripcion());
            if (datos.getFechaInicio() != null)
                proyecto.setFechaInicio(datos.getFechaInicio());
            if (datos.getFechaFin() != null)
                proyecto.setFechaFin(datos.getFechaFin());
            em.getTransaction().commit();
            return proyecto;
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
            Proyecto proyecto = em.find(Proyecto.class, id);
            if (proyecto == null) throw new IllegalArgumentException("Proyecto no encontrado");
            em.getTransaction().begin();
            em.remove(proyecto);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
