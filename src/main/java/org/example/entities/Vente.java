package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "vente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vente")
    private int id;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    @Temporal(TemporalType.DATE)
    private Date dateAchat;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "vente_article",
            joinColumns = @JoinColumn(name = "vente_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id"))
    private List<Article> articles;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    // Méthode pour ajouter un article à la liste des articles de la vente
    public void addArticle(Article article) {
        articles.add(article);
        article.getVentes().add(this);
    }
}

