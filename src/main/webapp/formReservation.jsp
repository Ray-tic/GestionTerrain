<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Créer une Réservation</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }

body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    padding: 20px;
    background: linear-gradient(135deg, #1e3a8a 0%, #1e40af 50%, #2563eb 100%);
    min-height: 100vh;
    position: relative;
}

body::before {
    content: '';
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: 
        radial-gradient(circle at 20% 50%, rgba(30, 64, 175, 0.2), transparent 50%),
        radial-gradient(circle at 80% 80%, rgba(30, 58, 138, 0.15), transparent 50%);
    z-index: 0;
    pointer-events: none;
}

.container {
    max-width: 900px;
    margin: 0 auto;
    background: rgba(255, 255, 255, 0.98);
    backdrop-filter: blur(10px);
    padding: 40px;
    border-radius: 20px;
    box-shadow: 0 20px 60px rgba(0, 0, 0, 0.4);
    position: relative;
    z-index: 1;
}

h1 {
    text-align: center;
    background: linear-gradient(135deg, #1e40af 0%, #1e3a8a 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    padding-bottom: 15px;
    margin-bottom: 30px;
    font-size: 2rem;
    font-weight: 700;
    position: relative;
}

h1::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    width: 80px;
    height: 3px;
    background: linear-gradient(90deg, #1e40af, #1e3a8a);
}

.btn {
    padding: 14px 28px;
    margin: 5px;
    background: linear-gradient(135deg, #1e40af 0%, #1e3a8a 100%);
    color: white;
    border: none;
    border-radius: 12px;
    cursor: pointer;
    font-weight: 600;
    box-shadow: 0 4px 15px rgba(30, 64, 175, 0.3);
    transition: all 0.3s ease;
    text-decoration: none;
    display: inline-block;
}

.btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(30, 64, 175, 0.4);
    background: linear-gradient(135deg, #1e3a8a 0%, #1e1b4b 100%);
}

.btn-warning {
    background: linear-gradient(135deg, #1e40af 0%, #1e3a8a 100%);
    box-shadow: 0 4px 15px rgba(30, 64, 175, 0.3);
    padding: 8px 16px;
    font-size: 13px;
}

.btn-danger {
    background: linear-gradient(135deg, #dc2626 0%, #991b1b 100%);
    box-shadow: 0 4px 15px rgba(220, 38, 38, 0.3);
    padding: 8px 16px;
    font-size: 13px;
}

.btn-secondary {
    background: linear-gradient(135deg, #6b7280 0%, #4b5563 100%);
}

.form-group { margin-bottom: 25px; }

label {
    display: block;
    margin-bottom: 8px;
    font-weight: 600;
    color: #1a1a2e;
}

input[type="date"],
input[type="time"],
select {
    width: 100%;
    padding: 14px;
    border: 2px solid #dbeafe;
    border-radius: 12px;
    font-size: 15px;
    transition: all 0.3s ease;
}

input:focus, select:focus {
    outline: none;
    border-color: #1e40af;
    box-shadow: 0 0 0 3px rgba(30, 64, 175, 0.1);
}

.reservation-info {
    background: linear-gradient(135deg, #dbeafe 0%, #bfdbfe 100%);
    border: 1px solid #93c5fd;
    border-radius: 12px;
    padding: 20px;
    margin-bottom: 25px;
    border-left: 4px solid #1e40af;
}

.reservation-info p {
    margin: 5px 0;
    color: #1e3a8a;
    font-weight: 500;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin: 25px 0;
    border-radius: 12px;
    overflow: hidden;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

th {
    background: linear-gradient(135deg, #1e40af 0%, #1e3a8a 100%);
    color: white;
    padding: 16px;
    text-align: left;
    font-weight: 600;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    font-size: 0.9rem;
}

td {
    padding: 14px;
    border-bottom: 1px solid #f3f4f6;
    background: white;
}

tr:hover td {
    background: #eff6ff;
}

.message-succes {
    background: linear-gradient(135deg, #d1fae5 0%, #a7f3d0 100%);
    color: #065f46;
    padding: 18px;
    border-radius: 12px;
    margin-bottom: 25px;
    border-left: 4px solid #10b981;
    font-weight: 500;
}

.message-erreur {
    background: linear-gradient(135deg, #fee2e2 0%, #fecaca 100%);
    color: #991b1b;
    padding: 18px;
    border-radius: 12px;
    margin-bottom: 25px;
    border-left: 4px solid #ef4444;
    font-weight: 500;
}

.text-center {
    text-align: center;
}
/* ========== ANIMATIONS ========== */
    @keyframes fadeInScale {
        from { opacity: 0; transform: scale(0.95) translateY(20px); }
        to { opacity: 1; transform: scale(1) translateY(0); }
    }
    
    @keyframes slideInFromLeft {
        from { opacity: 0; transform: translateX(-30px); }
        to { opacity: 1; transform: translateX(0); }
    }
    
    @keyframes slideInFromTop {
        from { opacity: 0; transform: translateY(-30px); }
        to { opacity: 1; transform: translateY(0); }
    }
    
    @keyframes slideInFromBottom {
        from { opacity: 0; transform: translateY(30px); }
        to { opacity: 1; transform: translateY(0); }
    }
    
    @keyframes fadeInRow {
        from { opacity: 0; transform: translateX(-10px); }
        to { opacity: 1; transform: translateX(0); }
    }
    
    @keyframes pulse {
        0%, 100% { transform: scale(1); }
        50% { transform: scale(1.02); }
    }
    
    @keyframes bounceIn {
        0% { opacity: 0; transform: scale(0.3); }
        50% { transform: scale(1.05); }
        70% { transform: scale(0.9); }
        100% { opacity: 1; transform: scale(1); }
    }
    
    .container {
        animation: fadeInScale 0.6s cubic-bezier(0.25, 0.46, 0.45, 0.94) forwards;
    }
    
    h1 {
        animation: slideInFromTop 0.8s cubic-bezier(0.25, 0.46, 0.45, 0.94) 0.1s backwards;
    }
    
    .message-succes, .message-erreur, .alert-message, .success-message {
        animation: pulse 0.6s ease-in-out, slideInFromTop 0.5s ease-out;
    }
    
    .actions, .btn-container {
        animation: slideInFromBottom 0.7s cubic-bezier(0.25, 0.46, 0.45, 0.94) 0.2s backwards;
    }
    
    .card, .client-info, .terrain-info, .reservation-info, .form-paiement, .filters, .results-count, .footer-info {
        animation: fadeInScale 0.6s cubic-bezier(0.25, 0.46, 0.45, 0.94) 0.3s backwards;
    }
    
    .stat-box {
        animation: bounceIn 0.6s cubic-bezier(0.25, 0.46, 0.45, 0.94) backwards;
    }
    .stat-box:nth-child(1) { animation-delay: 0.1s; }
    .stat-box:nth-child(2) { animation-delay: 0.2s; }
    .stat-box:nth-child(3) { animation-delay: 0.3s; }
    .stat-box:nth-child(4) { animation-delay: 0.4s; }
    
    .form-group {
        animation: slideInFromLeft 0.5s cubic-bezier(0.25, 0.46, 0.45, 0.94) backwards;
    }
    .form-group:nth-child(1) { animation-delay: 0.1s; }
    .form-group:nth-child(2) { animation-delay: 0.15s; }
    .form-group:nth-child(3) { animation-delay: 0.2s; }
    .form-group:nth-child(4) { animation-delay: 0.25s; }
    .form-group:nth-child(5) { animation-delay: 0.3s; }
    
    table {
        animation: fadeInScale 0.6s cubic-bezier(0.25, 0.46, 0.45, 0.94) 0.4s backwards;
    }
    
    tbody tr {
        animation: fadeInRow 0.4s cubic-bezier(0.25, 0.46, 0.45, 0.94) backwards;
    }
    tbody tr:nth-child(1) { animation-delay: 0.05s; }
    tbody tr:nth-child(2) { animation-delay: 0.1s; }
    tbody tr:nth-child(3) { animation-delay: 0.15s; }
    tbody tr:nth-child(4) { animation-delay: 0.2s; }
    tbody tr:nth-child(5) { animation-delay: 0.25s; }
    tbody tr:nth-child(n+6) { animation-delay: 0.25s; }
    
    .dashboard .card {
        animation: fadeInScale 0.6s cubic-bezier(0.25, 0.46, 0.45, 0.94) backwards;
    }
    .dashboard .card:nth-child(1) { animation-delay: 0.2s; }
    .dashboard .card:nth-child(2) { animation-delay: 0.3s; }
    .dashboard .card:nth-child(3) { animation-delay: 0.4s; }
    
    .menu li {
        animation: slideInFromLeft 0.4s cubic-bezier(0.25, 0.46, 0.45, 0.94) backwards;
    }
    .menu li:nth-child(1) { animation-delay: 0.1s; }
    .menu li:nth-child(2) { animation-delay: 0.15s; }
    .menu li:nth-child(3) { animation-delay: 0.2s; }
    .menu li:nth-child(4) { animation-delay: 0.25s; }
    .menu li:nth-child(5) { animation-delay: 0.3s; }
    
    @media (prefers-reduced-motion: reduce) {
        *, *::before, *::after {
            animation-duration: 0.01ms !important;
            animation-iteration-count: 1 !important;
            transition-duration: 0.01ms !important;
        }
    }
    </style>
</head>
<body>
    <div class="container">
        <h1>📅 Créer une Réservation</h1>
        
        <!-- Affichage des messages d'erreur -->
        <c:if test="${not empty messageErreur}">
            <div class="alert alert-danger">
                ${messageErreur}
            </div>
        </c:if>
        
        <!-- Affichage des messages de succès -->
        <c:if test="${not empty messageSucces}">
            <div class="alert alert-success">
                ${messageSucces}
            </div>
        </c:if>
        
        <div class="info-box">
            <strong>ℹ️ Règles de réservation :</strong>
            <ul>
                <li>✅ <strong>Plusieurs clients</strong> peuvent réserver le même terrain au même moment</li>
                <li>❌ <strong>Un même client</strong> ne peut pas avoir deux réservations qui se chevauchent pour le même terrain le même jour</li>
            </ul>
        </div>
        
        <form action="controller" method="post" id="reservationForm">
            <input type="hidden" name="action" value="creerReservation">
            
            <div class="form-group">
                <label for="clientId">👤 Client : *</label>
                <select id="clientId" name="clientId" required>
                    <option value="">-- Sélectionnez un client --</option>
                    <c:forEach var="client" items="${clients}">
                        <option value="${client.idClient}" 
                            <c:if test="${clientIdSaisi == client.idClient}">selected</c:if>>
                            ${client.nom} - ${client.email}
                        </option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-group">
                <label for="terrainId">🏟️ Terrain : *</label>
                <select id="terrainId" name="terrainId" required>
                    <option value="">-- Sélectionnez un terrain --</option>
                    <c:forEach var="terrain" items="${terrains}">
                        <option value="${terrain.idTerrain}"
                            <c:if test="${terrainIdSaisi == terrain.idTerrain}">selected</c:if>>
                            ${terrain.nom} (${terrain.type}) - Capacité: ${terrain.capacite} personnes
                        </option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-group">
                <label for="dateReservation">📅 Date de réservation : *</label>
                <input type="date" id="dateReservation" name="dateReservation" 
                       value="${dateReservationSaisie}" required>
            </div>
            
            <div class="form-group">
                <label for="heureDebut">🕐 Heure de début : *</label>
                <input type="time" id="heureDebut" name="heureDebut" 
                       value="${heureDebutSaisie}" required>
            </div>
            
            <div class="form-group">
                <label for="heureFin">🕐 Heure de fin : *</label>
                <input type="time" id="heureFin" name="heureFin" 
                       value="${heureFinSaisie}" required>
                <small>⚠️ L'heure de fin doit être après l'heure de début</small>
            </div>
            
            <div class="btn-container">
                <button type="submit" class="btn">✅ Créer la réservation</button>
                <a href="controller?action=reservations" class="btn btn-secondary">❌ Annuler</a>
            </div>
        </form>
    </div>
    
    <script>
        // Validation côté client
        document.getElementById('reservationForm').addEventListener('submit', function(event) {
            const clientId = document.getElementById('clientId').value;
            const terrainId = document.getElementById('terrainId').value;
            const dateReservation = document.getElementById('dateReservation').value;
            const heureDebut = document.getElementById('heureDebut').value;
            const heureFin = document.getElementById('heureFin').value;
            
            // Vérifier que tous les champs sont remplis
            if (!clientId || !terrainId || !dateReservation || !heureDebut || !heureFin) {
                alert("❌ Erreur : Tous les champs sont obligatoires!");
                event.preventDefault();
                return;
            }
            
            // Vérifier que l'heure de début est avant l'heure de fin
            if (heureDebut >= heureFin) {
                alert("❌ Erreur : L'heure de début doit être avant l'heure de fin!\n\n" +
                      "Heure de début : " + heureDebut + "\n" +
                      "Heure de fin : " + heureFin);
                event.preventDefault();
                return;
            }
            
            // Confirmation avant envoi
            const clientText = document.getElementById('clientId').options[document.getElementById('clientId').selectedIndex].text;
            const terrainText = document.getElementById('terrainId').options[document.getElementById('terrainId').selectedIndex].text;
            
            const confirmation = confirm(
                "Voulez-vous créer cette réservation ?\n\n" +
                "📋 Résumé :\n" +
                "━━━━━━━━━━━━━━━━━━━━\n" +
                "👤 Client : " + clientText + "\n" +
                "🏟️ Terrain : " + terrainText + "\n" +
                "📅 Date : " + dateReservation + "\n" +
                "🕐 Horaire : " + heureDebut + " - " + heureFin + "\n" +
                "━━━━━━━━━━━━━━━━━━━━"
            );
            
            if (!confirmation) {
                event.preventDefault();
            }
        });
        
        // Initialisation de la page
        window.onload = function() {
            // Scroll vers le message si présent
            const messageErreur = document.querySelector('.alert-danger');
            const messageSucces = document.querySelector('.alert-success');
            if (messageErreur || messageSucces) {
                window.scrollTo({
                    top: 0,
                    behavior: 'smooth'
                });
            }
        };
    </script>
</body>
</html>