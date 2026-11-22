package com.enit.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import com.enit.entities.Terrain;

@Stateless
public class LocalTerrainImpl implements ILocalTerrain {

    @PersistenceContext(unitName = "GestionTerrainPU")
    EntityManager em;

    @Override
    public void ajouterTerrain(Terrain terrain) {
        em.persist(terrain);
    }

    @Override
    public Terrain modifierTerrain(Terrain terrain) {
        return em.merge(terrain);
    }

    @Override
    public void supprimerTerrain(Long id) {
        Terrain terrain = em.find(Terrain.class, id);
        if (terrain != null) {
            em.remove(terrain);
        }
    }

    @Override
    public Terrain trouverTerrainParId(Long id) {
        return em.find(Terrain.class, id);
    }

    @Override
    public List<Terrain> tousLesTerrains() {
        Query req = em.createQuery("select t from Terrain t order by t.nom");
        return req.getResultList();
    }

    @Override
    public List<Terrain> terrainsParType(String type) {
        Query req = em.createQuery("select t from Terrain t where t.type = :type order by t.nom");
        req.setParameter("type", type);
        return req.getResultList();
    }

    @Override
    public List<Terrain> terrainsNonReserves() {
        Query req = em.createQuery("select t from Terrain t where t.reservations is empty order by t.nom");
        return req.getResultList();
    }

    @Override
    public List<Object[]> nombreReservationsParTerrain() {
        Query req = em.createQuery(
            "select t.nom, t.idTerrain, count(r) from Terrain t left join t.reservations r group by t.nom, t.idTerrain order by t.nom");
        return req.getResultList();
    }
    
}