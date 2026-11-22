package com.enit.services;

import java.util.List;

import jakarta.ejb.Local;

import com.enit.entities.Terrain;

@Local
public interface ILocalTerrain {

    public void ajouterTerrain(Terrain terrain);
    public Terrain modifierTerrain(Terrain terrain);
    public void supprimerTerrain(Long id);
    public Terrain trouverTerrainParId(Long id);
    public List<Terrain> tousLesTerrains();
    public List<Terrain> terrainsParType(String type);
    public List<Terrain> terrainsNonReserves();
    public List<Object[]> nombreReservationsParTerrain();
    
}