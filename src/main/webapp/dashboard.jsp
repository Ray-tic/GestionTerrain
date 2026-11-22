<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gestion Terrain - Dashboard</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 20px;
            background: linear-gradient(135deg, #0a4d2e 0%, #15803d 50%, #166534 100%);
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
                radial-gradient(circle at 20% 50%, rgba(34, 197, 94, 0.2), transparent 50%),
                radial-gradient(circle at 80% 80%, rgba(22, 163, 74, 0.15), transparent 50%),
                radial-gradient(circle at 40% 20%, rgba(21, 128, 61, 0.18), transparent 50%);
            z-index: 0;
            pointer-events: none;
        }
        
        .container {
            max-width: 1400px;
            margin: 0 auto;
            background: rgba(255, 255, 255, 0.98);
            backdrop-filter: blur(10px);
            padding: 30px;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0, 0, 0, 0.4);
            position: relative;
            z-index: 1;
            border: 1px solid rgba(255, 255, 255, 0.2);
        }
        
        h1 {
            color: #1a1a2e;
            text-align: center;
            border-bottom: 3px solid transparent;
            background: linear-gradient(135deg, #16a34a 0%, #15803d 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            padding-bottom: 15px;
            margin-bottom: 30px;
            font-size: 2.2rem;
            font-weight: 700;
            position: relative;
            text-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
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
            border-radius: 2px;
        }
        
        .dashboard {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(320px, 1fr));
            gap: 25px;
            margin: 30px 0;
        }
        
        .card {
            background: linear-gradient(145deg, #ffffff 0%, #f0fdf4 100%);
            padding: 25px;
            border-radius: 15px;
            box-shadow: 0 8px 25px rgba(22, 163, 74, 0.15);
            border: 1px solid rgba(22, 163, 74, 0.1);
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
        }
        
        .card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 4px;
            height: 100%;
            background: linear-gradient(180deg, #16a34a, #15803d);
        }
        
        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 12px 35px rgba(22, 163, 74, 0.25);
        }
        
        .card h2 {
            color: #15803d;
            margin-top: 0;
            margin-bottom: 20px;
            padding-bottom: 12px;
            border-bottom: 2px solid #dcfce7;
            font-size: 1.3rem;
            font-weight: 600;
        }
        
        .menu {
            list-style: none;
            padding: 0;
        }
        
        .menu li {
            margin: 12px 0;
        }
        
        .menu a {
            display: block;
            padding: 14px 18px;
            background: linear-gradient(135deg, #f0fdf4 0%, #ffffff 100%);
            border-radius: 10px;
            text-decoration: none;
            color: #1a1a2e;
            transition: all 0.3s ease;
            border-left: 3px solid #16a34a;
            font-weight: 500;
            box-shadow: 0 2px 8px rgba(22, 163, 74, 0.1);
        }
        
        .menu a:hover {
            background: linear-gradient(135deg, #16a34a 0%, #15803d 100%);
            color: white;
            transform: translateX(5px);
            box-shadow: 0 4px 15px rgba(22, 163, 74, 0.3);
        }
        
        .btn {
            display: inline-block;
            padding: 12px 24px;
            margin: 5px;
            background: linear-gradient(135deg, #16a34a 0%, #15803d 100%);
            color: white;
            text-decoration: none;
            border-radius: 10px;
            border: none;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s ease;
            box-shadow: 0 4px 15px rgba(22, 163, 74, 0.3);
        }
        
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(22, 163, 74, 0.4);
            background: linear-gradient(135deg, #15803d 0%, #14532d 100%);
        }
        
        .quick-actions {
            display: flex;
            flex-direction: column;
            gap: 12px;
        }
        
        .statistics {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin: 30px 0;
        }
        
        .stat-box {
            background: linear-gradient(135deg, #16a34a 0%, #15803d 100%);
            padding: 25px;
            border-radius: 15px;
            text-align: center;
            color: white;
            box-shadow: 0 8px 25px rgba(22, 163, 74, 0.35);
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
        }
        
        .stat-box::before {
            content: '';
            position: absolute;
            top: -50%;
            right: -50%;
            width: 200%;
            height: 200%;
            background: radial-gradient(circle, rgba(255,255,255,0.15) 0%, transparent 70%);
            transition: all 0.5s ease;
        }
        
        .stat-box:hover {
            transform: translateY(-5px) scale(1.02);
            box-shadow: 0 12px 35px rgba(22, 163, 74, 0.45);
            background: linear-gradient(135deg, #15803d 0%, #14532d 100%);
        }
        
        .stat-box:hover::before {
            top: -25%;
            right: -25%;
        }
        
        .stat-box:nth-child(2) {
            background: linear-gradient(135deg, #f59e0b 0%, #d97706 100%);
            box-shadow: 0 8px 25px rgba(245, 158, 11, 0.35);
        }
        
        .stat-box:nth-child(2):hover {
            background: linear-gradient(135deg, #d97706 0%, #b45309 100%);
            box-shadow: 0 12px 35px rgba(245, 158, 11, 0.45);
        }
        
        .stat-box:nth-child(3) {
            background: linear-gradient(135deg, #1e40af 0%, #1e3a8a 100%);
            box-shadow: 0 8px 25px rgba(30, 64, 175, 0.35);
        }
        
        .stat-box:nth-child(3):hover {
            background: linear-gradient(135deg, #1e3a8a 0%, #1e3a8a 100%);
            box-shadow: 0 12px 35px rgba(30, 64, 175, 0.45);
        }
        
        .stat-box:nth-child(4) {
            background: linear-gradient(135deg, #dc2626 0%, #991b1b 100%);
            box-shadow: 0 8px 25px rgba(220, 38, 38, 0.35);
        }
        
        .stat-box:nth-child(4):hover {
            background: linear-gradient(135deg, #991b1b 0%, #7f1d1d 100%);
            box-shadow: 0 12px 35px rgba(220, 38, 38, 0.45);
        }
        
        .stat-box h3 {
            margin: 0 0 12px 0;
            font-size: 0.95rem;
            opacity: 0.95;
            font-weight: 500;
            position: relative;
            z-index: 1;
            text-transform: uppercase;
            letter-spacing: 1px;
        }
        
        .stat-number {
            font-size: 2.5rem;
            font-weight: 700;
            position: relative;
            z-index: 1;
        }
        
        .message-succes {
            background: linear-gradient(135deg, #d1fae5 0%, #a7f3d0 100%);
            color: #065f46;
            padding: 18px;
            border-radius: 12px;
            margin-bottom: 25px;
            border-left: 4px solid #10b981;
            box-shadow: 0 4px 15px rgba(16, 185, 129, 0.2);
            font-weight: 500;
        }
        
        .message-erreur {
            background: linear-gradient(135deg, #fee2e2 0%, #fecaca 100%);
            color: #991b1b;
            padding: 18px;
            border-radius: 12px;
            margin-bottom: 25px;
            border-left: 4px solid #ef4444;
            box-shadow: 0 4px 15px rgba(239, 68, 68, 0.2);
            font-weight: 500;
        }
        
        .form-paiement {
            background: linear-gradient(145deg, #fef2f2 0%, #ffffff 100%);
            padding: 25px;
            border-radius: 15px;
            margin: 20px 0;
            border: 1px solid rgba(220, 38, 38, 0.1);
        }
        
        .form-paiement h3 {
            color: #dc2626;
            margin-top: 0;
            margin-bottom: 20px;
            font-size: 1.2rem;
        }
        
        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
            margin-bottom: 20px;
        }
        
        .form-group {
            margin-bottom: 15px;
        }
        
        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            font-size: 14px;
            color: #1a1a2e;
        }
        
        .form-group input, .form-group select {
            width: 100%;
            padding: 12px;
            border: 2px solid #dcfce7;
            border-radius: 10px;
            font-size: 14px;
            transition: all 0.3s ease;
            background: white;
        }
        
        .form-group input:focus, .form-group select:focus {
            outline: none;
            border-color: #16a34a;
            box-shadow: 0 0 0 3px rgba(22, 163, 74, 0.1);
        }
        
        .btn-paiement {
            background: linear-gradient(135deg, #dc2626 0%, #991b1b 100%);
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-size: 15px;
            font-weight: 600;
            box-shadow: 0 4px 15px rgba(220, 38, 38, 0.3);
            transition: all 0.3s ease;
        }
        
        .btn-paiement:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(220, 38, 38, 0.4);
            background: linear-gradient(135deg, #991b1b 0%, #7f1d1d 100%);
        }
        
        .action-rapide {
            text-align: center;
            margin-top: 20px;
            padding-top: 20px;
            border-top: 2px solid #fee2e2;
        }
        
        .btn-special {
            background: linear-gradient(135deg, #dc2626 0%, #991b1b 100%);
            display: inline-block;
            margin-top: 12px;
            box-shadow: 0 4px 15px rgba(220, 38, 38, 0.3);
        }
        
        .btn-special:hover {
            box-shadow: 0 6px 20px rgba(220, 38, 38, 0.4);
            background: linear-gradient(135deg, #991b1b 0%, #7f1d1d 100%);
        }
        
        .footer {
            margin-top: 40px;
            text-align: center;
            color: #64748b;
            border-top: 2px solid #dcfce7;
            padding-top: 25px;
            font-size: 0.9rem;
        }
        
        /* Responsive */
        @media (max-width: 768px) {
            .form-row {
                grid-template-columns: 1fr;
            }
            
            h1 {
                font-size: 1.6rem;
            }
            
            .stat-number {
                font-size: 2rem;
            }
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
        <h1>⚽ Tableau de Bord - Gestion des Terrains</h1>
        
        <c:if test="${not empty messageSucces}">
            <div class="message-succes">
                ${messageSucces}
            </div>
        </c:if>
        
        <c:if test="${not empty messageErreur}">
            <div class="message-erreur">
                ${messageErreur}
            </div>
        </c:if>
        
        <div class="statistics">
            <div class="stat-box">
                <h3>Terrains</h3>
                <div class="stat-number">${terrains != null ? terrains.size() : 0}</div>
            </div>
            <div class="stat-box">
                <h3>Clients</h3>
                <div class="stat-number">${clients != null ? clients.size() : 0}</div>
            </div>
            <div class="stat-box">
                <h3>Réservations</h3>
                <div class="stat-number">${reservations != null ? reservations.size() : 0}</div>
            </div>
            <div class="stat-box">
                <h3>Paiements</h3>
                <div class="stat-number">${paiements != null ? paiements.size() : 0}</div>
            </div>
        </div>
        
        <div class="dashboard">
            <div class="card">
                <h2>📊 Navigation Principale</h2>
                <ul class="menu">
                    <li><a href="controller?action=terrains">🏟️ Gérer les Terrains</a></li>
                    <li><a href="controller?action=clients">👥 Gérer les Clients</a></li>
                    <li><a href="controller?action=reservations">📅 Gérer les Réservations</a></li>
                    <li><a href="controller?action=paiements">💰 Gérer les Paiements</a></li>
                </ul>
            </div>
            
            <div class="card">
                <h2>⚙️ Administration</h2>
                <div class="quick-actions">
                    <a href="controller?action=formAjouterTerrain" class="btn">➕ Ajouter un Terrain</a>
                    <a href="controller?action=formAjouterClient" class="btn">👤 Ajouter un Client</a>
                    <a href="controller?action=formCreerReservation" class="btn">📅 Créer une Réservation</a>
                    <a href="controller?action=formAjouterPaiement" class="btn">💰 Ajouter un Paiement</a>
                </div>
            </div>
            
            <div class="card">
                <h2>📈 Rapports et Statistiques</h2>
                <ul class="menu">
                    <li><a href="controller?action=terrainsNonReserves">🏟️ Terrains jamais réservés</a></li>
                    <li><a href="controller?action=clientsFootball">⚽ Clients avec réservations Football</a></li>
                    <li><a href="controller?action=reservationsMars2025">📅 Réservations Mars 2025</a></li>
                    <li><a href="controller?action=statsReservations">📊 Statistiques réservations</a></li>
                    <li><a href="controller?action=statsPaiements">💰 Statistiques paiements</a></li>
                </ul>
            </div>
        </div>
        
        <c:if test="${not empty afficherFormulairePaiement or not empty reservationIdParDefaut}">
        <div class="card">
            <h2>💳 Modification du Mode de Paiement</h2>
            
            <div class="form-paiement">
                <h3>Modifier le mode de paiement d'une réservation</h3>
                
                <form method="post" action="controller">
                    <input type="hidden" name="action" value="executerModifierPaiement">
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label for="reservationId">ID de la Réservation :</label>
                            <input type="number" id="reservationId" name="reservationId" 
                                   value="${reservationIdParDefaut != null ? reservationIdParDefaut : ''}" 
                                   placeholder="Entrez l'ID de la réservation" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="nouveauMode">Nouveau Mode de Paiement :</label>
                            <select id="nouveauMode" name="nouveauMode" required>
                                <option value="">Sélectionnez un mode</option>
                                <option value="espèces" ${modeParDefaut == 'espèces' ? 'selected' : ''}>espèces</option>
                                <option value="carte" ${modeParDefaut == 'carte' ? 'selected' : ''}>carte bancaire</option>
                                <option value="virement" ${modeParDefaut == 'virement' ? 'selected' : ''}>virement bancaire</option>
                                <option value="chèque" ${modeParDefaut == 'chèque' ? 'selected' : ''}>chèque</option>
                                <option value="mobile" ${modeParDefaut == 'mobile' ? 'selected' : ''}>paiement mobile</option>
                            </select>
                        </div>
                    </div>
                    
                    <button type="submit" class="btn-paiement">
                        💾 Modifier le Mode de Paiement
                    </button>
                    
                    <div class="action-rapide">
                        <p style="margin: 0 0 10px 0; font-size: 14px; color: #64748b;">
                            <strong>Action spécifique :</strong> Modifier la réservation 202 en "virement"
                        </p>
                        <a href="controller?action=modifierPaiement202" 
                           class="btn btn-special"
                           onclick="return confirm('Êtes-vous sûr de vouloir modifier le mode de paiement de la réservation #202 en \\'virement\\' ?')">
                            🚀 Modifier paiement #202 en virement
                        </a>
                    </div>
                </form>
            </div>
        </div>
        </c:if>
        
        <div class="card">
            <h2>⚡ Actions Rapides</h2>
            <a href="controller?action=supprimerReservationsAvant2023" class="btn" 
               onclick="return confirm('Êtes-vous sûr de vouloir supprimer toutes les réservations avant 2023 ?')"
               style="background: linear-gradient(135deg, #dc2626 0%, #991b1b 100%);">
               🗑️ Supprimer réservations avant 2023
            </a>
            
            <a href="controller?action=modifierPaiement202" class="btn btn-special" 
               style="margin-top: 10px;"
               onclick="return confirm('Êtes-vous sûr de vouloir modifier le mode de paiement de la réservation #202 en \\'virement\\' ?')">
               💳 Modifier paiement #202 (virement)
            </a>
        </div>
        
        <div class="footer">
        </div>
    </div>
</body>
</html>