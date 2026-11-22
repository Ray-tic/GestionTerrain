package com.enit.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import com.enit.entities.Client;

@Stateless
public class LocalClientImpl implements ILocalClient {

    @PersistenceContext(unitName = "GestionTerrainPU")
    EntityManager em;

    @Override
    public void ajouterClient(Client client) {
        em.persist(client);
    }

    @Override
    public Client modifierClient(Client client) {
        return em.merge(client);
    }

    @Override
    public void supprimerClient(Long id) {
        Client client = em.find(Client.class, id);
        if (client != null) {
            em.remove(client);
        }
    }

    @Override
    public Client trouverClientParId(Long id) {
        return em.find(Client.class, id);
    }

    @Override
    public List<Client> tousLesClients() {
        Query req = em.createQuery("select c from Client c order by c.nom");
        return req.getResultList();
    }

    @Override
    public List<Client> clientsAvecReservationsFootball() {
        Query req = em.createQuery(
            "select distinct r.client from Reservation r where r.terrain.type = 'Football' order by r.client.dateInscription desc");
        return req.getResultList();
    }

    @Override
    public List<Object[]> clientsTriesParDateInscription() {
        Query req = em.createQuery(
            "select c.nom, c.email from Client c order by c.dateInscription desc");
        return req.getResultList();
    }

    @Override
    public List<Object[]> totalPaiementsParClientTrie() {
        Query req = em.createQuery(
            "select c.nom, sum(p.montant) as total from Client c " +
            "join c.reservations r " +
            "join Paiement p on p.reservation.idReservation = r.idReservation " +
            "group by c.nom order by total desc");
        return req.getResultList();
    }
    
}