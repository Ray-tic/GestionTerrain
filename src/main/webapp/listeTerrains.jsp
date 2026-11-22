<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${titre != null ? titre : 'Liste des Terrains'}</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }

body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    padding: 40px 20px;
    background: linear-gradient(135deg, #0a4d2e 0%, #15803d 50%, #166534 100%);
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
        radial-gradient(circle at 20% 50%, rgba(22, 163, 74, 0.2), transparent 50%),
        radial-gradient(circle at 80% 80%, rgba(21, 128, 61, 0.15), transparent 50%);
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
    box-shadow: 0 20px 60px rgba(0, 0, 0, 0.4);
    position: relative;
    z-index: 1;
    min-height: 80vh;
    display: flex;
    flex-direction: column;
}

h1 {
    text-align: center;
    background: linear-gradient(135deg, #16a34a 0%, #15803d 100%);
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
    background: linear-gradient(90deg, #16a34a, #15803d);
}

.actions {
    margin-bottom: 20px;
    text-align: center;
    flex-shrink: 0;
}

.btn {
    padding: 14px 28px;
    margin: 5px;
    background: linear-gradient(135deg, #16a34a 0%, #15803d 100%);
    color: white;
    border: none;
    border-radius: 12px;
    cursor: pointer;
    font-weight: 600;
    box-shadow: 0 4px 15px rgba(22, 163, 74, 0.3);
    transition: all 0.3s ease;
    text-decoration: none;
    display: inline-block;
}

.btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(22, 163, 74, 0.4);
    background: linear-gradient(135deg, #15803d 0%, #14532d 100%);
}

.btn-warning {
    background: linear-gradient(135deg, #16a34a 0%, #15803d 100%);
    box-shadow: 0 4px 15px rgba(22, 163, 74, 0.3);
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
}

.filters {
    background: linear-gradient(145deg, #f0fdf4 0%, #ffffff 100%);
    padding: 20px;
    border-radius: 12px;
    margin: 10px 0 20px 0;
    border: 1px solid #dcfce7;
    flex-shrink: 0;
}

.results-count {
    background: linear-gradient(135deg, #dcfce7 0%, #bbf7d0 100%);
    border-left: 4px solid #16a34a;
    border-radius: 10px;
    padding: 15px;
    color: #14532d;
    font-weight: 600;
    margin-bottom: 15px;
    flex-shrink: 0;
}

.table-container {
    flex: 1;
    overflow: auto;
    margin-bottom: 20px;
    border-radius: 12px;
    box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
}

table {
    width: 100%;
    border-collapse: collapse;
    min-width: 800px;
}

th {
    background: linear-gradient(135deg, #16a34a 0%, #15803d 100%);
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
}

td {
    padding: 14px;
    border-bottom: 1px solid #f3f4f6;
    background: white;
}

tr:hover td {
    background: #f0fdf4;
}

.badge {
    padding: 6px 12px;
    border-radius: 8px;
    font-size: 13px;
    font-weight: 600;
    background: linear-gradient(135deg, #16a34a 0%, #15803d 100%);
    color: white;
    display: inline-block;
}

.btn-group {
    display: flex;
    gap: 8px;
    flex-wrap: wrap;
}

.footer-info {
    margin-top: 10px;
    color: #666;
    font-size: 14px;
    padding: 15px;
    background: linear-gradient(135deg, #dcfce7 0%, #bbf7d0 100%);
    border-radius: 12px;
    border-left: 4px solid #16a34a;
    flex-shrink: 0;
}

.text-center {
    text-align: center;
    padding: 30px;
}

.form-inline {
    display: flex;
    align-items: center;
    gap: 10px;
    flex-wrap: wrap;
}

.filter-label {
    font-weight: 600;
    color: #14532d;
    margin-right: 10px;
}

.form-control {
    padding: 10px;
    border: 2px solid #dcfce7;
    border-radius: 8px;
    font-size: 14px;
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
        <h1>${titre != null ? titre : '🏟️ Liste des Terrains'}</h1>
        
        <!-- Messages -->
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
            <a href="controller?action=formAjouterTerrain" class="btn btn-primary">
                ➕ Ajouter un Terrain
            </a>
            <a href="controller?action=terrainsNonReserves" class="btn btn-primary">
                🏟️ Terrains non réservés
            </a>
            <a href="controller" class="btn">🏠 Accueil</a>
        </div>
        
        <!-- SECTION DE FILTRAGE -->
        <div class="filters">
            <form method="get" action="controller" class="form-inline">
                <input type="hidden" name="action" value="terrains">
                <span class="filter-label">🔍 Filtrer par type :</span>
                <select name="type" class="form-control">
                    <option value="">Tous les types</option>
                    <option value="Football" ${typeFiltreActif == 'Football' ? 'selected' : ''}>Football</option>
                    <option value="Tennis" ${typeFiltreActif == 'Tennis' ? 'selected' : ''}>Tennis</option>
                    <option value="Basketball" ${typeFiltreActif == 'Basketball' ? 'selected' : ''}>Basketball</option>
                    <option value="Natation" ${typeFiltreActif == 'Natation' ? 'selected' : ''}>Natation</option>
                    <option value="Volleyball" ${typeFiltreActif == 'Volleyball' ? 'selected' : ''}>Volleyball</option>
                    <option value="Handball" ${typeFiltreActif == 'Handball' ? 'selected' : ''}>Handball</option>
                </select>
                <button type="submit" class="btn btn-primary" style="padding: 10px 20px;">🔍 Filtrer</button>
                <a href="controller?action=terrains" class="btn" style="padding: 10px 20px;">🔄 Réinitialiser</a>
            </form>
        </div>
        
        <!-- COMPTEUR DE RÉSULTATS -->
        <c:if test="${not empty terrains}">
            <div class="results-count">
                📊 <strong>${terrains.size()}</strong> terrain(s) trouvé(s)
                <c:if test="${not empty typeFiltreActif}">
                    pour le type <strong>${typeFiltreActif}</strong>
                </c:if>
            </div>
        </c:if>
        
        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nom</th>
                        <th>Type</th>
                        <th>Capacité</th>
                        <th>Localisation</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty terrains}">
                            <c:forEach var="terrain" items="${terrains}">
                                <tr>
                                    <td>${terrain.idTerrain}</td>
                                    <td>${terrain.nom}</td>
                                    <td><span class="badge">${terrain.type}</span></td>
                                    <td>${terrain.capacite} pers.</td>
                                    <td>${terrain.localisation}</td>
                                    <td>
                                        <div class="btn-group">
                                            <a href="controller?action=formModifierTerrain&id=${terrain.idTerrain}" 
                                               class="btn btn-warning btn-sm" title="Modifier">
                                               ✏️ Modifier
                                            </a>
                                            
                                            <a href="controller?action=supprimerTerrain&id=${terrain.idTerrain}" 
                                               class="btn btn-danger btn-sm"
                                               onclick="return confirm('Êtes-vous sûr de vouloir supprimer ce terrain ?')"
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
                                    <c:choose>
                                        <c:when test="${not empty typeFiltreActif}">
                                            ❌ Aucun terrain de type <strong>${typeFiltreActif}</strong> trouvé.
                                        </c:when>
                                        <c:otherwise>
                                            Aucun terrain trouvé.
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
        
        <div class="footer-info">
            <p><strong>Total :</strong> ${terrains != null ? terrains.size() : 0} terrain(s)</p>
            <c:if test="${not empty typeFiltreActif}">
                <p><strong>Filtre actif :</strong> ${typeFiltreActif}</p>
            </c:if>
        </div>
    </div>
</body>
</html>