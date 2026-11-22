package com.enit.controller;
import java.util.ArrayList;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.enit.entities.Terrain;
import com.enit.entities.Client;
import com.enit.entities.Reservation;
import com.enit.entities.Paiement;
import com.enit.services.ILocalTerrain;
import com.enit.services.ILocalClient;
import com.enit.services.ILocalReservation;
import com.enit.services.ILocalPaiement;

@WebServlet(name = "ControleurServlet", urlPatterns = {"", "/", "/controller"})
public class ControleurServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    
    @EJB 
    private ILocalTerrain metierTerrain;
    
    @EJB 
    private ILocalClient metierClient;
    
    @EJB 
    private ILocalReservation metierReservation;
    
    @EJB 
    private ILocalPaiement metierPaiement;
    
    // ========== MÉTHODE INITIALISATION ==========
    @Override
    public void init() throws ServletException {
        super.init();
        System.out.println("Servlet Controleur initialisé avec support UTF-8");
    }
    
    // ========== MÉTHODE DE SERVICE ==========
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // FORÇAGE UTF-8 POUR TOUTES LES REQUÊTES
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        
        System.out.println("Charset request: " + request.getCharacterEncoding());
        System.out.println("Charset response: " + response.getCharacterEncoding());
        
        super.service(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        // Si aucune action n'est spécifiée, c'est la page d'accueil
        if (action == null || action.isEmpty()) {
            String servletPath = request.getServletPath();
            if ("/".equals(servletPath) || "".equals(servletPath)) {
                accueil(request, response);
                return;
            }
            action = "accueil";
        }
        
        try {
            switch (action) {
                case "accueil":
                    accueil(request, response);
                    break;
                case "terrains":
                    listerTerrains(request, response);
                    break;
                case "clients":
                    listerClients(request, response);
                    break;
                case "reservations":
                    listerReservations(request, response);
                    break;
                case "paiements":
                    listerPaiements(request, response);
                    break;
                case "terrainsNonReserves":
                    listerTerrainsNonReserves(request, response);
                    break;
                case "clientsFootball":
                    listerClientsFootball(request, response);
                    break;
                case "reservationsMars2025":
                    listerReservationsMars2025(request, response);
                    break;
                case "statsReservations":
                    statsReservationsParTerrain(request, response);
                    break;
                case "statsPaiements":
                    statsPaiementsParClient(request, response);
                    break;
                case "formAjouterTerrain":
                    formAjouterTerrain(request, response);
                    break;
                case "formAjouterClient":
                    formAjouterClient(request, response);
                    break;
                case "formCreerReservation":
                    formCreerReservation(request, response);
                    break;
                case "formAjouterPaiement":
                    formAjouterPaiement(request, response);
                    break;
                case "formModifierTerrain":
                    formModifierTerrain(request, response);
                    break;
                case "formModifierReservation": // NOUVELLE ACTION
                    formModifierReservation(request, response);
                    break;
                case "supprimerTerrain":
                    supprimerTerrain(request, response);
                    break;
                case "supprimerReservation": // NOUVELLE ACTION
                    supprimerReservation(request, response);
                    break;
                case "supprimerReservationsAvant2023":
                    supprimerReservationsAvant2023(request, response);
                    break;
                case "modifierPaiement202":
                    modifierPaiement202(request, response);
                    break;
                case "formModifierPaiement":
                    formModifierPaiement(request, response);
                    break;
                case "formModifierClient":
                    formModifierClient(request, response);
                    break;
                case "supprimerClient":
                    supprimerClient(request, response);
                    break;
                case "corrigerUTF8": // NOUVELLE ACTION POUR CORRIGER UTF-8
                    corrigerUTF8BaseDeDonnees(request, response);
                    break;
                case "verifierPaiements": // NOUVELLE ACTION POUR VÉRIFIER
                    verifierPaiements(request, response);
                    break;
                default:
                    accueil(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur: " + e.getMessage());
            request.getRequestDispatcher("/erreur.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // UTF-8 déjà forcé dans service()
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "ajouterTerrain":
                    ajouterTerrain(request, response);
                    break;
                case "ajouterClient":
                    ajouterClient(request, response);
                    break;
                case "creerReservation":
                    creerReservation(request, response);
                    break;
                case "modifierReservation": // NOUVELLE ACTION
                    modifierReservation(request, response);
                    break;
                case "ajouterPaiement":
                    ajouterPaiementUTF8(request, response); // Version UTF-8
                    break;
                case "modifierTerrain":
                    modifierTerrain(request, response);
                    break;
                case "modifierModePaiement":
                    modifierModePaiement(request, response);
                    break;
                case "executerModifierPaiement":
                    executerModifierPaiement(request, response);
                    break;
                case "modifierClient":
                    modifierClient(request, response);
                    break;
                default:
                    response.sendRedirect("controller?action=accueil");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erreur", "Erreur POST: " + e.getMessage());
            request.getRequestDispatcher("/erreur.jsp").forward(request, response);
        }
    }
    
    // ========== MÉTHODE DE CORRECTION UTF-8 ==========
    private String corrigerEncodageUTF8(String texte) {
        if (texte == null) return null;
        
        System.out.println("Avant correction UTF-8: " + texte);
        
        String corrige = texte;
        // Correction des séquences mal encodées (ISO-8859-1 interprété comme UTF-8)
        corrige = corrige.replace("Ã¨", "è")
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
                        .replace("Ã§", "ç")
                        .replace("Ã‰", "É")
                        .replace("Ã€", "À")
                        .replace("Ã‡", "Ç");
        
        if (!texte.equals(corrige)) {
            System.out.println("Après correction UTF-8: " + corrige);
        }
        
        return corrige;
    }
    
    // ========== NOUVELLE MÉTHODE POUR CORRIGER LA BASE DE DONNÉES ==========
    private void corrigerUTF8BaseDeDonnees(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("=== DÉBUT CORRECTION UTF-8 BASE DE DONNÉES ===");
        
        try {
            // Tenter d'appeler la méthode de correction de l'EJB
            // Si la méthode n'existe pas, on utilise une approche alternative
            try {
                // Cette méthode doit exister dans ILocalPaiement et LocalPaiementImpl
                metierPaiement.corrigerEncodagePaiements();
                request.setAttribute("messageSucces", 
                    "✅ Correction UTF-8 appliquée avec succès!<br>" +
                    "Tous les paiements ont été vérifiés et corrigés.");
                    
            } catch (NoSuchMethodError | Exception e) {
                // Fallback: corriger manuellement via le service existant
                System.out.println("Méthode corrigerEncodagePaiements() non disponible, fallback...");
                
                // Récupérer tous les paiements et les corriger un par un
                List<Paiement> paiements = metierPaiement.tousLesPaiements();
                int corriges = 0;
                
                if (paiements != null) {
                    for (Paiement p : paiements) {
                        if (p.getModePaiement() != null && p.getModePaiement().contains("Ã")) {
                            String ancien = p.getModePaiement();
                            String nouveau = corrigerEncodageUTF8(ancien);
                            if (!ancien.equals(nouveau)) {
                                p.setModePaiement(nouveau);
                                metierPaiement.modifierPaiement(p);
                                corriges++;
                                System.out.println("Corrigé: " + ancien + " -> " + nouveau);
                            }
                        }
                    }
                }
                
                request.setAttribute("messageSucces", 
                    "✅ Correction UTF-8 appliquée manuellement!<br>" +
                    corriges + " paiement(s) corrigé(s).");
            }
            
        } catch (Exception e) {
            System.err.println("Erreur lors de la correction UTF-8: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("messageErreur", 
                "❌ Erreur lors de la correction UTF-8: " + e.getMessage());
        }
        
        // Rediriger vers la liste des paiements
        listerPaiements(request, response);
    }
    
    // ========== NOUVELLE MÉTHODE POUR VÉRIFIER LES PAIEMENTS ==========
    private void verifierPaiements(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("=== VÉRIFICATION DES PAIEMENTS ===");
        
        List<Paiement> paiements = metierPaiement.tousLesPaiements();
        int total = 0;
        int avecReservation = 0;
        int utf8Probleme = 0;
        
        if (paiements != null) {
            total = paiements.size();
            
            for (Paiement p : paiements) {
                // Vérifier si le paiement a une réservation
                if (p.getReservation() != null) {
                    avecReservation++;
                }
                
                // Vérifier les problèmes UTF-8
                if (p.getModePaiement() != null && p.getModePaiement().contains("Ã")) {
                    utf8Probleme++;
                }
                
                // Log détaillé
                System.out.println("Paiement #" + p.getIdPaiement() + 
                    " - Réservation: " + (p.getReservation() != null ? p.getReservation().getIdReservation() : "NULL") +
                    " - Mode: " + p.getModePaiement() +
                    " - UTF-8 OK: " + (p.getModePaiement() != null && !p.getModePaiement().contains("Ã")));
            }
        }
        
        request.setAttribute("messageSucces", 
            "📊 Rapport de vérification:<br>" +
            "• Total paiements: " + total + "<br>" +
            "• Avec réservation: " + avecReservation + "<br>" +
            "• Problèmes UTF-8: " + utf8Probleme + "<br>" +
            "• Pourcentage valides: " + (total > 0 ? (avecReservation * 100 / total) : 0) + "%");
        
        listerPaiements(request, response);
    }
    
    // ========== NOUVELLES MÉTHODES POUR LA MODIFICATION/SUPPRESSION DES RÉSERVATIONS ==========
    
    private void formModifierReservation(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            
            // Récupérer la réservation à modifier
            Reservation reservation = metierReservation.trouverReservationParId(id);
            
            if (reservation == null) {
                request.setAttribute("messageErreur", 
                    "<div class='alert alert-danger'>" +
                    "❌ <strong>Erreur :</strong> Réservation #" + id + " non trouvée!" +
                    "</div>");
                listerReservations(request, response);
                return;
            }
            
            // Récupérer les listes de clients et terrains pour les dropdowns
            List<Client> clients = metierClient.tousLesClients();
            List<Terrain> terrains = metierTerrain.tousLesTerrains();
            
            request.setAttribute("reservation", reservation);
            request.setAttribute("clients", clients);
            request.setAttribute("terrains", terrains);
            
            request.getRequestDispatcher("/formModifierReservation.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            request.setAttribute("messageErreur", 
                "<div class='alert alert-danger'>" +
                "❌ <strong>Erreur :</strong> ID invalide!" +
                "</div>");
            listerReservations(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("messageErreur", 
                "<div class='alert alert-danger'>" +
                "❌ <strong>Erreur :</strong> " + e.getMessage() +
                "</div>");
            listerReservations(request, response);
        }
    }
    
    private void supprimerReservation(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            Integer id = Integer.parseInt(request.getParameter("id"));
            
            // Récupérer la réservation pour afficher ses infos avant suppression
            Reservation reservation = metierReservation.trouverReservationParId(id);
            
            if (reservation == null) {
                request.setAttribute("messageErreur", 
                    "<div class='alert alert-danger'>" +
                    "❌ <strong>Erreur :</strong> Réservation #" + id + " non trouvée!" +
                    "</div>");
            } else {
                // Enregistrer les infos avant suppression pour le message
                String clientNom = reservation.getClient() != null ? reservation.getClient().getNom() : "Inconnu";
                String terrainNom = reservation.getTerrain() != null ? reservation.getTerrain().getNom() : "Inconnu";
                Date dateReservation = reservation.getDateReservation();
                
                // Supprimer la réservation
                metierReservation.supprimerReservation(id);
                
                // Formater la date pour l'affichage
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateFormatee = dateReservation != null ? dateFormat.format(dateReservation) : "Date inconnue";
                
                request.setAttribute("messageSucces", 
                    "<div class='alert alert-success'>" +
                    "✅ <strong>Succès !</strong> Réservation #" + id + " supprimée avec succès.<br>" +
                    "• Client : " + clientNom + "<br>" +
                    "• Terrain : " + terrainNom + "<br>" +
                    "• Date : " + dateFormatee +
                    "</div>");
            }
            
        } catch (NumberFormatException e) {
            request.setAttribute("messageErreur", 
                "<div class='alert alert-danger'>" +
                "❌ <strong>Erreur :</strong> ID invalide!" +
                "</div>");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("messageErreur", 
                "<div class='alert alert-danger'>" +
                "❌ <strong>Erreur :</strong> " + e.getMessage() +
                "</div>");
        }
        
        // Rediriger vers la liste des réservations
        listerReservations(request, response);
    }
    
    // ========== MÉTHODES MODIFIÉES POUR LA VALIDATION DES RÉSERVATIONS ==========
    
private void creerReservation(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    
    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    System.out.println("🎬 DÉBUT creerReservation dans ControleurServlet");
    System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    
    try {
        // Récupération des paramètres
        String clientIdStr = request.getParameter("clientId");
        String terrainIdStr = request.getParameter("terrainId");
        String dateReservationStr = request.getParameter("dateReservation");
        String heureDebutStr = request.getParameter("heureDebut");
        String heureFinStr = request.getParameter("heureFin");
        
        System.out.println("📋 Paramètres reçus:");
        System.out.println("   clientId: " + clientIdStr);
        System.out.println("   terrainId: " + terrainIdStr);
        System.out.println("   dateReservation: " + dateReservationStr);
        System.out.println("   heureDebut: " + heureDebutStr);
        System.out.println("   heureFin: " + heureFinStr);
        
        // Validation des paramètres
        if (clientIdStr == null || terrainIdStr == null || dateReservationStr == null || 
            heureDebutStr == null || heureFinStr == null) {
            System.err.println("❌ Paramètres manquants!");
            request.setAttribute("messageErreur", 
                "<div class='alert alert-danger'>" +
                "❌ <strong>Erreur :</strong> Tous les champs sont obligatoires!" +
                "</div>");
            formCreerReservation(request, response);
            return;
        }
        
        Long clientId = Long.parseLong(clientIdStr);
        Long terrainId = Long.parseLong(terrainIdStr);
        
        System.out.println("🔍 Recherche du client ID: " + clientId);
        Client client = metierClient.trouverClientParId(clientId);
        
        System.out.println("🔍 Recherche du terrain ID: " + terrainId);
        Terrain terrain = metierTerrain.trouverTerrainParId(terrainId);
        
        if (client == null) {
            System.err.println("❌ Client non trouvé: " + clientId);
            request.setAttribute("messageErreur", 
                "<div class='alert alert-danger'>" +
                "❌ <strong>Erreur :</strong> Client non trouvé!" +
                "</div>");
            formCreerReservation(request, response);
            return;
        }
        System.out.println("✅ Client trouvé: " + client.getNom());
        
        if (terrain == null) {
            System.err.println("❌ Terrain non trouvé: " + terrainId);
            request.setAttribute("messageErreur", 
                "<div class='alert alert-danger'>" +
                "❌ <strong>Erreur :</strong> Terrain non trouvé!" +
                "</div>");
            formCreerReservation(request, response);
            return;
        }
        System.out.println("✅ Terrain trouvé: " + terrain.getNom());
        
        // Parsing des dates
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateReservation = dateFormat.parse(dateReservationStr);
        
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date heureDebut = timeFormat.parse(heureDebutStr);
        Date heureFin = timeFormat.parse(heureFinStr);
        
        System.out.println("📅 Dates parsées:");
        System.out.println("   Date: " + dateReservation);
        System.out.println("   Début: " + heureDebut);
        System.out.println("   Fin: " + heureFin);
        
        // VALIDATION 1: Heure de début avant heure de fin
        if (heureDebut.after(heureFin) || heureDebut.equals(heureFin)) {
            System.err.println("❌ Validation échouée: heure de début >= heure de fin");
            request.setAttribute("messageErreur", 
                "<div class='alert alert-danger'>" +
                "<h4>❌ Erreur de saisie</h4>" +
                "<p><strong>Problème :</strong> L'heure de début doit être avant l'heure de fin!</p>" +
                "<p><strong>Votre saisie :</strong></p>" +
                "<ul>" +
                "<li>Heure de début : <strong>" + heureDebutStr + "</strong></li>" +
                "<li>Heure de fin : <strong>" + heureFinStr + "</strong></li>" +
                "</ul>" +
                "<p>⚠️ Veuillez corriger les horaires.</p>" +
                "</div>");
            request.setAttribute("clientIdSaisi", clientId);
            request.setAttribute("terrainIdSaisi", terrainId);
            request.setAttribute("dateReservationSaisie", dateReservationStr);
            request.setAttribute("heureDebutSaisie", heureDebutStr);
            request.setAttribute("heureFinSaisie", heureFinStr);
            formCreerReservation(request, response);
            return;
        }
        System.out.println("✅ Validation 1 OK: heure de début < heure de fin");
        
        // VALIDATION 2: Vérifier que CE CLIENT n'a pas déjà une réservation qui chevauche pour CE TERRAIN à CETTE DATE
        System.out.println("🔍 Vérification: ce client a-t-il une réservation qui chevauche à cette date ?");
        
        // Normaliser la date pour la comparaison (sans heure)
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(dateReservation);
        calDate.set(Calendar.HOUR_OF_DAY, 0);
        calDate.set(Calendar.MINUTE, 0);
        calDate.set(Calendar.SECOND, 0);
        calDate.set(Calendar.MILLISECOND, 0);
        Date dateOnly = calDate.getTime();
        
        // Récupérer toutes les réservations de ce client pour ce terrain
        List<Reservation> reservationsDuClient = metierReservation.reservationsParTerrain(terrainId);
        List<Reservation> conflits = new ArrayList<>();
        SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat displayTimeFormat = new SimpleDateFormat("HH:mm");
        
        for (Reservation r : reservationsDuClient) {
            // Vérifier si c'est une réservation de CE client
            if (r.getClient() != null && r.getClient().getIdClient().equals(clientId)) {
                if (r.getDateReservation() != null) {
                    // Normaliser la date de la réservation existante
                    Calendar calResa = Calendar.getInstance();
                    calResa.setTime(r.getDateReservation());
                    calResa.set(Calendar.HOUR_OF_DAY, 0);
                    calResa.set(Calendar.MINUTE, 0);
                    calResa.set(Calendar.SECOND, 0);
                    calResa.set(Calendar.MILLISECOND, 0);
                    Date dateResaOnly = calResa.getTime();
                    
                    // Vérifier si c'est le même jour
                    if (dateOnly.equals(dateResaOnly)) {
                        // Vérifier le chevauchement horaire
                        boolean chevauche = 
                            (r.getHeureDebut().before(heureFin) && r.getHeureFin().after(heureDebut)) ||
                            (r.getHeureDebut().equals(heureDebut) && r.getHeureFin().equals(heureFin)) ||
                            (r.getHeureDebut().before(heureDebut) && r.getHeureFin().after(heureFin)) ||
                            (r.getHeureDebut().after(heureDebut) && r.getHeureFin().before(heureFin));
                        
                        if (chevauche) {
                            conflits.add(r);
                        }
                    }
                }
            }
        }
        
        if (!conflits.isEmpty()) {
            System.err.println("❌ Ce client a déjà " + conflits.size() + " réservation(s) qui chevauche(nt) !");
            
            StringBuilder detailsConflits = new StringBuilder();
            for (Reservation r : conflits) {
                // Déterminer le type de conflit
                String typeConflit = "";
                if (r.getHeureDebut().equals(heureDebut) && r.getHeureFin().equals(heureFin)) {
                    typeConflit = "<span style='color: #dc3545;'>⚠️ Créneau identique</span>";
                } else if (r.getHeureDebut().before(heureDebut) && r.getHeureFin().after(heureFin)) {
                    typeConflit = "<span style='color: #fd7e14;'>⚠️ Votre créneau est inclus dans cette réservation</span>";
                } else if (r.getHeureDebut().after(heureDebut) && r.getHeureFin().before(heureFin)) {
                    typeConflit = "<span style='color: #ffc107;'>⚠️ Cette réservation est incluse dans votre créneau</span>";
                } else {
                    typeConflit = "<span style='color: #e83e8c;'>⚠️ Chevauchement partiel</span>";
                }
                
                detailsConflits.append(
                    "<div style='background: #fff; border: 1px solid #ffc107; padding: 10px; margin: 10px 0; border-radius: 4px;'>" +
                    "<strong>Réservation #" + r.getIdReservation() + "</strong><br>" +
                    "📅 Date : " + displayDateFormat.format(r.getDateReservation()) + "<br>" +
                    "🕐 Horaire : <strong>" + displayTimeFormat.format(r.getHeureDebut()) + " - " + 
                    displayTimeFormat.format(r.getHeureFin()) + "</strong><br>" +
                    typeConflit +
                    "</div>"
                );
            }
            
            String messageErreur = 
                "<div class='alert alert-danger' style='max-width: 800px;'>" +
                "<h4>❌ Conflit de réservation pour ce client !</h4>" +
                "<p><strong>Ce client a déjà une ou plusieurs réservations qui chevauchent cet horaire.</strong></p>" +
                
                "<div style='background: #e7f3fe; border-left: 4px solid #2196F3; padding: 15px; margin: 15px 0;'>" +
                "<h5 style='margin-top: 0;'>📋 Votre demande :</h5>" +
                "<ul style='margin: 5px 0;'>" +
                "<li><strong>Client :</strong> " + client.getNom() + "</li>" +
                "<li><strong>Terrain :</strong> " + terrain.getNom() + " (" + terrain.getType() + ")</li>" +
                "<li><strong>Date :</strong> " + displayDateFormat.format(dateReservation) + "</li>" +
                "<li><strong>Horaire demandé :</strong> <strong style='color: #dc3545;'>" + heureDebutStr + " - " + heureFinStr + "</strong></li>" +
                "</ul>" +
                "</div>" +
                
                "<div style='background: #fff3cd; border-left: 4px solid #ffc107; padding: 15px; margin: 15px 0;'>" +
                "<h5 style='margin-top: 0;'>⚠️ Réservation(s) du client en conflit (" + conflits.size() + ") :</h5>" +
                detailsConflits.toString() +
                "</div>" +
                
                "<div style='background: #f8d7da; border-left: 4px solid #dc3545; padding: 15px; margin: 15px 0;'>" +
                "<h5 style='margin-top: 0;'>🚫 Règle :</h5>" +
                "<p><strong>Un client ne peut pas avoir deux réservations qui se chevauchent pour le même terrain le même jour.</strong></p>" +
                "<p>Plusieurs clients peuvent réserver le même terrain au même moment, mais un client ne peut pas se chevaucher lui-même.</p>" +
                "</div>" +
                
                "<div style='background: #d1ecf1; border-left: 4px solid #17a2b8; padding: 15px; margin: 15px 0;'>" +
                "<h5 style='margin-top: 0;'>💡 Solutions suggérées :</h5>" +
                "<ul>" +
                "<li>Choisissez un <strong>créneau horaire différent</strong> qui ne chevauche pas</li>" +
                "<li>Choisissez un <strong>autre terrain</strong> pour ce client</li>" +
                "<li>Choisissez une <strong>autre date</strong></li>" +
                "<li>Sélectionnez un <strong>autre client</strong> pour cette réservation</li>" +
                "<li>Modifiez une des <strong>réservations existantes</strong> au lieu d'en créer une nouvelle</li>" +
                "</ul>" +
                "</div>" +
                "</div>";
            
            request.setAttribute("messageErreur", messageErreur);
            
            // Pré-remplir le formulaire avec les valeurs saisies
            request.setAttribute("clientIdSaisi", clientId);
            request.setAttribute("terrainIdSaisi", terrainId);
            request.setAttribute("dateReservationSaisie", dateReservationStr);
            request.setAttribute("heureDebutSaisie", heureDebutStr);
            request.setAttribute("heureFinSaisie", heureFinStr);
            
            formCreerReservation(request, response);
            return;
        }
        
        System.out.println("✅ Validation 2 OK: aucun conflit pour ce client");
        
        // Création de la réservation
        System.out.println("🏗️ Création de l'objet Reservation...");
        Reservation reservation = new Reservation();
        reservation.setClient(client);
        reservation.setTerrain(terrain);
        reservation.setDateReservation(dateReservation);
        reservation.setHeureDebut(heureDebut);
        reservation.setHeureFin(heureFin);
        
        System.out.println("✅ Objet Reservation créé");
        System.out.println("📤 Appel de metierReservation.ajouterReservation()...");
        
        // AJOUT CRITIQUE
        metierReservation.ajouterReservation(reservation);
        
        System.out.println("✅ metierReservation.ajouterReservation() terminé");
        System.out.println("   ID généré: " + reservation.getIdReservation());
        
        // Message de succès
        String messageSucces = 
            "<div class='alert alert-success' style='max-width: 800px;'>" +
            "<h4>✅ Réservation créée avec succès !</h4>" +
            
            "<div style='background: #d4edda; border-left: 4px solid #28a745; padding: 15px; margin: 15px 0;'>" +
            "<h5 style='margin-top: 0;'>📋 Détails de la réservation :</h5>" +
            "<table style='width: 100%; border-collapse: collapse;'>" +
            "<tr><td style='padding: 5px;'><strong>N° Réservation :</strong></td><td style='padding: 5px;'><strong style='color: #28a745; font-size: 18px;'>#" + reservation.getIdReservation() + "</strong></td></tr>" +
            "<tr><td style='padding: 5px;'><strong>Client :</strong></td><td style='padding: 5px;'>" + client.getNom() + "</td></tr>" +
            "<tr><td style='padding: 5px;'><strong>Email :</strong></td><td style='padding: 5px;'>" + client.getEmail() + "</td></tr>" +
            "<tr><td style='padding: 5px;'><strong>Terrain :</strong></td><td style='padding: 5px;'>" + terrain.getNom() + " (" + terrain.getType() + ")</td></tr>" +
            "<tr><td style='padding: 5px;'><strong>Date :</strong></td><td style='padding: 5px;'>" + displayDateFormat.format(dateReservation) + "</td></tr>" +
            "<tr><td style='padding: 5px;'><strong>Horaire :</strong></td><td style='padding: 5px;'><strong style='color: #28a745;'>" + displayTimeFormat.format(heureDebut) + " - " + displayTimeFormat.format(heureFin) + "</strong></td></tr>" +
            "</table>" +
            "</div>" +
            
            "<div style='margin-top: 20px; padding: 10px; background: #fff; border: 1px solid #ddd; border-radius: 4px;'>" +
            "<strong>Actions disponibles :</strong><br>" +
            "<a href='controller?action=formCreerReservation' style='margin: 5px; display: inline-block; padding: 8px 15px; background: #007bff; color: white; text-decoration: none; border-radius: 4px;'>➕ Nouvelle réservation</a> " +
            "<a href='controller?action=reservations' style='margin: 5px; display: inline-block; padding: 8px 15px; background: #28a745; color: white; text-decoration: none; border-radius: 4px;'>📋 Voir toutes les réservations</a> " +
            "<a href='controller?action=formModifierReservation&id=" + reservation.getIdReservation() + "' style='margin: 5px; display: inline-block; padding: 8px 15px; background: #ffc107; color: #000; text-decoration: none; border-radius: 4px;'>✏️ Modifier cette réservation</a>" +
            "</div>" +
            "</div>";
        
        request.setAttribute("messageSucces", messageSucces);
        
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("🎉 FIN creerReservation - SUCCÈS");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        
        // Redirection vers la liste
        listerReservations(request, response);
        
    } catch (ParseException e) {
        System.err.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.err.println("❌ ERREUR ParseException");
        System.err.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        e.printStackTrace();
        request.setAttribute("messageErreur", 
            "<div class='alert alert-danger'>" +
            "<h4>❌ Erreur de format</h4>" +
            "<p><strong>Problème :</strong> Format de date ou heure invalide!</p>" +
            "<p><strong>Détail technique :</strong> " + e.getMessage() + "</p>" +
            "</div>");
        formCreerReservation(request, response);
    } catch (NumberFormatException e) {
        System.err.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.err.println("❌ ERREUR NumberFormatException");
        System.err.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        e.printStackTrace();
        request.setAttribute("messageErreur", 
            "<div class='alert alert-danger'>" +
            "<h4>❌ Erreur de saisie</h4>" +
            "<p><strong>Problème :</strong> Identifiant invalide!</p>" +
            "<p><strong>Détail technique :</strong> " + e.getMessage() + "</p>" +
            "</div>");
        formCreerReservation(request, response);
    } catch (Exception e) {
        System.err.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.err.println("❌ ERREUR Exception générale");
        System.err.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        e.printStackTrace();
        request.setAttribute("messageErreur", 
            "<div class='alert alert-danger'>" +
            "<h4>❌ Erreur système</h4>" +
            "<p><strong>Une erreur inattendue s'est produite.</strong></p>" +
            "<p><strong>Détail technique :</strong> " + e.getMessage() + "</p>" +
            "</div>");
        formCreerReservation(request, response);
    }
}
    
private void modifierReservation(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    
    try {
        Integer id = Integer.parseInt(request.getParameter("id"));
        Long clientId = Long.parseLong(request.getParameter("clientId"));
        Long terrainId = Long.parseLong(request.getParameter("terrainId"));
        String dateReservationStr = request.getParameter("dateReservation");
        String heureDebutStr = request.getParameter("heureDebut");
        String heureFinStr = request.getParameter("heureFin");
        
        // Récupérer la réservation existante
        Reservation reservation = metierReservation.trouverReservationParId(id);
        
        if (reservation == null) {
            request.setAttribute("messageErreur", 
                "<div class='alert alert-danger'>" +
                "❌ <strong>Erreur :</strong> Réservation #" + id + " non trouvée!" +
                "</div>");
            listerReservations(request, response);
            return;
        }
        
        // Mettre à jour les informations
        Client client = metierClient.trouverClientParId(clientId);
        Terrain terrain = metierTerrain.trouverTerrainParId(terrainId);
        
        if (client == null) {
            request.setAttribute("messageErreur", 
                "<div class='alert alert-danger'>" +
                "❌ <strong>Erreur :</strong> Client non trouvé!" +
                "</div>");
            formModifierReservation(request, response);
            return;
        }
        
        if (terrain == null) {
            request.setAttribute("messageErreur", 
                "<div class='alert alert-danger'>" +
                "❌ <strong>Erreur :</strong> Terrain non trouvé!" +
                "</div>");
            formModifierReservation(request, response);
            return;
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateReservation = dateFormat.parse(dateReservationStr);
        
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date heureDebut = timeFormat.parse(heureDebutStr);
        Date heureFin = timeFormat.parse(heureFinStr);
        
        // VALIDATION 1: Vérifier que l'heure de début est avant l'heure de fin
        if (heureDebut.after(heureFin) || heureDebut.equals(heureFin)) {
            request.setAttribute("messageErreur", 
                "<div class='alert alert-danger'>" +
                "<h4>❌ Erreur de saisie</h4>" +
                "<p><strong>Problème :</strong> L'heure de début doit être avant l'heure de fin!</p>" +
                "<p><strong>Votre saisie :</strong></p>" +
                "<ul>" +
                "<li>Heure de début : <strong>" + heureDebutStr + "</strong></li>" +
                "<li>Heure de fin : <strong>" + heureFinStr + "</strong></li>" +
                "</ul>" +
                "</div>");
            formModifierReservation(request, response);
            return;
        }
        
        // VALIDATION 2: Vérifier que CE CLIENT n'a pas déjà une réservation qui chevauche (EXCLURE la réservation actuelle)
        SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat displayTimeFormat = new SimpleDateFormat("HH:mm");
        
        // Normaliser la date pour la comparaison
        Calendar calDate = Calendar.getInstance();
        calDate.setTime(dateReservation);
        calDate.set(Calendar.HOUR_OF_DAY, 0);
        calDate.set(Calendar.MINUTE, 0);
        calDate.set(Calendar.SECOND, 0);
        calDate.set(Calendar.MILLISECOND, 0);
        Date dateOnly = calDate.getTime();
        
        // Récupérer toutes les réservations de ce client pour ce terrain
        List<Reservation> reservationsDuClient = metierReservation.reservationsParTerrain(terrainId);
        List<Reservation> conflits = new ArrayList<>();
        
        for (Reservation r : reservationsDuClient) {
            // EXCLURE la réservation en cours de modification
            if (r.getIdReservation().equals(id)) {
                continue;
            }
            
            // Vérifier si c'est une réservation de CE client
            if (r.getClient() != null && r.getClient().getIdClient().equals(clientId)) {
                if (r.getDateReservation() != null) {
                    // Normaliser la date de la réservation existante
                    Calendar calResa = Calendar.getInstance();
                    calResa.setTime(r.getDateReservation());
                    calResa.set(Calendar.HOUR_OF_DAY, 0);
                    calResa.set(Calendar.MINUTE, 0);
                    calResa.set(Calendar.SECOND, 0);
                    calResa.set(Calendar.MILLISECOND, 0);
                    Date dateResaOnly = calResa.getTime();
                    
                    // Vérifier si c'est le même jour
                    if (dateOnly.equals(dateResaOnly)) {
                        // Vérifier le chevauchement horaire
                        boolean chevauche = 
                            (r.getHeureDebut().before(heureFin) && r.getHeureFin().after(heureDebut)) ||
                            (r.getHeureDebut().equals(heureDebut) && r.getHeureFin().equals(heureFin)) ||
                            (r.getHeureDebut().before(heureDebut) && r.getHeureFin().after(heureFin)) ||
                            (r.getHeureDebut().after(heureDebut) && r.getHeureFin().before(heureFin));
                        
                        if (chevauche) {
                            conflits.add(r);
                        }
                    }
                }
            }
        }
        
        if (!conflits.isEmpty()) {
            StringBuilder detailsConflits = new StringBuilder();
            for (Reservation r : conflits) {
                // Déterminer le type de conflit
                String typeConflit = "";
                if (r.getHeureDebut().equals(heureDebut) && r.getHeureFin().equals(heureFin)) {
                    typeConflit = "<span style='color: #dc3545;'>⚠️ Créneau identique</span>";
                } else if (r.getHeureDebut().before(heureDebut) && r.getHeureFin().after(heureFin)) {
                    typeConflit = "<span style='color: #fd7e14;'>⚠️ Votre créneau est inclus dans cette réservation</span>";
                } else if (r.getHeureDebut().after(heureDebut) && r.getHeureFin().before(heureFin)) {
                    typeConflit = "<span style='color: #ffc107;'>⚠️ Cette réservation est incluse dans votre créneau</span>";
                } else {
                    typeConflit = "<span style='color: #e83e8c;'>⚠️ Chevauchement partiel</span>";
                }
                
                detailsConflits.append(
                    "<div style='background: #fff; border: 1px solid #ffc107; padding: 10px; margin: 10px 0; border-radius: 4px;'>" +
                    "<strong>Réservation #" + r.getIdReservation() + "</strong><br>" +
                    "📅 Date : " + displayDateFormat.format(r.getDateReservation()) + "<br>" +
                    "🕐 Horaire : <strong>" + displayTimeFormat.format(r.getHeureDebut()) + " - " + 
                    displayTimeFormat.format(r.getHeureFin()) + "</strong><br>" +
                    typeConflit +
                    "</div>"
                );
            }
            
            String messageErreur = 
                "<div class='alert alert-danger' style='max-width: 800px;'>" +
                "<h4>❌ Conflit de réservation pour ce client !</h4>" +
                "<p><strong>Ce client a déjà une ou plusieurs réservations qui chevauchent cet horaire.</strong></p>" +
                
                "<div style='background: #e7f3fe; border-left: 4px solid #2196F3; padding: 15px; margin: 15px 0;'>" +
                "<h5 style='margin-top: 0;'>📋 Modification demandée :</h5>" +
                "<ul style='margin: 5px 0;'>" +
                "<li><strong>Client :</strong> " + client.getNom() + "</li>" +
                "<li><strong>Terrain :</strong> " + terrain.getNom() + " (" + terrain.getType() + ")</li>" +
                "<li><strong>Date :</strong> " + displayDateFormat.format(dateReservation) + "</li>" +
                "<li><strong>Horaire demandé :</strong> <strong style='color: #dc3545;'>" + heureDebutStr + " - " + heureFinStr + "</strong></li>" +
                "</ul>" +
                "</div>" +
                
                "<div style='background: #fff3cd; border-left: 4px solid #ffc107; padding: 15px; margin: 15px 0;'>" +
                "<h5 style='margin-top: 0;'>⚠️ Autre(s) réservation(s) du client en conflit (" + conflits.size() + ") :</h5>" +
                detailsConflits.toString() +
                "</div>" +
                
                "<div style='background: #f8d7da; border-left: 4px solid #dc3545; padding: 15px; margin: 15px 0;'>" +
                "<h5 style='margin-top: 0;'>🚫 Règle :</h5>" +
                "<p><strong>Un client ne peut pas avoir deux réservations qui se chevauchent pour le même terrain le même jour.</strong></p>" +
                "</div>" +
                "</div>";
            
            request.setAttribute("messageErreur", messageErreur);
            formModifierReservation(request, response);
            return;
        }
        
        // Enregistrer les anciennes valeurs pour le message
        String ancienClient = reservation.getClient().getNom();
        String ancienTerrain = reservation.getTerrain().getNom();
        Date ancienneDate = reservation.getDateReservation();
        Date ancienneHeureDebut = reservation.getHeureDebut();
        Date ancienneHeureFin = reservation.getHeureFin();
        
        // Mettre à jour la réservation
        reservation.setClient(client);
        reservation.setTerrain(terrain);
        reservation.setDateReservation(dateReservation);
        reservation.setHeureDebut(heureDebut);
        reservation.setHeureFin(heureFin);
        
        // Sauvegarder les modifications
        metierReservation.modifierReservation(reservation);
        
        // Message de succès détaillé
        String messageSucces = 
            "<div class='alert alert-success' style='max-width: 800px;'>" +
            "<h4>✅ Réservation #" + id + " modifiée avec succès !</h4>" +
            
            "<div style='background: #fff3cd; border-left: 4px solid #ffc107; padding: 15px; margin: 15px 0;'>" +
            "<h5 style='margin-top: 0;'>📋 Anciennes informations :</h5>" +
            "<ul>" +
            "<li><strong>Client :</strong> " + ancienClient + "</li>" +
            "<li><strong>Terrain :</strong> " + ancienTerrain + "</li>" +
            "<li><strong>Date :</strong> " + displayDateFormat.format(ancienneDate) + "</li>" +
            "<li><strong>Horaire :</strong> " + displayTimeFormat.format(ancienneHeureDebut) + " - " + 
                        displayTimeFormat.format(ancienneHeureFin) + "</li>" +
            "</ul>" +
            "</div>" +
            
            "<div style='background: #d4edda; border-left: 4px solid #28a745; padding: 15px; margin: 15px 0;'>" +
            "<h5 style='margin-top: 0;'>📋 Nouvelles informations :</h5>" +
            "<ul>" +
            "<li><strong>Client :</strong> " + client.getNom() + "</li>" +
            "<li><strong>Terrain :</strong> " + terrain.getNom() + " (" + terrain.getType() + ")</li>" +
            "<li><strong>Date :</strong> " + displayDateFormat.format(dateReservation) + "</li>" +
            "<li><strong>Horaire :</strong> <strong style='color: #28a745;'>" + displayTimeFormat.format(heureDebut) + " - " + 
                        displayTimeFormat.format(heureFin) + "</strong></li>" +
            "</ul>" +
            "</div>" +
            "</div>";
        
        request.setAttribute("messageSucces", messageSucces);
        
        // Rediriger vers la liste des réservations
        listerReservations(request, response);
        
    } catch (ParseException e) {
        request.setAttribute("messageErreur", 
            "<div class='alert alert-danger'>" +
            "<h4>❌ Erreur de format</h4>" +
            "<p><strong>Problème :</strong> Format de date ou heure invalide!</p>" +
            "</div>");
        formModifierReservation(request, response);
    } catch (NumberFormatException e) {
        request.setAttribute("messageErreur", 
            "<div class='alert alert-danger'>" +
            "<h4>❌ Erreur de saisie</h4>" +
            "<p><strong>Problème :</strong> ID invalide!</p>" +
            "</div>");
        listerReservations(request, response);
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("messageErreur", 
            "<div class='alert alert-danger'>" +
            "<h4>❌ Erreur système</h4>" +
            "<p><strong>Détail :</strong> " + e.getMessage() + "</p>" +
            "</div>");
        formModifierReservation(request, response);
    }
}
    
    // ========== MÉTHODES PRINCIPALES ==========
    
    private void accueil(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            List<Terrain> terrains = metierTerrain.tousLesTerrains();
            List<Client> clients = metierClient.tousLesClients();
            List<Reservation> reservations = metierReservation.toutesLesReservations();
            List<Paiement> paiements = metierPaiement.tousLesPaiements();
            
            // CORRECTION UTF-8 POUR LES PAIEMENTS
            if (paiements != null) {
                for (Paiement p : paiements) {
                    if (p.getModePaiement() != null) {
                        String corrige = corrigerEncodageUTF8(p.getModePaiement());
                        p.setModePaiement(corrige);
                    }
                }
            }
            
            request.setAttribute("terrains", terrains);
            request.setAttribute("clients", clients);
            request.setAttribute("reservations", reservations);
            request.setAttribute("paiements", paiements);
            
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Erreur dans accueil: " + e.getMessage(), e);
        }
    }
    
    private void listerPaiements(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        System.out.println("=== LISTE DES PAIEMENTS ===");
        
        // Tenter une correction automatique au chargement
        try {
            // Vérifier si la méthode existe avant de l'appeler
            List<Paiement> testPaiements = metierPaiement.tousLesPaiements();
            if (testPaiements != null) {
                for (Paiement p : testPaiements) {
                    if (p.getModePaiement() != null && p.getModePaiement().contains("Ã")) {
                        System.out.println("Détection de problèmes UTF-8 dans la base");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Note: Impossible de vérifier la base au préalable");
        }
        
        List<Paiement> paiements = metierPaiement.tousLesPaiements();
        
        // LOG POUR DÉBOGAGE
        System.out.println("Nombre de paiements récupérés: " + (paiements != null ? paiements.size() : 0));
        
        if (paiements != null) {
            for (Paiement p : paiements) {
                System.out.println("Paiement ID: " + p.getIdPaiement() + 
                                 ", Montant: " + p.getMontant() + 
                                 ", Mode: " + p.getModePaiement() + 
                                 ", Réservation: " + (p.getReservation() != null ? p.getReservation().getIdReservation() : "null"));
                
                // CORRECTION UTF-8 EN MÉMOIRE POUR L'AFFICHAGE
                if (p.getModePaiement() != null) {
                    String corrige = corrigerEncodageUTF8(p.getModePaiement());
                    if (!p.getModePaiement().equals(corrige)) {
                        System.out.println("Correction en mémoire: " + p.getModePaiement() + " -> " + corrige);
                        p.setModePaiement(corrige);
                    }
                }
            }
        }
        
        request.setAttribute("paiements", paiements);
        request.setAttribute("titre", "Liste des Paiements");
        request.getRequestDispatcher("/listePaiements.jsp").forward(request, response);
    }
    
    // ========== MÉTHODE POUR AJOUTER PAIEMENT AVEC VALIDATION UTF-8 ==========
    private void ajouterPaiementUTF8(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    
    System.out.println("=== DÉBUT AJOUT PAIEMENT UTF-8 ===");
    
    // VALIDER LES PARAMÈTRES
    String reservationIdStr = request.getParameter("reservationId");
    String montantStr = request.getParameter("montant");
    String modePaiement = request.getParameter("modePaiement");
    String datePaiementStr = request.getParameter("datePaiement");
    
    // Vérification des paramètres obligatoires
    if (reservationIdStr == null || reservationIdStr.trim().isEmpty() ||
        montantStr == null || montantStr.trim().isEmpty() ||
        modePaiement == null || modePaiement.trim().isEmpty() ||
        datePaiementStr == null || datePaiementStr.trim().isEmpty()) {
        
        request.setAttribute("messageErreur", 
            "<div class='alert alert-danger'>" +
            "❌ <strong>Erreur :</strong> Tous les champs sont obligatoires!" +
            "</div>");
        formAjouterPaiement(request, response);
        return;
    }
    
    Integer reservationId;
    Double montant;
    
    try {
        reservationId = Integer.parseInt(reservationIdStr.trim());
        montant = Double.parseDouble(montantStr.trim());
        
        // Validation du montant
        if (montant <= 0) {
            request.setAttribute("messageErreur", 
                "<div class='alert alert-danger'>" +
                "❌ <strong>Erreur :</strong> Le montant doit être positif!" +
                "</div>");
        formAjouterPaiement(request, response);
            return;
        }
        
        if (montant > 10000) { // Limite de sécurité
            request.setAttribute("messageErreur", 
                "<div class='alert alert-danger'>" +
                "❌ <strong>Erreur :</strong> Le montant ne peut pas dépasser 10 000€!" +
                "</div>");
            formAjouterPaiement(request, response);
            return;
        }
        
    } catch (NumberFormatException e) {
        request.setAttribute("messageErreur", 
            "<div class='alert alert-danger'>" +
            "❌ <strong>Erreur :</strong> Format de nombre invalide!" +
            "</div>");
        formAjouterPaiement(request, response);
        return;
    }
    
    // VÉRIFIER SI LA RÉSERVATION EXISTE
    Reservation reservation = metierReservation.trouverReservationParId(reservationId);
    if (reservation == null) {
        request.setAttribute("messageErreur", 
            "<div class='alert alert-danger'>" +
            "❌ <strong>Erreur :</strong> Réservation #" + reservationId + " non trouvée!" +
            "</div>");
        formAjouterPaiement(request, response);
        return;
    }
    
    // ===== VÉRIFICATION CRITIQUE : PAIEMENT EXISTANT POUR CETTE RÉSERVATION =====
    try {
        // Méthode 1: Utiliser la nouvelle méthode optimisée
        boolean aDejaPaiement = metierPaiement.reservationADejaPaiement(reservationId);
        
        if (aDejaPaiement) {
            // Récupérer les détails du paiement existant pour afficher un message informatif
            Paiement paiementExistant = metierPaiement.trouverPaiementParReservation(reservationId);
            
            String detailsExistant = "";
            if (paiementExistant != null) {
                detailsExistant = 
                    "<br><strong>Détails du paiement existant :</strong>" +
                    "<br>• ID Paiement : " + paiementExistant.getIdPaiement() +
                    "<br>• Montant : " + paiementExistant.getMontant() + "€" +
                    "<br>• Mode : " + paiementExistant.getModePaiement() +
                    "<br>• Date : " + paiementExistant.getDatePaiement();
            }
            
            // Message d'erreur détaillé avec options
            String messageErreur = 
                "<div class='alert alert-warning'>" +
                "⚠️ <strong>Attention :</strong> Un paiement existe déjà pour la réservation #" + reservationId + "!" +
                detailsExistant +
                "</div>";
            
            request.setAttribute("messageErreur", messageErreur);
            request.setAttribute("reservationId", reservationId); // Pour pré-remplir le formulaire
            formAjouterPaiement(request, response);
            return;
        }
        
    } catch (Exception e) {
        System.err.println("⚠️ Erreur lors de la vérification du paiement existant: " + e.getMessage());
        // Continuer malgré l'erreur (ne pas bloquer l'utilisateur)
    }
    
    // CORRECTION UTF-8
    modePaiement = corrigerEncodageUTF8(modePaiement);
    System.out.println("Mode de paiement après correction: " + modePaiement);
    
    // VALIDATION DU MODE DE PAIEMENT
    List<String> modesValides = List.of("espèces", "chèque", "virement", "carte bancaire", "carte de crédit", "paypal");
    if (!modesValides.contains(modePaiement.toLowerCase())) {
        System.out.println("⚠️ Mode de paiement non standard: " + modePaiement);
        // On accepte quand même mais on log
    }
    
    // CRÉER LE PAIEMENT
    Paiement paiement = new Paiement();
    paiement.setReservation(reservation);
    paiement.setMontant(montant);
    paiement.setModePaiement(modePaiement);
    
    try {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date datePaiement = sdf.parse(datePaiementStr);
        
        // Validation de la date
        Date aujourdhui = new Date();
        if (datePaiement.after(aujourdhui)) {
            request.setAttribute("messageErreur", 
                "<div class='alert alert-danger'>" +
                "❌ <strong>Erreur :</strong> La date de paiement ne peut pas être dans le futur!" +
                "</div>");
            formAjouterPaiement(request, response);
            return;
        }
        
        paiement.setDatePaiement(datePaiement);
    } catch (ParseException e) {
        request.setAttribute("messageErreur", 
            "<div class='alert alert-danger'>" +
            "❌ <strong>Erreur :</strong> Format de date invalide. Utilisez AAAA-MM-JJ" +
            "</div>");
        formAjouterPaiement(request, response);
        return;
    }
    
    // AJOUTER LE PAIEMENT
    try {
        metierPaiement.ajouterPaiement(paiement);
        
        // Récupérer les infos pour le message de succès
        String clientNom = reservation.getClient() != null ? reservation.getClient().getNom() : "Inconnu";
        String terrainNom = reservation.getTerrain() != null ? reservation.getTerrain().getNom() : "Inconnu";
        String clientId = reservation.getClient() != null ? reservation.getClient().getIdClient().toString() : "?";
        String terrainId = reservation.getTerrain() != null ? reservation.getTerrain().getIdTerrain().toString() : "?";
        
        // MESSAGE DE SUCCÈS AVEC LIENS
        String messageSucces = 
            "<div class='alert alert-success'>" +
            "✅ <strong>Succès !</strong> Paiement ajouté avec succès." +
            "<br><br>" +
            "<strong>Détails :</strong>" +
            "<ul>" +
            "<li><strong>Montant :</strong> " + montant + "€</li>" +
            "<li><strong>Mode :</strong> " + modePaiement + "</li>" +
            "<li><strong>Date :</strong> " + datePaiementStr + "</li>" +
            "<li><strong>Réservation :</strong> #" + reservationId + "</li>" +
            "<li><strong>Client :</strong> " + clientNom + 
            " (<a href='controller?action=formModifierClient&id=" + clientId + "'>Modifier</a>)</li>" +
            "<li><strong>Terrain :</strong> " + terrainNom + 
            " (<a href='controller?action=formModifierTerrain&id=" + terrainId + "'>Modifier</a>)</li>" +
            "</ul>" +
            "<br>" +
            "<div class='actions'>" +
            "<a href='controller?action=formAjouterPaiement' class='btn btn-primary btn-sm'>" +
            "➕ Ajouter un autre paiement</a> " +
            "<a href='controller?action=paiements' class='btn btn-success btn-sm'>" +
            "📋 Voir tous les paiements</a> " +
            "<a href='controller?action=reservations' class='btn btn-info btn-sm'>" +
            "📅 Voir les réservations</a>" +
            "</div>" +
            "</div>";
        
        request.setAttribute("messageSucces", messageSucces);
        
        System.out.println("✅ Paiement ajouté avec succès: ID=" + paiement.getIdPaiement() + 
                          " pour réservation #" + reservationId);
        
    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("messageErreur", 
            "<div class='alert alert-danger'>" +
            "❌ <strong>Erreur :</strong> Impossible d'ajouter le paiement. " +
            "Détail technique: " + e.getMessage() +
            "<br><br>" +
            "<a href='controller?action=formAjouterPaiement' class='btn btn-secondary btn-sm'>" +
            "↩️ Réessayer</a>" +
            "</div>");
    }
    
    listerPaiements(request, response);
}
    
    // ========== AUTRES MÉTHODES (inchangées pour la plupart) ==========
    
// Dans ControleurServlet.java, remplacez la méthode listerTerrains() par celle-ci :

private void listerTerrains(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    
    // Récupérer le paramètre de filtrage
    String typeFiltrage = request.getParameter("type");
    
    List<Terrain> terrains;
    
    // Si un type est spécifié, filtrer par type
    if (typeFiltrage != null && !typeFiltrage.trim().isEmpty()) {
        terrains = metierTerrain.terrainsParType(typeFiltrage);
        request.setAttribute("titre", "Liste des Terrains - Type: " + typeFiltrage);
        request.setAttribute("typeFiltreActif", typeFiltrage);
    } else {
        // Sinon, afficher tous les terrains
        terrains = metierTerrain.tousLesTerrains();
        request.setAttribute("titre", "Liste des Terrains");
    }
    
    request.setAttribute("terrains", terrains);
    request.getRequestDispatcher("/listeTerrains.jsp").forward(request, response);
}

    
    private void listerClients(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Client> clients = metierClient.tousLesClients();
        request.setAttribute("clients", clients);
        request.setAttribute("titre", "Liste des Clients");
        request.getRequestDispatcher("/listeClients.jsp").forward(request, response);
    }
    
    private void listerReservations(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Reservation> reservations = metierReservation.toutesLesReservations();
        request.setAttribute("reservations", reservations);
        request.setAttribute("titre", "Liste des Réservations");
        request.getRequestDispatcher("/listeReservations.jsp").forward(request, response);
    }
    
    private void listerTerrainsNonReserves(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Terrain> terrains = metierTerrain.terrainsNonReserves();
        request.setAttribute("terrains", terrains);
        request.setAttribute("titre", "Terrains jamais réservés");
        request.getRequestDispatcher("/listeTerrains.jsp").forward(request, response);
    }
    
    private void listerClientsFootball(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Client> clients = metierClient.clientsAvecReservationsFootball();
        request.setAttribute("clients", clients);
        request.setAttribute("titre", "Clients avec réservations Football");
        request.getRequestDispatcher("/listeClients.jsp").forward(request, response);
    }
    
    private void listerReservationsMars2025(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Reservation> reservations = metierReservation.reservationsMars2025();
        request.setAttribute("reservations", reservations);
        request.setAttribute("titre", "Réservations du mois de Mars 2025");
        request.getRequestDispatcher("/listeReservations.jsp").forward(request, response);
    }
    
    private void statsReservationsParTerrain(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Object[]> stats = metierTerrain.nombreReservationsParTerrain();
        request.setAttribute("statsReservations", stats);
        request.setAttribute("titre", "Statistiques des Réservations par Terrain");
        request.getRequestDispatcher("/statsReservations.jsp").forward(request, response);
    }
    
    private void statsPaiementsParClient(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Object[]> stats = metierPaiement.totalPaiementsParClientTrie();
        request.setAttribute("statsPaiements", stats);
        request.setAttribute("titre", "Statistiques des Paiements par Client");
        request.getRequestDispatcher("/statsPaiements.jsp").forward(request, response);
    }
    
    private void formAjouterTerrain(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/formTerrain.jsp").forward(request, response);
    }
    
    private void formAjouterClient(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/formClient.jsp").forward(request, response);
    }
    
    private void formCreerReservation(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Terrain> terrains = metierTerrain.tousLesTerrains();
        List<Client> clients = metierClient.tousLesClients();
        
        request.setAttribute("terrains", terrains);
        request.setAttribute("clients", clients);
        
        request.getRequestDispatcher("/formReservation.jsp").forward(request, response);
    }
    
    private void formAjouterPaiement(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        List<Reservation> reservations = metierReservation.toutesLesReservations();
        request.setAttribute("reservations", reservations);
        request.getRequestDispatcher("/formPaiement.jsp").forward(request, response);
    }
    
    private void formModifierTerrain(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        Terrain terrain = metierTerrain.trouverTerrainParId(id);
        request.setAttribute("terrain", terrain);
        request.getRequestDispatcher("/formModifierTerrain.jsp").forward(request, response);
    }
    
    private void supprimerTerrain(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        metierTerrain.supprimerTerrain(id);
        response.sendRedirect("controller?action=terrains");
    }
    
    private void supprimerReservationsAvant2023(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        metierReservation.supprimerReservationsAvant2023();
        response.sendRedirect("controller?action=reservations");
    }
    
    private void modifierPaiement202(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            metierPaiement.modifierModePaiementReservation(202, "virement");
            request.setAttribute("messageSucces", "✅ Le mode de paiement de la réservation #202 a été modifié en 'virement' avec succès !");
        } catch (Exception e) {
            request.setAttribute("messageErreur", "❌ Erreur lors de la modification : " + e.getMessage());
        }
        
        accueil(request, response);
    }
    
    private void formModifierPaiement(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.setAttribute("reservationIdParDefaut", 202);
        request.setAttribute("modeParDefaut", "virement");
        request.setAttribute("afficherFormulairePaiement", true);
        
        accueil(request, response);
    }
    
    private void formModifierClient(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        try {
            Client client = metierClient.trouverClientParId(id);
            if (client != null) {
                request.setAttribute("client", client);
                request.getRequestDispatcher("/formModifierClient.jsp").forward(request, response);
            } else {
                request.setAttribute("messageErreur", "❌ Client non trouvé avec l'ID : " + id);
                listerClients(request, response);
            }
        } catch (Exception e) {
            request.setAttribute("messageErreur", "❌ Erreur : " + e.getMessage());
            listerClients(request, response);
        }
    }
    
    private void supprimerClient(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        try {
            Client client = metierClient.trouverClientParId(id);
            String nomClient = client != null ? client.getNom() : "Inconnu";
            
            metierClient.supprimerClient(id);
            
            request.setAttribute("messageSucces", "✅ Client '" + nomClient + "' (ID: " + id + ") supprimé avec succès !");
        } catch (Exception e) {
            request.setAttribute("messageErreur", "❌ Erreur lors de la suppression du client : " + e.getMessage());
            e.printStackTrace();
        }
        
        listerClients(request, response);
    }
    
    // ========== AUTRES MÉTHODES POST ==========
    
    private void ajouterTerrain(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String type = request.getParameter("type");
        Integer capacite = Integer.parseInt(request.getParameter("capacite"));
        String localisation = request.getParameter("localisation");
        
        Terrain terrain = new Terrain();
        terrain.setNom(nom);
        terrain.setType(type);
        terrain.setCapacite(capacite);
        terrain.setLocalisation(localisation);
        
        metierTerrain.ajouterTerrain(terrain);
        response.sendRedirect("controller?action=terrains");
    }
    
    private void ajouterClient(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        String dateInscriptionStr = request.getParameter("dateInscription");
        
        Client client = new Client();
        client.setNom(nom);
        client.setEmail(email);
        client.setTelephone(telephone);
        
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateInscription = sdf.parse(dateInscriptionStr);
            client.setDateInscription(dateInscription);
        } catch (ParseException e) {
            throw new ServletException("Format de date invalide. Utilisez AAAA-MM-JJ", e);
        }
        
        metierClient.ajouterClient(client);
        response.sendRedirect("controller?action=clients");
    }
    
    private void modifierTerrain(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String nom = request.getParameter("nom");
        String type = request.getParameter("type");
        Integer capacite = Integer.parseInt(request.getParameter("capacite"));
        String localisation = request.getParameter("localisation");
        
        Terrain terrain = metierTerrain.trouverTerrainParId(id);
        terrain.setNom(nom);
        terrain.setType(type);
        terrain.setCapacite(capacite);
        terrain.setLocalisation(localisation);
        
        metierTerrain.modifierTerrain(terrain);
        response.sendRedirect("controller?action=terrains");
    }
    
    private void modifierModePaiement(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Integer reservationId = Integer.parseInt(request.getParameter("reservationId"));
        String nouveauMode = request.getParameter("nouveauMode");
        
        // Correction UTF-8
        nouveauMode = corrigerEncodageUTF8(nouveauMode);
        
        metierPaiement.modifierModePaiementReservation(reservationId, nouveauMode);
        response.sendRedirect("controller?action=paiements");
    }
    
    private void executerModifierPaiement(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Integer reservationId = Integer.parseInt(request.getParameter("reservationId"));
        String nouveauMode = request.getParameter("nouveauMode");
        
        try {
            // Correction UTF-8
            nouveauMode = corrigerEncodageUTF8(nouveauMode);
            
            metierPaiement.modifierModePaiementReservation(reservationId, nouveauMode);
            request.setAttribute("messageSucces", "✅ Le mode de paiement de la réservation #" + reservationId + 
                                                  " a été modifié en '" + nouveauMode + "' avec succès !");
        } catch (Exception e) {
            request.setAttribute("messageErreur", "❌ Erreur : " + e.getMessage());
        }
        
        accueil(request, response);
    }
    
    private void modifierClient(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        Long id = Long.parseLong(request.getParameter("id"));
        String nom = request.getParameter("nom");
        String email = request.getParameter("email");
        String telephone = request.getParameter("telephone");
        String dateInscriptionStr = request.getParameter("dateInscription");
        
        try {
            Client client = metierClient.trouverClientParId(id);
            if (client == null) {
                throw new ServletException("Client non trouvé avec l'ID : " + id);
            }
            
            String ancienNom = client.getNom();
            client.setNom(nom);
            client.setEmail(email);
            client.setTelephone(telephone);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date dateInscription = sdf.parse(dateInscriptionStr);
            client.setDateInscription(dateInscription);
            
            metierClient.modifierClient(client);
            
            request.setAttribute("messageSucces", "✅ Client '" + ancienNom + "' (ID: " + id + ") modifié avec succès !\n" +
                                                 "Nouvelles informations : " + nom + " - " + email);
            
        } catch (ParseException e) {
            request.setAttribute("messageErreur", "❌ Format de date invalide. Utilisez AAAA-MM-JJ");
        } catch (Exception e) {
            request.setAttribute("messageErreur", "❌ Erreur lors de la modification : " + e.getMessage());
        }
        
        listerClients(request, response);
    }
    
    // Méthode utilitaire pour débogage (optionnelle)
    private String bytesToString(byte[] bytes) {
        if (bytes == null) return "null";
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }
}