<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modifier Terrain</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }

body {
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
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
        radial-gradient(circle at 20% 50%, rgba(22, 163, 74, 0.2), transparent 50%),
        radial-gradient(circle at 80% 80%, rgba(21, 128, 61, 0.15), transparent 50%);
    z-index: 0;
    pointer-events: none;
}

.container {
    max-width: 650px;
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
    background: linear-gradient(135deg, #16a34a 0%, #15803d 100%);
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
    background: linear-gradient(90deg, #16a34a, #15803d);
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

.form-group { margin-bottom: 25px; }

label {
    display: block;
    margin-bottom: 8px;
    font-weight: 600;
    color: #1a1a2e;
}

input[type="text"],
input[type="number"],
select {
    width: 100%;
    padding: 14px;
    border: 2px solid #dcfce7;
    border-radius: 12px;
    font-size: 15px;
    transition: all 0.3s ease;
}

input:focus, select:focus {
    outline: none;
    border-color: #16a34a;
    box-shadow: 0 0 0 3px rgba(22, 163, 74, 0.1);
}

.terrain-info {
    background: linear-gradient(135deg, #dcfce7 0%, #bbf7d0 100%);
    border: 1px solid #86efac;
    border-radius: 12px;
    padding: 20px;
    margin-bottom: 25px;
    border-left: 4px solid #16a34a;
}

.terrain-info p {
    margin: 5px 0;
    color: #14532d;
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
    background: linear-gradient(135deg, #16a34a 0%, #15803d 100%);
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
    background: #f0fdf4;
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

.badge {
    padding: 6px 12px;
    border-radius: 8px;
    font-size: 13px;
    font-weight: 600;
    background: linear-gradient(135deg, #16a34a 0%, #15803d 100%);
    color: white;
    display: inline-block;
}

.filters {
    background: linear-gradient(145deg, #f0fdf4 0%, #ffffff 100%);
    padding: 20px;
    border-radius: 12px;
    margin: 20px 0;
    border: 1px solid #dcfce7;
}

.results-count {
    background: linear-gradient(135deg, #dcfce7 0%, #bbf7d0 100%);
    border-left: 4px solid #16a34a;
    border-radius: 10px;
    padding: 15px;
    color: #14532d;
    font-weight: 600;
    margin-bottom: 20px;
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
        <h1>✏️ Modifier le Terrain</h1>
        
        <form action="controller" method="post">
            <input type="hidden" name="action" value="modifierTerrain">
            <input type="hidden" name="id" value="${terrain.idTerrain}">
            
            <div class="form-group">
                <label for="nom">Nom du terrain:</label>
                <input type="text" id="nom" name="nom" value="${terrain.nom}" required>
            </div>
            
            <div class="form-group">
                <label for="type">Type de terrain:</label>
                <select id="type" name="type" required>
                    <option value="Football" ${terrain.type == 'Football' ? 'selected' : ''}>Football</option>
                    <option value="Tennis" ${terrain.type == 'Tennis' ? 'selected' : ''}>Tennis</option>
                    <option value="Basketball" ${terrain.type == 'Basketball' ? 'selected' : ''}>Basketball</option>
                    <option value="Natation" ${terrain.type == 'Natation' ? 'selected' : ''}>Natation</option>
                </select>
            </div>
            
            <div class="form-group">
                <label for="capacite">Capacité:</label>
                <input type="number" id="capacite" name="capacite" value="${terrain.capacite}" required>
            </div>
            
            <div class="form-group">
                <label for="localisation">Localisation:</label>
                <input type="text" id="localisation" name="localisation" value="${terrain.localisation}" required>
            </div>
            
            <div class="btn-container">
                <button type="submit" class="btn">Modifier</button>
                <a href="controller?action=terrains" class="btn">Annuler</a>
            </div>
        </form>
    </div>
</body>
</html>