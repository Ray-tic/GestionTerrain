package com.enit.services;

import java.util.List;

import jakarta.ejb.Local;

import com.enit.entities.Paiement;

@Local
public interface ILocalPaiement {

    // ========== CRUD BASIQUE ==========
    public void ajouterPaiement(Paiement paiement);
    public Paiement modifierPaiement(Paiement paiement);
    public void supprimerPaiement(Long id);
    public Paiement trouverPaiementParId(Long id);
    public List<Paiement> tousLesPaiements();
    public boolean reservationADejaPaiement(Integer reservationId);
    
    // ========== MÉTHODES SPÉCIFIQUES ==========
    public void modifierModePaiementReservation(Integer reservationId, String nouveauMode);
    public List<Object[]> totalPaiementsParClientTrie();
    
    // ========== NOUVELLES MÉTHODES POUR UTF-8 ET VALIDATION ==========
    public Paiement trouverPaiementParReservation(Integer reservationId);
    public void corrigerEncodagePaiements();
    public int compterPaiementsParReservation(Integer reservationId);
    public List<Paiement> trouverPaiementsAvecProblemeUTF8();
    
    
}