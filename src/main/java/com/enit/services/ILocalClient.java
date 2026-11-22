package com.enit.services;

import java.util.List;

import jakarta.ejb.Local;

import com.enit.entities.Client;

@Local
public interface ILocalClient {

    public void ajouterClient(Client client);
    public Client modifierClient(Client client);
    public void supprimerClient(Long id);
    public Client trouverClientParId(Long id);
    public List<Client> tousLesClients();
    public List<Client> clientsAvecReservationsFootball();
    public List<Object[]> clientsTriesParDateInscription();
    public List<Object[]> totalPaiementsParClientTrie();
    
}