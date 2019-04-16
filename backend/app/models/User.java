package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class User {

    public enum Role {
        ADMIN,
        USER
    }

    public enum State {
        PENDING,
        VERIFIED,
        LOCKED
    }

    @Id
    @JsonProperty("username")
    private String username;

    @Basic
    @JsonIgnore
    private String passwordHash;

    @Basic
    @JsonIgnore
    private String salt;

    @Basic
    @JsonIgnore
    private Integer hashIterations;

    @Basic
    @Column(unique = true, nullable = false)
    @JsonProperty("email")
    private String email;

    @Basic
    @JsonProperty("role")
    private Role role;

    @Basic
    @Column(nullable = false)
    @JsonProperty("city")
    private String city;

    @Basic
    @JsonProperty("state")
    private State state;

    @Basic
    @JsonProperty("accessToken")
    private String accessToken;

    public User() {
    }

    public User(String username, String passwordHash, String salt, Integer hashIterations, String email, Role role, String city, State state, String accessToken) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.hashIterations = hashIterations;
        this.email = email;
        this.role = role;
        this.city = city;
        this.state = state;
        this.accessToken = accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getHashIterations() {
        return hashIterations;
    }

    public void setHashIterations(Integer hashIterations) {
        this.hashIterations = hashIterations;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}