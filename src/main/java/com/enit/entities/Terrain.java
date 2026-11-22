package com.enit.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "terrain")
public class Terrain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTerrain;

    private String nom;
    private String type;
    private Integer capacite;
    private String localisation;

    @OneToMany(mappedBy = "terrain", cascade = CascadeType.ALL)
    private List<Reservation> reservations = new ArrayList<>();

    public Long getIdTerrain() { return idTerrain; }
    public void setIdTerrain(Long idTerrain) { this.idTerrain = idTerrain; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getCapacite() { return capacite; }
    public void setCapacite(Integer capacite) { this.capacite = capacite; }

    public String getLocalisation() { return localisation; }
    public void setLocalisation(String localisation) { this.localisation = localisation; }

    public List<Reservation> getReservations() { return reservations; }
    public void setReservations(List<Reservation> reservations) { this.reservations = reservations; }
}