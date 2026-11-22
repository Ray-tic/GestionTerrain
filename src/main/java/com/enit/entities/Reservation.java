package com.enit.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReservation;

    @Temporal(TemporalType.DATE)
    private Date dateReservation;

    @Temporal(TemporalType.TIME)
    private Date heureDebut;

    @Temporal(TemporalType.TIME)
    private Date heureFin;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "terrain_id")
    private Terrain terrain;

    // SUPPRIMÉ: pas de référence directe à Paiement
    // Un paiement référence une réservation, pas l'inverse

    public Integer getIdReservation() { 
        return idReservation; 
    }
    
    public void setIdReservation(Integer idReservation) { 
        this.idReservation = idReservation; 
    }

    public Date getDateReservation() { 
        return dateReservation; 
    }
    
    public void setDateReservation(Date dateReservation) { 
        this.dateReservation = dateReservation; 
    }

    public Date getHeureDebut() { 
        return heureDebut; 
    }
    
    public void setHeureDebut(Date heureDebut) { 
        this.heureDebut = heureDebut; 
    }

    public Date getHeureFin() { 
        return heureFin; 
    }
    
    public void setHeureFin(Date heureFin) { 
        this.heureFin = heureFin; 
    }

    public Client getClient() { 
        return client; 
    }
    
    public void setClient(Client client) { 
        this.client = client; 
    }

    public Terrain getTerrain() { 
        return terrain; 
    }
    
    public void setTerrain(Terrain terrain) { 
        this.terrain = terrain; 
    }
}