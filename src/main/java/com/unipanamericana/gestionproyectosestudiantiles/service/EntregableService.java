/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unipanamericana.gestionproyectosestudiantiles.service;

import com.unipanamericana.gestionproyectosestudiantiles.model.Actividad;
import com.unipanamericana.gestionproyectosestudiantiles.model.Entregable;
import com.unipanamericana.gestionproyectosestudiantiles.model.Estudiante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class EntregableService {

    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public List<Entregable> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Entregable e", Entregable.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Entregable buscarPorId(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Entregable.class, id);
        } finally {
            em.close();
        }
    }

    public Entregable crear(Entregable entregable) {
        if (entregable.getActividad() == null || entregable.getActividad().getIdActividad() == null)
            throw new IllegalArgumentException("La actividad es obligatoria");
        if (entregable.getEstudiante() == null || entregable.getEstudiante().getIdEstudiante() == null)
            throw new IllegalArgumentException("El estudiante es obligatorio");

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Actividad actividad = em.find(Actividad.class, entregable.getActividad().getIdActividad());
            Estudiante estudiante = em.find(Estudiante.class, entregable.getEstudiante().getIdEstudiante());
            if (actividad == null) throw new IllegalArgumentException("Actividad no encontrada");
            if (estudiante == null) throw new IllegalArgumentException("Estudiante no encontrado");
            entregable.setActividad(actividad);
            entregable.setEstudiante(estudiante);
            em.persist(entregable);
            em.getTransaction().commit();
            return entregable;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Entregable actualizar(Integer id, Entregable datos) {
        EntityManager em = emf.createEntityManager();
        try {
            Entregable entregable = em.find(Entregable.class, id);
            if (entregable == null) throw new IllegalArgumentException("Entregable no encontrado");

            em.getTransaction().begin();
            if (datos.getEstado() != null)
                entregable.setEstado(datos.getEstado());
            if (datos.getFechaSubida() != null)
                entregable.setFechaSubida(datos.getFechaSubida());
            em.getTransaction().commit();
            return entregable;
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
            Entregable entregable = em.find(Entregable.class, id);
            if (entregable == null) throw new IllegalArgumentException("Entregable no encontrado");
            em.getTransaction().begin();
            em.remove(entregable);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}