/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unipanamericana.gestionproyectosestudiantiles.service;

import com.unipanamericana.gestionproyectosestudiantiles.model.Estudiante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class EstudianteService {

    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public List<Estudiante> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT e FROM Estudiante e", Estudiante.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Estudiante buscarPorId(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Estudiante.class, id);
        } finally {
            em.close();
        }
    }

    public Estudiante crear(Estudiante estudiante) {
        if (estudiante.getNombre() == null || estudiante.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre es obligatorio");
        if (estudiante.getCorreo() == null || estudiante.getCorreo().isBlank())
            throw new IllegalArgumentException("El correo es obligatorio");
        if (estudiante.getProgramaAcademico() == null || estudiante.getProgramaAcademico().isBlank())
            throw new IllegalArgumentException("El programa académico es obligatorio");

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(estudiante);
            em.getTransaction().commit();
            return estudiante;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Estudiante actualizar(Integer id, Estudiante datos) {
        EntityManager em = emf.createEntityManager();
        try {
            Estudiante estudiante = em.find(Estudiante.class, id);
            if (estudiante == null) throw new IllegalArgumentException("Estudiante no encontrado");

            em.getTransaction().begin();
            if (datos.getNombre() != null && !datos.getNombre().isBlank())
                estudiante.setNombre(datos.getNombre());
            if (datos.getCorreo() != null && !datos.getCorreo().isBlank())
                estudiante.setCorreo(datos.getCorreo());
            if (datos.getProgramaAcademico() != null && !datos.getProgramaAcademico().isBlank())
                estudiante.setProgramaAcademico(datos.getProgramaAcademico());
            em.getTransaction().commit();
            return estudiante;
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
            Estudiante estudiante = em.find(Estudiante.class, id);
            if (estudiante == null) throw new IllegalArgumentException("Estudiante no encontrado");
            em.getTransaction().begin();
            em.remove(estudiante);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
