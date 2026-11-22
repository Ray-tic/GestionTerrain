package com.enit.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ejb.TransactionManagement;
import jakarta.ejb.TransactionManagementType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TemporalType;

import com.enit.entities.Reservation;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class LocalReservationImpl implements ILocalReservation {

    @PersistenceContext(unitName = "GestionTerrainPU")
    EntityManager em;
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void ajouterReservation(Reservation reservation) {
        try {
            System.out.println("🔵 Tentative d'ajout de réservation...");
            System.out.println("   Client: " + (reservation.getClient() != null ? reservation.getClient().getNom() : "NULL"));
            System.out.println("   Terrain: " + (reservation.getTerrain() != null ? reservation.getTerrain().getNom() : "NULL"));
            System.out.println("   Date: " + reservation.getDateReservation());
            
            em.persist(reservation);
            em.flush(); // Force la synchronisation avec la BD
            
            System.out.println("✅ Réservation créée avec succès - ID: " + reservation.getIdReservation());
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de l'ajout de la réservation: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Échec de la création de la réservation: " + e.getMessage(), e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Reservation modifierReservation(Reservation reservation) {
        try {
            System.out.println("🔵 Modification de la réservation ID: " + reservation.getIdReservation());
            Reservation updated = em.merge(reservation);
            em.flush();
            System.out.println("✅ Réservation modifiée avec succès");
            return updated;
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la modification: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Échec de la modification: " + e.getMessage(), e);
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void supprimerReservation(Integer id) {
        try {
            System.out.println("🔵 Suppression de la réservation ID: " + id);
            Reservation reservation = em.find(Reservation.class, id);
            if (reservation != null) {
                // Supprimer d'abord les paiements associés
                supprimerPaiementsParReservation(id);
                // Puis supprimer la réservation
                em.remove(reservation);
                em.flush();
                System.out.println("✅ Réservation supprimée avec succès");
            } else {
                System.out.println("⚠️ Réservation non trouvée: " + id);
            }
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la suppression: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Échec de la suppression: " + e.getMessage(), e);
        }
    }

    @Override
    public Reservation trouverReservationParId(Integer id) {
        try {
            return em.find(Reservation.class, id);
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la recherche: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Reservation> toutesLesReservations() {
        try {
            Query req = em.createQuery("select r from Reservation r order by r.dateReservation desc, r.heureDebut");
            return req.getResultList();
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reservation> reservationsMars2025() {
        Calendar cal = Calendar.getInstance();
        cal.set(2025, Calendar.MARCH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date debut = cal.getTime();
        
        cal.set(2025, Calendar.MARCH, 31);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        Date fin = cal.getTime();
        
        return reservationsParPeriode(debut, fin);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void supprimerReservationsAvant2023() {
        Calendar cal = Calendar.getInstance();
        cal.set(2023, Calendar.JANUARY, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date dateLimite = cal.getTime();
        
        System.out.println("🚀 Début suppression réservations avant: " + dateLimite);
        
        try {
            // 1. D'abord, compter les réservations concernées
            Query countQuery = em.createQuery(
                "SELECT COUNT(r) FROM Reservation r WHERE r.dateReservation < :dateLimite");
            countQuery.setParameter("dateLimite", dateLimite, TemporalType.DATE);
            Long totalReservations = (Long) countQuery.getSingleResult();
            
            System.out.println("📊 " + totalReservations + " réservations trouvées avant 2023");
            
            if (totalReservations == 0) {
                System.out.println("✅ Aucune réservation à supprimer");
                return;
            }
            
            // 2. Supprimer d'abord TOUS les paiements liés à ces réservations
            System.out.println("🗑️ Suppression des paiements associés...");
            int paiementsSupprimes = supprimerPaiementsAvant2023(dateLimite);
            System.out.println("✅ " + paiementsSupprimes + " paiements supprimés");
            
            // 3. Maintenant, supprimer les réservations
            System.out.println("🗑️ Suppression des réservations...");
            Query deleteReservationsQuery = em.createQuery(
                "DELETE FROM Reservation r WHERE r.dateReservation < :dateLimite");
            deleteReservationsQuery.setParameter("dateLimite", dateLimite, TemporalType.DATE);
            
            int reservationsSupprimees = deleteReservationsQuery.executeUpdate();
            em.flush();
            
            System.out.println("🎉 " + reservationsSupprimees + " réservations supprimées avec succès !");
            
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la suppression: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Échec de la suppression: " + e.getMessage(), e);
        }
    }
    
    /**
     * Supprime les paiements liés aux réservations avant une date donnée
     */
    private int supprimerPaiementsAvant2023(Date dateLimite) {
        try {
            // Méthode 1: Utiliser une sous-requête native (plus efficace)
            Query deleteQuery = em.createNativeQuery(
                "DELETE FROM paiement WHERE reservation_id IN " +
                "(SELECT IDRESERVATION FROM reservation WHERE DATERESERVATION < ?)");
            deleteQuery.setParameter(1, dateLimite);
            
            int result = deleteQuery.executeUpdate();
            em.flush();
            return result;
            
        } catch (Exception e) {
            System.err.println("⚠️ Méthode native échouée, tentative avec JPQL...");
            
            // Méthode 2: JPQL si vous avez une entité Paiement
            try {
                Query deleteQuery = em.createQuery(
                    "DELETE FROM Paiement p WHERE p.reservation.dateReservation < :dateLimite");
                deleteQuery.setParameter("dateLimite", dateLimite, TemporalType.DATE);
                
                int result = deleteQuery.executeUpdate();
                em.flush();
                return result;
                
            } catch (Exception ex) {
                System.err.println("⚠️ JPQL échoué, tentative manuelle...");
                
                // Méthode 3: Récupérer les IDs et supprimer un par un
                Query idQuery = em.createNativeQuery(
                    "SELECT IDRESERVATION FROM reservation WHERE DATERESERVATION < ?");
                idQuery.setParameter(1, dateLimite);
                List<Integer> reservationIds = idQuery.getResultList();
                
                int count = 0;
                for (Integer id : reservationIds) {
                    Query deleteSingle = em.createNativeQuery(
                        "DELETE FROM paiement WHERE reservation_id = ?");
                    deleteSingle.setParameter(1, id);
                    count += deleteSingle.executeUpdate();
                }
                em.flush();
                
                return count;
            }
        }
    }
    
    /**
     * Supprime les paiements pour une réservation spécifique
     */
    private void supprimerPaiementsParReservation(Integer reservationId) {
        try {
            Query deleteQuery = em.createNativeQuery(
                "DELETE FROM paiement WHERE reservation_id = ?");
            deleteQuery.setParameter(1, reservationId);
            deleteQuery.executeUpdate();
            em.flush();
        } catch (Exception e) {
            System.err.println("Erreur suppression paiements pour réservation " + reservationId + ": " + e.getMessage());
        }
    }

    @Override
    public List<Reservation> reservationsParTerrain(Long terrainId) {
        try {
            Query req = em.createQuery("select r from Reservation r where r.terrain.idTerrain = :terrainId order by r.dateReservation desc");
            req.setParameter("terrainId", terrainId);
            return req.getResultList();
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Reservation> reservationsParPeriode(Date debut, Date fin) {
        try {
            Query req = em.createQuery("select r from Reservation r where r.dateReservation between :debut and :fin order by r.dateReservation, r.heureDebut");
            req.setParameter("debut", debut, TemporalType.DATE);
            req.setParameter("fin", fin, TemporalType.DATE);
            return req.getResultList();
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la récupération: " + e.getMessage());
            return null;
        }
    }
    
    @Override
    public boolean existeReservationConflit(Long terrainId, Date dateReservation, Date heureDebut, Date heureFin, Integer reservationIdExclue) {
        try {
            System.out.println("🔍 Vérification des conflits pour terrain " + terrainId + " le " + dateReservation);
            
            // Convertir les dates en format DATE seulement pour la comparaison
            Calendar calDate = Calendar.getInstance();
            calDate.setTime(dateReservation);
            calDate.set(Calendar.HOUR_OF_DAY, 0);
            calDate.set(Calendar.MINUTE, 0);
            calDate.set(Calendar.SECOND, 0);
            calDate.set(Calendar.MILLISECOND, 0);
            Date dateOnly = calDate.getTime();
            
            // Requête pour trouver les réservations conflictuelles
            StringBuilder jpql = new StringBuilder();
            jpql.append("SELECT COUNT(r) FROM Reservation r ");
            jpql.append("WHERE r.terrain.idTerrain = :terrainId ");
            jpql.append("AND r.dateReservation = :dateReservation ");
            jpql.append("AND ( ");
            jpql.append("   (r.heureDebut < :heureFin AND r.heureFin > :heureDebut) "); // Chevauchement partiel
            jpql.append("   OR (r.heureDebut = :heureDebut AND r.heureFin = :heureFin) "); // Exactement même créneau
            jpql.append("   OR (r.heureDebut <= :heureDebut AND r.heureFin >= :heureFin) "); // Créneau inclus dans une autre réservation
            jpql.append("   OR (r.heureDebut >= :heureDebut AND r.heureFin <= :heureFin) "); // Créneau qui inclut une autre réservation
            jpql.append(") ");
            
            // Exclure la réservation en cours de modification (si applicable)
            if (reservationIdExclue != null) {
                jpql.append("AND r.idReservation != :reservationIdExclue ");
            }
            
            Query query = em.createQuery(jpql.toString());
            query.setParameter("terrainId", terrainId);
            query.setParameter("dateReservation", dateOnly, TemporalType.DATE);
            query.setParameter("heureDebut", heureDebut, TemporalType.TIME);
            query.setParameter("heureFin", heureFin, TemporalType.TIME);
            
            if (reservationIdExclue != null) {
                query.setParameter("reservationIdExclue", reservationIdExclue);
            }
            
            Long count = (Long) query.getSingleResult();
            boolean conflit = count > 0;
            
            System.out.println(conflit ? "⚠️ Conflit détecté: " + count + " réservation(s)" : "✅ Aucun conflit");
            
            return conflit;
            
        } catch (Exception e) {
            System.err.println("❌ Erreur lors de la vérification des conflits: " + e.getMessage());
            e.printStackTrace();
            return false; // En cas d'erreur, on accepte la réservation pour ne pas bloquer
        }
    }
    
    // Ajoutez aussi une méthode pour obtenir les créneaux disponibles
    public List<Object[]> getCreneauxDisponibles(Long terrainId, Date dateRecherche) {
        try {
            // Convertir la date en format DATE seulement
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateRecherche);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Date dateOnly = cal.getTime();
            
            // Récupérer tous les créneaux occupés pour ce terrain et cette date
            Query query = em.createQuery(
                "SELECT r.heureDebut, r.heureFin FROM Reservation r " +
                "WHERE r.terrain.idTerrain = :terrainId " +
                "AND r.dateReservation = :dateRecherche " +
                "ORDER BY r.heureDebut");
            
            query.setParameter("terrainId", terrainId);
            query.setParameter("dateRecherche", dateOnly, TemporalType.DATE);
            
            return query.getResultList();
            
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des créneaux: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Méthode utilitaire pour vérifier l'état avant suppression
     */
    public void verifierEtatAvantSuppression() {
        Calendar cal = Calendar.getInstance();
        cal.set(2023, Calendar.JANUARY, 1);
        Date dateLimite = cal.getTime();
        
        System.out.println("=== VÉRIFICATION AVANT SUPPRESSION ===");
        
        // Compter les réservations
        Query countResQuery = em.createQuery(
            "SELECT COUNT(r) FROM Reservation r WHERE r.dateReservation < :dateLimite");
        countResQuery.setParameter("dateLimite", dateLimite, TemporalType.DATE);
        Long nbReservations = (Long) countResQuery.getSingleResult();
        System.out.println("Réservations à supprimer: " + nbReservations);
        
        // Compter les paiements associés
        try {
            Query countPaiementQuery = em.createNativeQuery(
                "SELECT COUNT(*) FROM paiement p " +
                "INNER JOIN reservation r ON p.reservation_id = r.IDRESERVATION " +
                "WHERE r.DATERESERVATION < ?");
            countPaiementQuery.setParameter(1, dateLimite);
            Object nbPaiements = countPaiementQuery.getSingleResult();
            System.out.println("Paiements associés à supprimer: " + nbPaiements);
        } catch (Exception e) {
            System.out.println("Impossible de compter les paiements: " + e.getMessage());
        }
        
        // Afficher quelques exemples
        try {
            Query exemplesQuery = em.createQuery(
                "SELECT r.idReservation, r.dateReservation FROM Reservation r " +
                "WHERE r.dateReservation < :dateLimite ORDER BY r.dateReservation");
            exemplesQuery.setParameter("dateLimite", dateLimite, TemporalType.DATE);
            exemplesQuery.setMaxResults(5);
            List<Object[]> exemples = exemplesQuery.getResultList();
            
            System.out.println("Exemples de réservations à supprimer:");
            for (Object[] exemple : exemples) {
                System.out.println("  ID: " + exemple[0] + ", Date: " + exemple[1]);
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'affichage des exemples: " + e.getMessage());
        }
        
        System.out.println("=== FIN VÉRIFICATION ===");
    }
}