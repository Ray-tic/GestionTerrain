<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Erreur</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f8f9fa;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .error-container {
            text-align: center;
            padding: 40px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
            max-width: 600px;
        }
        .error-icon {
            font-size: 80px;
            color: #f44336;
            margin-bottom: 20px;
        }
        h1 {
            color: #333;
            margin-bottom: 20px;
        }
        .error-message {
            background-color: #ffebee;
            border: 1px solid #ffcdd2;
            border-radius: 5px;
            padding: 15px;
            margin: 20px 0;
            color: #c62828;
            font-family: monospace;
            text-align: left;
            max-height: 200px;
            overflow-y: auto;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin: 10px;
            background: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
        .btn:hover {
            background: #45a049;
        }
        .error-code {
            color: #757575;
            margin-top: 20px;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-icon">⚠️</div>
        <h1>Une erreur est survenue</h1>
        
        <div class="error-message">
            <c:choose>
                <c:when test="${not empty erreur}">
                    ${erreur}
                </c:when>
                <c:when test="${not empty pageContext.exception}">
                    ${pageContext.exception.message}
                </c:when>
                <c:otherwise>
                    Une erreur inattendue s'est produite.
                </c:otherwise>
            </c:choose>
        </div>
        
        <div>
            <a href="controller" class="btn">🏠 Accueil</a>
            <a href="javascript:history.back()" class="btn">↩️ Retour en arrière</a>
        </div>
        
        <div class="error-code">
            Code d'erreur: ${pageContext.errorData.statusCode}
        </div>
    </div>
</body>
</html>