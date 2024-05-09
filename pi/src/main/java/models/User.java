package models;
import java.util.Date;
import java.util.Objects;

public class User {



        private int id;
    private String email;
    private String roles;
    private String password;
    private boolean isVerified;

    private String nom;
    private String prenom;
    private Date date_naissance;
    private String numero_telephone;

        public User() {
            // Constructeur vide
        }

        // Getters et setters
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNumeroTelephone() {
            return numero_telephone;
        }

        public void setNumeroTelephone(String numero_telephone) {
            this.numero_telephone = numero_telephone;
        }

        public String getRoles() {
            return roles;
        }

        public void setRoles(String roles) {
            this.roles = roles;
        }

        public boolean isVerified() {
            return isVerified;
        }

        public void setVerified(boolean verified) {
            isVerified = verified;
        }

        public Date  getDateNaissance() {
            return date_naissance;
        }

        public void setDateNaissance(Date date_naissance) {
            this.date_naissance = date_naissance;
        }
    public User(int id,  String email, String password,String nom, String prenom, String numero_telephone) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.numero_telephone = numero_telephone;
    }

    public User(String email, String password,String nom, String prenom,  String numero_telephone) {
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.numero_telephone = numero_telephone;
    }

    // Getters et setters...

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && isVerified == user.isVerified && Objects.equals(nom, user.nom) && Objects.equals(prenom, user.prenom) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(numero_telephone, user.numero_telephone) && Objects.equals(roles, user.roles) && Objects.equals(date_naissance, user.date_naissance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email,  roles, password,isVerified,nom, prenom,  date_naissance,numero_telephone);
    }

    }
