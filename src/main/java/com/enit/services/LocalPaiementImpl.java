package com.enit.services;

import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import com.enit.entities.Paiement;

@Stateless
public class LocalPaiementImpl implements ILocalPaiement {

    @PersistenceContext(unitName = "GestionTerrainPU")
    EntityManager em;

    // ========== CRUD BASIQUE ==========
    @Override
    public void ajouterPaiement(Paiement paiement) {
        System.out.println("📥 Ajout paiement: " + paiement.getMontant() + 
                          "€ pour réservation: " + 
                          (paiement.getReservation() != null ? paiement.getReservation().getIdReservation() : "null"));
        em.persist(paiement);
        System.out.println("✅ Paiement ajouté avec ID: " + paiement.getIdPaiement());
    }

    @Override
    public Paiement modifierPaiement(Paiement paiement) {
        System.out.println("📝 Modification paiement ID: " + paiement.getIdPaiement());
        return em.merge(paiement);
    }

    @Override
    public void supprimerPaiement(Long id) {
        Paiement paiement = em.find(Paiement.class, id);
        if (paiement != null) {
            System.out.println("🗑️ Suppression paiement ID: " + id);
            em.remove(paiement);
        }
    }
    @Override
    public boolean reservationADejaPaiement(Integer reservationId) {
        System.out.println("🔍 Vérification si réservation #" + reservationId + " a déjà un paiement");
        
        try {
            // Méthode 1: Utiliser COUNT pour une vérification rapide
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(p) FROM Paiement p WHERE p.reservation.idReservation = :reservationId", 
                Long.class
            );
            query.setParameter("reservationId", reservationId);
            
            Long count = query.getSingleResult();
            boolean aDejaPaiement = count != null && count > 0;
            
            System.out.println("📊 Résultat vérification: " + (aDejaPaiement ? "OUI, paiement existant" : "NON, pas de paiement"));
            
            return aDejaPaiement;
            
        } catch (Exception e) {
            System.err.println("❌ Erreur vérification paiement existant: " + e.getMessage());
            // En cas d'erreur, on suppose qu'il n'y a pas de paiement pour éviter de bloquer l'ajout
            return false;
        }
    }
    @Override
    public Paiement trouverPaiementParId(Long id) {
        return em.find(Paiement.class, id);
    }

    @Override
    public List<Paiement> tousLesPaiements() {
        Query req = em.createQuery("select p from Paiement p order by p.datePaiement desc");
        List<Paiement> resultats = req.getResultList();
        System.out.println("📋 Récupération de " + resultats.size() + " paiements");
        return resultats;
    }

    // ========== MÉTHODES SPÉCIFIQUES EXISTANTES ==========
    @Override
    public void modifierModePaiementReservation(Integer reservationId, String nouveauMode) {
        System.out.println("🔄 Modification mode paiement pour réservation #" + reservationId + " -> " + nouveauMode);
        
        Query req = em.createQuery("select p from Paiement p where p.reservation.idReservation = :reservationId");
        req.setParameter("reservationId", reservationId);
        List<Paiement> paiements = req.getResultList();
        
        System.out.println("🔍 " + paiements.size() + " paiement(s) trouvé(s) pour cette réservation");
        
        for (Paiement paiement : paiements) {
            String ancienMode = paiement.getModePaiement();
            paiement.setModePaiement(nouveauMode);
            em.merge(paiement);
            System.out.println("✅ Paiement ID " + paiement.getIdPaiement() + ": " + ancienMode + " -> " + nouveauMode);
        }
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

    // ========== NOUVELLES MÉTHODES POUR UTF-8 ET VALIDATION ==========
    @Override
    public Paiement trouverPaiementParReservation(Integer reservationId) {
        System.out.println("🔍 Recherche paiement pour réservation #" + reservationId);
        
        try {
            TypedQuery<Paiement> query = em.createQuery(
                "SELECT p FROM Paiement p WHERE p.reservation.idReservation = :reservationId", 
                Paiement.class
            );
            query.setParameter("reservationId", reservationId);
            
            List<Paiement> resultats = query.getResultList();
            
            if (resultats.isEmpty()) {
                System.out.println("ℹ️ Aucun paiement trouvé pour réservation #" + reservationId);
                return null;
            } else {
                System.out.println("✅ Paiement trouvé: ID " + resultats.get(0).getIdPaiement());
                return resultats.get(0);
            }
            
        } catch (Exception e) {
            System.err.println("❌ Erreur trouverPaiementParReservation: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void corrigerEncodagePaiements() {
        System.out.println("🚀 DÉBUT CORRECTION ENCODAGE DES PAIEMENTS...");
        
        int totalCorriges = 0;
        int totalVerifies = 0;
        
        try {
            // ===== MÉTHODE 1: CORRECTION NATIVE SQL (TRÈS RAPIDE) =====
            System.out.println("📊 Méthode 1: Correction native SQL...");
            
            try {
                // Corriger "espÃ¨ces" -> "espèces"
                Query nativeQuery1 = em.createNativeQuery(
                    "UPDATE paiement SET MODEPAIEMENT = 'espèces' WHERE MODEPAIEMENT = 'espÃ¨ces'"
                );
                int corriges1 = nativeQuery1.executeUpdate();
                totalCorriges += corriges1;
                
                if (corriges1 > 0) {
                    System.out.println("✅ " + corriges1 + " 'espÃ¨ces' corrigé(s) en 'espèces'");
                }
                
            } catch (Exception e) {
                System.err.println("⚠️ Échec correction native SQL 1: " + e.getMessage());
            }
            
            try {
                // Corriger d'autres variantes possibles
                Query nativeQuery2 = em.createNativeQuery(
                    "UPDATE paiement SET MODEPAIEMENT = 'chèque' WHERE MODEPAIEMENT LIKE '%ch%que%'"
                );
                int corriges2 = nativeQuery2.executeUpdate();
                totalCorriges += corriges2;
                
                if (corriges2 > 0) {
                    System.out.println("✅ " + corriges2 + " chèque(s) corrigé(s)");
                }
                
            } catch (Exception e) {
                System.err.println("⚠️ Échec correction native SQL 2: " + e.getMessage());
            }
            
            // ===== MÉTHODE 2: CORRECTION VIA JPQL (POUR LES CAS COMPLEXES) =====
            System.out.println("📊 Méthode 2: Correction via JPQL...");
            
            try {
                // Récupérer tous les paiements avec problèmes d'encodage
                TypedQuery<Paiement> query = em.createQuery(
                    "SELECT p FROM Paiement p WHERE p.modePaiement LIKE '%Ã%'", 
                    Paiement.class
                );
                List<Paiement> paiementsAvecProblemes = query.getResultList();
                totalVerifies = paiementsAvecProblemes.size();
                
                System.out.println("🔍 " + paiementsAvecProblemes.size() + " paiement(s) avec problèmes UTF-8 détecté(s)");
                
                // Corrections manuelles pour chaque cas
                for (Paiement p : paiementsAvecProblemes) {
                    String ancien = p.getModePaiement();
                    String nouveau = ancien;
                    
                    // Appliquer les corrections
                    nouveau = nouveau.replace("Ã¨", "è")
                                    .replace("Ã©", "é")
                                    .replace("Ãª", "ê")
                                    .replace("Ã«", "ë")
                                    .replace("Ã ", "à")
                                    .replace("Ã¢", "â")
                                    .replace("Ã¤", "ä")
                                    .replace("Ã®", "î")
                                    .replace("Ã¯", "ï")
                                    .replace("Ã´", "ô")
                                    .replace("Ã¶", "ö")
                                    .replace("Ã¹", "ù")
                                    .replace("Ã»", "û")
                                    .replace("Ã¼", "ü")
                                    .replace("Ã§", "ç");
                    
                    if (!ancien.equals(nouveau)) {
                        p.setModePaiement(nouveau);
                        em.merge(p);
                        totalCorriges++;
                        System.out.println("🔄 Paiement ID " + p.getIdPaiement() + ": " + ancien + " -> " + nouveau);
                    }
                }
                
            } catch (Exception e) {
                System.err.println("⚠️ Échec correction JPQL: " + e.getMessage());
                e.printStackTrace();
            }
            
            // ===== MÉTHODE 3: VÉRIFICATION FINALE =====
            System.out.println("📊 Méthode 3: Vérification finale...");
            
            try {
                // Vérifier s'il reste des problèmes
                TypedQuery<Long> checkQuery = em.createQuery(
                    "SELECT COUNT(p) FROM Paiement p WHERE p.modePaiement LIKE '%Ã%'", 
                    Long.class
                );
                Long restants = checkQuery.getSingleResult();
                
                if (restants == 0) {
                    System.out.println("🎉 CORRECTION RÉUSSIE! Tous les paiements sont maintenant correctement encodés!");
                } else {
                    System.out.println("⚠️ ATTENTION: Il reste " + restants + " paiement(s) avec problèmes d'encodage");
                    
                    // Afficher les paiements problématiques restants
                    TypedQuery<Paiement> problemQuery = em.createQuery(
                        "SELECT p FROM Paiement p WHERE p.modePaiement LIKE '%Ã%'", 
                        Paiement.class
                    );
                    List<Paiement> problemes = problemQuery.getResultList();
                    
                    for (Paiement p : problemes) {
                        System.err.println("❌ Problème restant - Paiement ID " + p.getIdPaiement() + ": " + p.getModePaiement());
                    }
                }
                
                // Statistiques
                TypedQuery<Long> totalQuery = em.createQuery(
                    "SELECT COUNT(p) FROM Paiement p", 
                    Long.class
                );
                Long totalPaiements = totalQuery.getSingleResult();
                
                System.out.println("📈 STATISTIQUES FINALES:");
                System.out.println("   • Total paiements: " + totalPaiements);
                System.out.println("   • Paiements vérifiés: " + totalVerifies);
                System.out.println("   • Paiements corrigés: " + totalCorriges);
                System.out.println("   • Taux de correction: " + 
                    (totalVerifies > 0 ? (totalCorriges * 100 / totalVerifies) : 100) + "%");
                
            } catch (Exception e) {
                System.err.println("⚠️ Échec vérification finale: " + e.getMessage());
            }
            
            // ===== MÉTHODE 4: VÉRIFIER LA STRUCTURE DE LA BASE =====
            System.out.println("📊 Méthode 4: Vérification structure base...");
            
            try {
                // Vérifier l'encodage de la table via requête native
                Query encodingQuery = em.createNativeQuery(
                    "SHOW CREATE TABLE paiement"
                );
                Object result = encodingQuery.getSingleResult();
                String createTable = result.toString();
                
                if (createTable.contains("utf8") || createTable.contains("utf8mb4")) {
                    System.out.println("✅ La table paiement utilise l'encodage UTF-8");
                } else {
                    System.out.println("⚠️ La table paiement n'utilise PAS l'encodage UTF-8");
                    System.out.println("   Structure: " + createTable);
                }
                
            } catch (Exception e) {
                System.err.println("⚠️ Impossible de vérifier la structure de la table: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.err.println("❌ ERREUR GRAVE lors de la correction: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Échec de la correction encodage", e);
        }
        
        System.out.println("🏁 FIN CORRECTION ENCODAGE - Total corrigés: " + totalCorriges);
    }

    @Override
    public int compterPaiementsParReservation(Integer reservationId) {
        System.out.println("🔢 Comptage paiements pour réservation #" + reservationId);
        
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(p) FROM Paiement p WHERE p.reservation.idReservation = :reservationId", 
                Long.class
            );
            query.setParameter("reservationId", reservationId);
            
            Long count = query.getSingleResult();
            System.out.println("📊 " + count + " paiement(s) pour réservation #" + reservationId);
            
            return count != null ? count.intValue() : 0;
            
        } catch (Exception e) {
            System.err.println("❌ Erreur compterPaiementsParReservation: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public List<Paiement> trouverPaiementsAvecProblemeUTF8() {
        System.out.println("🔍 Recherche paiements avec problèmes UTF-8...");
        
        try {
            TypedQuery<Paiement> query = em.createQuery(
                "SELECT p FROM Paiement p WHERE p.modePaiement LIKE '%Ã%' ORDER BY p.idPaiement", 
                Paiement.class
            );
            
            List<Paiement> resultats = query.getResultList();
            System.out.println("📊 " + resultats.size() + " paiement(s) avec problèmes UTF-8 trouvé(s)");
            
            return resultats;
            
        } catch (Exception e) {
            System.err.println("❌ Erreur trouverPaiementsAvecProblemeUTF8: " + e.getMessage());
            e.printStackTrace();
            return List.of(); // Retourne une liste vide en cas d'erreur
        }
    }

    // ========== MÉTHODES UTILITAIRES SUPPLEMENTAIRES ==========
    
    /**
     * Méthode pour vérifier l'intégrité des données
     */
    public void verifierIntegriteDonnees() {
        System.out.println("🔍 VÉRIFICATION INTÉGRITÉ DES DONNÉES...");
        
        try {
            // 1. Compter les paiements sans réservation
            TypedQuery<Long> querySansReservation = em.createQuery(
                "SELECT COUNT(p) FROM Paiement p WHERE p.reservation IS NULL", 
                Long.class
            );
            Long sansReservation = querySansReservation.getSingleResult();
            
            if (sansReservation > 0) {
                System.out.println("⚠️ ATTENTION: " + sansReservation + " paiement(s) sans réservation!");
                
                // Lister les paiements sans réservation
                TypedQuery<Paiement> detailsQuery = em.createQuery(
                    "SELECT p FROM Paiement p WHERE p.reservation IS NULL", 
                    Paiement.class
                );
                List<Paiement> problemes = detailsQuery.getResultList();
                
                for (Paiement p : problemes) {
                    System.out.println("   • Paiement ID " + p.getIdPaiement() + 
                                     " - Montant: " + p.getMontant() + 
                                     "€ - Mode: " + p.getModePaiement());
                }
            } else {
                System.out.println("✅ Tous les paiements ont une réservation associée");
            }
            
            // 2. Vérifier les montants négatifs ou nuls
            TypedQuery<Long> queryMontants = em.createQuery(
                "SELECT COUNT(p) FROM Paiement p WHERE p.montant <= 0", 
                Long.class
            );
            Long montantsInvalides = queryMontants.getSingleResult();
            
            if (montantsInvalides > 0) {
                System.out.println("⚠️ ATTENTION: " + montantsInvalides + " paiement(s) avec montant invalide (≤ 0)!");
            } else {
                System.out.println("✅ Tous les paiements ont un montant valide (> 0)");
            }
            
            // 3. Statistiques générales
            TypedQuery<Long> totalQuery = em.createQuery(
                "SELECT COUNT(p) FROM Paiement p", 
                Long.class
            );
            Long total = totalQuery.getSingleResult();
            
            TypedQuery<Double> sommeQuery = em.createQuery(
                "SELECT SUM(p.montant) FROM Paiement p", 
                Double.class
            );
            Double somme = sommeQuery.getSingleResult();
            
            System.out.println("📈 STATISTIQUES GLOBALES:");
            System.out.println("   • Total paiements: " + total);
            System.out.println("   • Somme totale: " + (somme != null ? somme : 0) + "€");
            System.out.println("   • Moyenne par paiement: " + 
                (total > 0 && somme != null ? (somme / total) : 0) + "€");
            
        } catch (Exception e) {
            System.err.println("❌ Erreur vérification intégrité: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("🏁 FIN VÉRIFICATION INTÉGRITÉ");
    }
    
    /**
     * Méthode pour nettoyer les doublons (un paiement par réservation maximum)
     */
    public void nettoyerDoublons() {
        System.out.println("🧹 NETTOYAGE DES DOUBLONS DE PAIEMENTS...");
        
        try {
            // Trouver les réservations avec plusieurs paiements
            TypedQuery<Object[]> queryDoublons = em.createQuery(
                "SELECT p.reservation.idReservation, COUNT(p) as nb " +
                "FROM Paiement p " +
                "WHERE p.reservation IS NOT NULL " +
                "GROUP BY p.reservation.idReservation " +
                "HAVING COUNT(p) > 1", 
                Object[].class
            );
            
            List<Object[]> doublons = queryDoublons.getResultList();
            
            if (doublons.isEmpty()) {
                System.out.println("✅ Aucun doublon trouvé");
                return;
            }
            
            System.out.println("⚠️ " + doublons.size() + " réservation(s) avec doublons trouvée(s)");
            
            for (Object[] doublon : doublons) {
                Integer reservationId = (Integer) doublon[0];
                Long nombre = (Long) doublon[1];
                
                System.out.println("   • Réservation #" + reservationId + ": " + nombre + " paiement(s)");
                
                // Garder le paiement le plus récent, supprimer les autres
                TypedQuery<Paiement> queryPaiements = em.createQuery(
                    "SELECT p FROM Paiement p " +
                    "WHERE p.reservation.idReservation = :reservationId " +
                    "ORDER BY p.datePaiement DESC", 
                    Paiement.class
                );
                queryPaiements.setParameter("reservationId", reservationId);
                
                List<Paiement> paiements = queryPaiements.getResultList();
                
                if (paiements.size() > 1) {
                    // Garder le premier (le plus récent)
                    Paiement aGarder = paiements.get(0);
                    System.out.println("     → Garder paiement ID " + aGarder.getIdPaiement() + 
                                     " (Date: " + aGarder.getDatePaiement() + ")");
                    
                    // Supprimer les autres
                    for (int i = 1; i < paiements.size(); i++) {
                        Paiement aSupprimer = paiements.get(i);
                        System.out.println("     → Supprimer paiement ID " + aSupprimer.getIdPaiement());
                        em.remove(aSupprimer);
                    }
                }
            }
            
            System.out.println("✅ Nettoyage des doublons terminé");
            
        } catch (Exception e) {
            System.err.println("❌ Erreur nettoyage doublons: " + e.getMessage());
            e.printStackTrace();
        }
    }
}