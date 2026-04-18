/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unipanamericana.gestionproyectosestudiantiles.service;

import com.unipanamericana.gestionproyectosestudiantiles.model.Docente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class DocenteService {

    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public List<Docente> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT d FROM Docente d", Docente.class).getResultList();
        } finally {
            em.close();
        }
    }

    public Docente buscarPorId(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Docente.class, id);
        } finally {
            em.close();
        }
    }

    public Docente crear(Docente docente) {
        if (docente.getNombre() == null || docente.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre del docente es obligatorio");
        if (docente.getCorreo() == null || docente.getCorreo().isBlank())
            throw new IllegalArgumentException("El correo del docente es obligatorio");
        if (docente.getArea() == null || docente.getArea().isBlank())
            throw new IllegalArgumentException("El área del docente es obligatoria");

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(docente);
            em.getTransaction().commit();
            return docente;
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Docente actualizar(Integer id, Docente datos) {
        EntityManager em = emf.createEntityManager();
        try {
            Docente docente = em.find(Docente.class, id);
            if (docente == null) throw new IllegalArgumentException("Docente no encontrado");

            em.getTransaction().begin();
            if (datos.getNombre() != null && !datos.getNombre().isBlank())
                docente.setNombre(datos.getNombre());
            if (datos.getCorreo() != null && !datos.getCorreo().isBlank())
                docente.setCorreo(datos.getCorreo());
            if (datos.getArea() != null && !datos.getArea().isBlank())
                docente.setArea(datos.getArea());
            em.getTransaction().commit();
            return docente;
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
            Docente docente = em.find(Docente.class, id);
            if (docente == null) throw new IllegalArgumentException("Docente no encontrado");
            em.getTransaction().begin();
            em.remove(docente);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
