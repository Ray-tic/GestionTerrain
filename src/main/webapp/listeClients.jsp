<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${titre != null ? titre : 'Liste des Clients'}</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }

body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    padding: 40px 20px;
    background: linear-gradient(135deg, #b45309 0%, #f59e0b 50%, #fbbf24 100%);
    min-height: 100vh;
    position: relative;
    display: flex;
    justify-content: center;
    align-items: flex-start;
}

body::before {
    content: '';
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: 
        radial-gradient(circle at 20% 50%, rgba(245, 158, 11, 0.2), transparent 50%),
        radial-gradient(circle at 80% 80%, rgba(217, 119, 6, 0.15), transparent 50%);
    z-index: 0;
    pointer-events: none;
}

.container {
    max-width: 1200px;
    width: 95%;
    margin: 0 auto;
    background: rgba(255, 255, 255, 0.98);
    backdrop-filter: blur(10px);
    padding: 40px;
    border-radius: 20px;
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15)
    position: relative;
    z-index: 1;
    min-height: 80vh;
    display: flex;
    flex-direction: column;
}

h1 {
    text-align: center;
    background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    padding-bottom: 15px;
    margin-bottom: 30px;
    font-size: 2.2rem;
    font-weight: 700;
    position: relative;
    flex-shrink: 0;
}

h1::after {
    content: '';
    position: absolute;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    width: 100px;
    height: 4px;
    background: linear-gradient(90deg, #f59e0b, #d97706);
}

.actions {
    margin-bottom: 30px;
    text-align: center;
    flex-shrink: 0;
}

.btn {
    padding: 14px 28px;
    margin: 5px;
    background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
    color: white;
    border: none;
    border-radius: 12px;
    cursor: pointer;
    font-weight: 600;
    box-shadow: 0 4px 15px rgba(245, 158, 11, 0.3);
    transition: all 0.3s ease;
    text-decoration: none;
    display: inline-block;
}

.btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(245, 158, 11, 0.4);
    background: linear-gradient(135deg, #d97706 0%, #b45309 100%);
}

.btn-warning {
    background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
    box-shadow: 0 4px 15px rgba(245, 158, 11, 0.3);
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

.message-succes {
    background: linear-gradient(135deg, #d1fae5 0%, #a7f3d0 100%);
    color: #065f46;
    padding: 18px;
    border-radius: 12px;
    margin-bottom: 25px;
    border-left: 4px solid #10b981;
    font-weight: 500;
    flex-shrink: 0;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.08);
}

.message-erreur {
    background: linear-gradient(135deg, #fee2e2 0%, #fecaca 100%);
    color: #991b1b;
    padding: 18px;
    border-radius: 12px;
    margin-bottom: 25px;
    border-left: 4px solid #ef4444;
    font-weight: 500;
    flex-shrink: 0;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.08);
}

.table-container {
    flex: 1;
    overflow: auto;
    margin-bottom: 20px;
    border-radius: 12px;
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.15);
    background: white;
    padding: 1px;
    border: 1px solid #fef3c7;
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
    background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
    color: white;
    padding: 16px;
    text-align: left;
    font-weight: 600;
    text-transform: uppercase;
    letter-spacing: 0.5px;
    font-size: 0.9rem;
    position: sticky;
    top: 0;
    z-index: 10;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

td {
    padding: 14px;
    border-bottom: 1px solid #f3f4f6;
    background: white;
}

tr:hover td {
    background: #fffbeb;
    box-shadow: inset 0 0 10px rgba(245, 158, 11, 0.1);
}

tr:last-child td {
    border-bottom: none;
}

.btn-group {
    display: flex;
    gap: 8px;
    flex-wrap: wrap;
}

.footer-info {
    margin-top: 20px;
    color: #666;
    font-size: 14px;
    padding: 15px;
    background: linear-gradient(135deg, #fef3c7 0%, #fde68a 100%);
    border-radius: 12px;
    border-left: 4px solid #f59e0b;
    flex-shrink: 0;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.08);
}

.text-center {
    text-align: center;
    padding: 30px;
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
        <h1>${titre != null ? titre : '👥 Liste des Clients'}</h1>
        
        <!-- Messages de confirmation/erreur -->
        <c:if test="${not empty messageSucces}">
            <div class="message-succes">
                ✅ ${messageSucces}
            </div>
        </c:if>
        
        <c:if test="${not empty messageErreur}">
            <div class="message-erreur">
                ❌ ${messageErreur}
            </div>
        </c:if>
        
        <div class="actions">
            <a href="controller?action=formAjouterClient" class="btn btn-primary">
                ➕ Ajouter un Client
            </a>
            <a href="controller?action=clientsFootball" class="btn btn-info">
                ⚽ Clients Football
            </a>
            <a href="controller" class="btn">🏠 Accueil</a>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Email</th>
                    <th>Téléphone</th>
                    <th>Date Inscription</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty clients}">
                        <c:forEach var="client" items="${clients}">
                            <tr>
                                <td>${client.idClient}</td>
                                <td><strong>${client.nom}</strong></td>
                                <td>${client.email}</td>
                                <td>${client.telephone}</td>
                                <td class="date-cell">${client.dateInscription}</td>
                                <td>
                                    <div class="btn-group">
                                        <!-- Bouton Modifier -->
                                        <a href="controller?action=formModifierClient&id=${client.idClient}" 
                                           class="btn btn-warning btn-sm" title="Modifier">
                                           ✏️ Modifier
                                        </a>
                                        
                                        <!-- Bouton Supprimer -->
                                        <a href="controller?action=supprimerClient&id=${client.idClient}" 
                                           class="btn btn-danger btn-sm"
                                           onclick="return confirmSuppression(${client.idClient}, '${client.nom}')"
                                           title="Supprimer">
                                           🗑️ Supprimer
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="6" class="text-center">
                                Aucun client trouvé.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
        
        <div style="margin-top: 20px; color: #666; font-size: 14px;">
            <p><strong>Total :</strong> ${clients != null ? clients.size() : 0} client(s)</p>
        </div>
    </div>
    
    <script>
        function confirmSuppression(id, nom) {
            return confirm(`Êtes-vous sûr de vouloir supprimer le client "${nom}" (ID: ${id}) ?\n\nCette action est irréversible.`);
        }
    </script>
</body>
</html>