<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Statistiques des Réservations par Terrain</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f4f4f4;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
            border-bottom: 2px solid #4CAF50;
            padding-bottom: 10px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        .stat-number {
            font-size: 24px;
            font-weight: bold;
            color: #4CAF50;
        }
        .btn {
            display: inline-block;
            padding: 10px 15px;
            margin: 10px;
            background: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
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
        <h1>📊 Statistiques des Réservations par Terrain</h1>
        
        <a href="controller" class="btn">🏠 Accueil</a>
        
        <table>
            <thead>
                <tr>
                    <th>Nom du Terrain</th>
                    <th>ID Terrain</th>
                    <th>Nombre de Réservations</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${not empty statsReservations}">
                        <c:forEach var="stat" items="${statsReservations}">
                            <tr>
                                <td>${stat[0]}</td>
                                <td>${stat[1]}</td>
                                <td class="stat-number">${stat[2]}</td>
                            </tr>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="3" class="text-center">
                                Aucune statistique disponible.
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</body>
</html>