<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Ajouter un Paiement</title>
    <style>
         * { margin: 0; padding: 0; box-sizing: border-box; }

body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    padding: 20px;
    background: linear-gradient(135deg, #7f1d1d 0%, #991b1b 50%, #dc2626 100%);
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
        radial-gradient(circle at 20% 50%, rgba(220, 38, 38, 0.2), transparent 50%),
        radial-gradient(circle at 80% 80%, rgba(153, 27, 27, 0.15), transparent 50%);
    z-index: 0;
    pointer-events: none;
}

.container {
    max-width: 1200px;
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
    background: linear-gradient(135deg, #dc2626 0%, #991b1b 100%);
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
    background: linear-gradient(90deg, #dc2626, #991b1b);
}

.btn {
    padding: 14px 28px;
    margin: 5px;
    background: linear-gradient(135deg, #dc2626 0%, #991b1b 100%);
    color: white;
    border: none;
    border-radius: 12px;
    cursor: pointer;
    font-weight: 600;
    box-shadow: 0 4px 15px rgba(220, 38, 38, 0.3);
    transition: all 0.3s ease;
    text-decoration: none;
    display: inline-block;
}

.btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(220, 38, 38, 0.4);
    background: linear-gradient(135deg, #991b1b 0%, #7f1d1d 100%);
}

.btn-warning {
    background: linear-gradient(135deg, #dc2626 0%, #991b1b 100%);
    box-shadow: 0 4px 15px rgba(220, 38, 38, 0.3);
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

input[type="number"],
input[type="date"],
select {
    width: 100%;
    padding: 14px;
    border: 2px solid #fee2e2;
    border-radius: 12px;
    font-size: 15px;
    transition: all 0.3s ease;
}

input:focus, select:focus {
    outline: none;
    border-color: #dc2626;
    box-shadow: 0 0 0 3px rgba(220, 38, 38, 0.1);
}

.client-info {
    background: linear-gradient(135deg, #fee2e2 0%, #fecaca 100%);
    border: 1px solid #fca5a5;
    border-radius: 12px;
    padding: 20px;
    margin-bottom: 25px;
    border-left: 4px solid #dc2626;
}

.client-info p {
    margin: 5px 0;
    color: #991b1b;
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
    background: linear-gradient(135deg, #dc2626 0%, #991b1b 100%);
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
    background: #fef2f2;
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
    border-left: 4px solid #dc2626;
    font-weight: 500;
}

.warning-badge {
    background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
    color: #92400e;
    padding: 4px 10px;
    border-radius: 6px;
    font-size: 12px;
    font-weight: 600;
    border: 1px solid #fbbf24;
    display: inline-block;
    margin-left: 5px;
}

.error-badge {
    background: linear-gradient(135deg, #fee2e2 0%, #fecaca 100%);
    color: #991b1b;
    padding: 4px 10px;
    border-radius: 6px;
    font-size: 12px;
    font-weight: 600;
    border: 1px solid #dc2626;
    display: inline-block;
}

.stat-amount {
    font-size: 20px;
    font-weight: 700;
    color: #dc2626;
}

.rank-1 { background: linear-gradient(135deg, #ffd700 0%, #ffed4e 100%) !important; }
.rank-2 { background: linear-gradient(135deg, #c0c0c0 0%, #e5e5e5 100%) !important; }
.rank-3 { background: linear-gradient(135deg, #cd7f32 0%, #e89f65 100%) !important; }

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
    <!-- AJOUTEZ ce code au début du formulaire (après <div class="container">) -->

<!-- Affichage des messages d'erreur/succès -->
<c:if test="${not empty messageErreur}">
    <div class="alert-message">
        ${messageErreur}
    </div>
</c:if>

<c:if test="${not empty messageSucces}">
    <div class="success-message">
        ${messageSucces}
    </div>
</c:if>

<!-- Style pour les messages -->
<style>
.alert-message {
    background: #f8d7da;
    color: #721c24;
    padding: 15px;
    border-radius: 5px;
    border-left: 4px solid #dc3545;
    margin-bottom: 20px;
}
.success-message {
    background: #d4edda;
    color: #155724;
    padding: 15px;
    border-radius: 5px;
    border-left: 4px solid #28a745;
    margin-bottom: 20px;
}
.alert-message ul, .success-message ul {
    margin: 10px 0 10px 20px;
}
.alert-message li, .success-message li {
    margin-bottom: 5px;
}
</style>

<!-- SCRIPT pour pré-remplir le formulaire en cas d'erreur -->
<script>
document.addEventListener('DOMContentLoaded', function() {
    // Pré-remplir les champs en cas d'erreur de validation
    const urlParams = new URLSearchParams(window.location.search);
    const reservationId = '<%= request.getParameter("reservationId") != null ? request.getParameter("reservationId") : "" %>';
    
    if (reservationId) {
        document.getElementById('reservationId').value = reservationId;
    }
    
    // Ajouter un avertissement visuel si tentative de doublon
    const modePaiementSelect = document.getElementById('modePaiement');
    if (modePaiementSelect) {
        modePaiementSelect.addEventListener('change', function() {
            const selectedOption = this.options[this.selectedIndex];
            if (selectedOption.text.includes('UTF-8')) {
                this.style.borderColor = '#ffc107';
                this.style.backgroundColor = '#fff3cd';
            } else {
                this.style.borderColor = '';
                this.style.backgroundColor = '';
            }
        });
    }
});
</script>
        <h1>💰 Ajouter un Paiement</h1>
        
        
        <!-- ATTENTION: accept-charset="UTF-8" est ESSENTIEL -->
        <form action="controller?action=ajouterPaiement" method="post" accept-charset="UTF-8">
            
            <div class="form-group">
                <label for="reservationId">Réservation:</label>
                <select id="reservationId" name="reservationId" required>
                    <option value="">-- Sélectionner une réservation --</option>
                    <c:forEach var="reservation" items="${reservations}">
                        <option value="${reservation.idReservation}">
                            Réservation #${reservation.idReservation} - 
                            ${reservation.client.nom} - 
                            ${reservation.terrain.nom}
                        </option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-group">
                <label for="montant">Montant (DT):</label>
                <input type="number" id="montant" name="montant" step="0.01" min="0" required>
            </div>
            
            <div class="form-group">
                <label for="modePaiement">Mode de Paiement:</label>
                <select id="modePaiement" name="modePaiement" required>
                    <option value="">-- Sélectionner un mode --</option>
                    <option value="espèces">Espèces</option>
                    <option value="virement">Virement</option>
                    <option value="carte">Carte</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="datePaiement">Date de Paiement:</label>
                <input type="date" id="datePaiement" name="datePaiement" required>
            </div>
            
            <button type="submit" class="btn btn-primary">💰 Ajouter le Paiement</button>
            <a href="controller?action=paiements" class="btn btn-secondary">🔙 Annuler</a>
        </form>
        
        
    </div>
    
    <script>
    document.addEventListener('DOMContentLoaded', function() {
        // Vérifier que le formulaire envoie bien en UTF-8
        const form = document.querySelector('form');
        form.addEventListener('submit', function(e) {
            console.log('Formulaire soumis avec encodage UTF-8');
            
            // Vérifier le mode de paiement sélectionné
            const modeSelect = document.getElementById('modePaiement');
            const selectedMode = modeSelect.options[modeSelect.selectedIndex].text;
            console.log('Mode sélectionné:', selectedMode);
            console.log('Valeur UTF-8:', encodeURIComponent(selectedMode));
        });
    });
    </script>
</body>
</html>