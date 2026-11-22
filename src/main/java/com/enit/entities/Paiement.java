package com.enit.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "paiement")
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPaiement;

    private Double montant;

    @Temporal(TemporalType.DATE)
    private Date datePaiement;

    private String modePaiement;

    // AJOUT: référence à Reservation
    @OneToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    public Long getIdPaiement() { 
        return idPaiement; 
    }
    
    public void setIdPaiement(Long idPaiement) { 
        this.idPaiement = idPaiement; 
    }

    public Double getMontant() { 
        return montant; 
    }
    
    public void setMontant(Double montant) { 
        this.montant = montant; 
    }

    public Date getDatePaiement() { 
        return datePaiement; 
    }
    
    public void setDatePaiement(Date datePaiement) { 
        this.datePaiement = datePaiement; 
    }

    public String getModePaiement() { 
        return modePaiement; 
    }
    
    public void setModePaiement(String modePaiement) { 
        this.modePaiement = modePaiement; 
    }

    // AJOUT: getter et setter pour reservation
    public Reservation getReservation() { 
        return reservation; 
    }
    
    public void setReservation(Reservation reservation) { 
        this.reservation = reservation; 
    }
}