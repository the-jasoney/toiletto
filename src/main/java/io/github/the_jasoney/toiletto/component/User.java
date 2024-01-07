package io.github.the_jasoney.toiletto.component;

import jakarta.persistence.*;

import java.util.List;

// 'user' is a reserved keyword in postgreSQL
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(targetEntity = Review.class, mappedBy = "author")
    private List<Review> reviews;

    @OneToMany(targetEntity = Token.class, mappedBy = "toilettouser")
    private List<Token> token;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Token> getToken() {
        return token;
    }

    public void setToken(List<Token> token) {
        this.token = token;
    }
}
