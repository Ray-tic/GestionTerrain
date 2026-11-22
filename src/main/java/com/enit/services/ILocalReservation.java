package com.enit.services;

import java.util.Date;
import java.util.List;

import jakarta.ejb.Local;

import com.enit.entities.Reservation;

@Local
public interface ILocalReservation {

    public void ajouterReservation(Reservation reservation);
    public Reservation modifierReservation(Reservation reservation);
    public void supprimerReservation(Integer id);
    public Reservation trouverReservationParId(Integer id);
    public List<Reservation> toutesLesReservations();
    public List<Reservation> reservationsMars2025();
    public void supprimerReservationsAvant2023();
    public List<Reservation> reservationsParTerrain(Long terrainId);
    public List<Reservation> reservationsParPeriode(Date debut, Date fin);
    public boolean existeReservationConflit(Long terrainId, Date dateReservation, Date heureDebut, Date heureFin, Integer reservationIdExclue);
    
    
}