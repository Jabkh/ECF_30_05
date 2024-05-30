package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "article")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;
    @Enumerated(EnumType.STRING)
    private Categorie categorie;

    private double prix;

    private String taille;

    private int quantite;

    @ManyToOne
    @JoinColumn(name = "id_vente")
    private Vente vente;

    @ManyToMany(mappedBy = "articles", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Vente> ventes;

    // Méthode pour ajouter une vente à la liste des ventes de l'article
    public void addVente(Vente vente) {
        ventes.add(vente);
        vente.getArticles().add(this);
    }
}

