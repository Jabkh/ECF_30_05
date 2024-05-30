package org.example;

import org.example.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class IHM {

    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean exit = false;
            while (!exit) {
                System.out.println("Menu:");
                System.out.println("1. Gestion de l'inventaire");
                System.out.println("2. Gestion des clients");
                System.out.println("3. Gestion des ventes");
                System.out.println("4. Quitter");
                System.out.print("Choisissez une option: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        gestionInventaire(scanner);
                        break;
                    case 2:
                        gestionClients(scanner);
                        break;
                    case 3:
                        gestionVentes(scanner);
                        break;
                    case 4:
                        System.out.println("Au revoir !");
                        exit = true;
                        break;
                    default:
                        System.out.println("Option invalide.");
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            sessionFactory.close();
        }
    }

    private static void gestionInventaire(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("Gestion de l'inventaire:");
            System.out.println("1. Ajouter un article");
            System.out.println("2. Modifier un article");
            System.out.println("3. Supprimer un article");
            System.out.println("4. Consulter les articles");
            System.out.println("4. Consulter les articles");
            System.out.println("5. Consulter la quantite des articles en stock");

            System.out.println("7. Consulter les produits les plus vendus");
            System.out.println("9. Retour");
            System.out.print("Choisissez une option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    ajouterArticle(scanner);
                    break;
                case 2:
                    modifierArticle(scanner);
                    break;
                case 3:
                    supprimerArticle(scanner);
                    break;
                case 4:
                    listerTousLesArticles();
                    break;
                case 5:

                    afficherArticlesQuantiteLimite();
                    break;
                case 6:

                    break;
                case 7:
                    afficherArticlesLesPlusVendus();
                    break;
                case 8:
                    break;
                case 9:
                    back = true;
                    break;
                default:
                    System.out.println("Option invalide.");
            }
        }
    }

    private static void gestionClients(Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("Gestion des clients:");
            System.out.println("1. Ajouter un client");
            System.out.println("2. Modifier un client");
            System.out.println("3. Supprimer un client");
            System.out.println("4. Consulter les clients");
            System.out.println("5. Retour");
            System.out.print("Choisissez une option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    ajouterClient(scanner);
                    break;
                case 2:
                    modifierClient(scanner);
                    break;
                case 3:
                    supprimerClient(scanner);
                    break;
                case 4:
                    listerTousLesClients();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Option invalide.");
            }
        }
    }

    private static void gestionVentes(Scanner scanner) throws Exception {
        boolean back = false;
        while (!back) {
            System.out.println("Gestion des ventes:");
            System.out.println("1. Enregistrer une vente");
            System.out.println("2. Consulter les ventes");
            System.out.println("3. Consulter le nombre de ventes finalisées");
            System.out.println("4. Consulter les ventes par catégorie");
            System.out.println("5. Consulter les ventes entre deux dates");
            System.out.println("6. Consulter les ventes par l'id du client");
            System.out.println("7. Retour");
            System.out.print("Choisissez une option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    enregistrerVente(scanner);
                    break;
                case 2:
                    consulterVentes();
                    break;
                case 3:
                    afficherNombreVentesFinalisees();
                    break;
                case 4:
                    afficherVentesParCategorie();
                    break;
                case 5:
                    afficherVentesEntreDeuxDates();
                    break;
                case 6:
                    afficherVentesParClientId();
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    System.out.println("Option invalide.");
            }
        }
    }

    private static void ajouterArticle(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            System.out.print("Entrez la description: ");
            String description = scanner.nextLine();

            System.out.print("Entrez la catégorie (HOMME, FEMME, ENFANT): ");
            String categorieString = scanner.nextLine();
            Categorie categorie = Categorie.valueOf(categorieString.toUpperCase());

            System.out.print("Entrez le prix: ");
            double prix = scanner.nextDouble();

            System.out.print("Entrez la taille: ");
            scanner.nextLine();
            String taille = scanner.nextLine();

            System.out.print("Entrez la quantité: ");
            int quantite = scanner.nextInt();

            Article article = Article.builder()
                    .description(description)
                    .categorie(categorie)
                    .prix(prix)
                    .taille(taille)
                    .quantite(quantite)
                    .build();

            session.save(article);
            session.getTransaction().commit();
            System.out.println("Article ajouté avec succès: " + article);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void modifierArticle(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            System.out.print("Entrez l'ID de l'article à modifier: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Article article = session.get(Article.class, id);
            if (article != null) {
                System.out.print("Entrez la nouvelle description: ");
                String description = scanner.nextLine();
                article.setDescription(description);

                System.out.print("Entrez la nouvelle catégorie (HOMME, FEMME, ENFANT): ");
                String categorieString = scanner.nextLine();
                Categorie categorie = Categorie.valueOf(categorieString.toUpperCase());
                article.setCategorie(categorie);

                System.out.print("Entrez le nouveau prix: ");
                double prix = scanner.nextDouble();
                article.setPrix(prix);

                System.out.print("Entrez la nouvelle taille: ");
                scanner.nextLine();
                String taille = scanner.nextLine();
                article.setTaille(taille);

                System.out.print("Entrez la nouvelle quantité: ");
                int quantite = scanner.nextInt();
                article.setQuantite(quantite);

                session.update(article);
                session.getTransaction().commit();
                System.out.println("Article modifié avec succès: " + article);
            } else {
                System.out.println("Article non trouvé.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void supprimerArticle(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            System.out.print("Entrez l'ID de l'article à supprimer: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Article article = session.get(Article.class, id);
            if (article != null) {
                session.delete(article);
                session.getTransaction().commit();
                System.out.println("Article supprimé avec succès.");
            } else {
                System.out.println("Article non trouvé.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listerTousLesArticles() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Article> articles = session.createQuery("from Article", Article.class).list();
            session.getTransaction().commit();
            System.out.println("Tous les articles:");
            for (Article article : articles) {
                System.out.println(article);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void ajouterClient(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            System.out.print("Entrez le nom du client: ");
            String nom = scanner.nextLine();

            System.out.print("Entrez le mail du client: ");
            String mail = scanner.nextLine();

            Client client = Client.builder()
                    .nom(nom)
                    .mail(mail)
                    .build();

            session.save(client);
            session.getTransaction().commit();
            System.out.println("Client ajouté avec succès: " + client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void modifierClient(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            System.out.print("Entrez l'ID du client à modifier: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Client client = session.get(Client.class, id);
            if (client != null) {
                System.out.print("Entrez le nouveau nom: ");
                String nom = scanner.nextLine();
                client.setNom(nom);

                System.out.print("Entrez le nouveau mail: ");
                String mail = scanner.nextLine();
                client.setMail(mail);

                session.update(client);
                session.getTransaction().commit();
                System.out.println("Client modifié avec succès: " + client);
            } else {
                System.out.println("Client non trouvé.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void supprimerClient(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            System.out.print("Entrez l'ID du client à supprimer: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Client client = session.get(Client.class, id);
            if (client != null) {
                session.delete(client);
                session.getTransaction().commit();
                System.out.println("Client supprimé avec succès.");
            } else {
                System.out.println("Client non trouvé.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listerTousLesClients() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Client> clients = session.createQuery("from Client", Client.class).list();
            session.getTransaction().commit();
            System.out.println("Tous les clients:");
            for (Client client : clients) {
                System.out.println(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void enregistrerVente(Scanner scanner) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            System.out.print("Entrez l'ID du client: ");
            int clientId = scanner.nextInt();
            scanner.nextLine();
            Client client = session.get(Client.class, clientId);

            if (client == null) {
                System.out.println("Client non trouvé.");
                return;
            }

            Vente vente = Vente.builder()
                    .statut(Statut.EN_COURS)
                    .dateAchat(new Date())
                    .client(client)
                    .build();

            boolean addMoreArticles = true;
            while (addMoreArticles) {
                System.out.print("Entrez l'ID de l'article: ");
                int articleId = scanner.nextInt();
                scanner.nextLine();
                Article article = session.get(Article.class, articleId);

                if (article != null) {
                    vente.getArticles().add(article);
                } else {
                    System.out.println("Article non trouvé.");
                }

                System.out.print("Voulez-vous ajouter un autre article ? (oui/non): ");
                String response = scanner.nextLine();
                addMoreArticles = response.equalsIgnoreCase("oui");
            }

            session.save(vente);
            session.getTransaction().commit();
            System.out.println("Vente enregistrée avec succès: " + vente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void consulterVentes() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Vente> ventes = session.createQuery("from Vente", Vente.class).list();
            session.getTransaction().commit();
            System.out.println("Toutes les ventes:");
            for (Vente vente : ventes) {
                System.out.println(vente);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void afficherArticlesQuantiteLimite() {
        try (Session session = sessionFactory.openSession()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez la quantité limite : ");
            int quantiteLimite = scanner.nextInt();
            Query<Article> articleQuery = session.createQuery("from Article where quantite < :quantiteLimite", Article.class);
            articleQuery.setParameter("quantiteLimite", quantiteLimite);
            List<Article> articles = articleQuery.list();
            System.out.println("Articles avec une quantité en stock inférieure à " + quantiteLimite + ":");
            for (Article article : articles) {
                System.out.println("Identifiant: " + article.getId() + ", Catégorie: " + article.getCategorie() + ", Description: " + article.getDescription() +", Prix " + article.getPrix() + ", Quantité: " + article.getQuantite() + ", Taille: " + article.getTaille());
            }
        }
    }


    private static void afficherNombreVentesFinalisees() {
        try (Session session = sessionFactory.openSession()) {
            Query<Integer> query = session.createQuery(
                    "select count(v) from Vente v where v.statut = :statutFinalise",
                    Integer.class
            );
            query.setParameter("statutFinalise", Statut.FINALISEE);
            int nombreVentes = query.uniqueResult();
            System.out.println("Nombre de ventes finalisées : " + nombreVentes);
        }
    }

    private static void afficherArticlesLesPlusVendus() {
        try (Session session = sessionFactory.openSession()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez le nombre de produits les plus vendus à afficher : ");
            int nombreArticles = scanner.nextInt();
            Query<Article> query = session.createQuery(
                    "select va.article from VenteArticle va group by va.article order by count(va.article) desc",
                    Article.class
            );
            query.setMaxResults(nombreArticles);
            List<Article> articles = query.list();
            System.out.println("Les " + nombreArticles + " articles les plus vendus :");
            for (Article article : articles) {
                System.out.println("Identifiant: " + article.getId() + ", Description: " + article.getDescription());
            }
        }
    }

    private static void afficherVentesParCategorie() {
        try (Session session = sessionFactory.openSession()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez la catégorie (homme, femme, enfant) : ");
            String categorieStr = scanner.nextLine();
            Categorie categorie = Categorie.valueOf(categorieStr.toUpperCase());
            Query<Vente> venteQuery = session.createQuery(
                    "select v from Vente v join v.articles a where a.categorie = :categorie", Vente.class);
            venteQuery.setParameter("categorie", categorie);
            List<Vente> ventes = venteQuery.list();
            System.out.println("Ventes de la catégorie " + categorie + " :");
            for (Vente vente : ventes) {
                System.out.println(vente);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Catégorie invalide !");
        }
    }

    private static void afficherVentesEntreDeuxDates() throws Exception {
        try (Session session = sessionFactory.openSession()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Entrez la date de début (yyyy-MM-dd) : ");
            String dateDebutStr = scanner.nextLine();
            LocalDate dateDebut = LocalDate.parse(dateDebutStr);
            System.out.print("Entrez la date de fin (yyyy-MM-dd) : ");
            String dateFinStr = scanner.nextLine();
            LocalDate dateFin = LocalDate.parse(dateFinStr);
            Query<Vente> venteQuery = session.createQuery(
                    "select v from Vente v where v.dateAchat between :dateDebut and :dateFin", Vente.class);
            venteQuery.setParameter("dateDebut", dateDebut);
            venteQuery.setParameter("dateFin", dateFin);
            List<Vente> ventes = venteQuery.list();
            System.out.println("Ventes entre " + dateDebut + " et " + dateFin + " :");
            for (Vente vente : ventes) {
                System.out.println(vente);
            }
        } catch (Exception e) {
            System.out.println("Une erreur s'est produite lors de la récupération des ventes entre les deux dates.");
            e.printStackTrace();
        }
    }


        private static void afficherVentesParClientId () {
            try (Session session = sessionFactory.openSession()) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Entrez l'ID du client : ");
                int idClient = scanner.nextInt();
                Query<Vente> venteQuery = session.createQuery(
                        "select v from Vente v where v.client.id = :clientId", Vente.class);
                venteQuery.setParameter("clientId", idClient);
                List<Vente> ventes = venteQuery.list();
                System.out.println("Ventes pour le client avec l'ID " + idClient + " :");
                for (Vente vente : ventes) {
                    System.out.println(vente);
                }
            } catch (Exception e) {
                System.out.println("Une erreur s'est produite lors de la récupération des ventes pour le client.");
                e.printStackTrace();
            }
        }


    }



