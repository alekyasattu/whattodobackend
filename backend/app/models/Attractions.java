package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import play.data.format.Formats;

import javax.persistence.*;
import java.util.List;

@Entity
public class Attractions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("aid")
    private Integer aid;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @Basic
    @Column(unique = true, nullable = false)
    @JsonProperty("attr_Name")
    private String attr_Name;

    @Basic
    @Column(nullable = false)
    @JsonProperty("category")
    private String category;

    @Basic
    @Column(nullable = false)
    @JsonProperty("city")
    private String city;

    @Basic
    @JsonProperty("description")
    private String description;

    @Basic
    @Column(nullable = false)
    @JsonProperty("location")
    private String location;

    @Basic
    @Column(nullable = false)
    @JsonProperty("fare")
    private Integer fare;

    @Basic
    @JsonProperty("rating")
    private Float rating;

    @Basic
    @Column(nullable = false)
    @Formats.DateTime(pattern = "HH:mm:ss")
    @JsonProperty("from_Timings")
    private String from_Timings;

    @Basic
    @Column(nullable = false)
    @Formats.DateTime(pattern = "HH:mm:ss")
    @JsonProperty("upto_Timings")
    private String upto_Timings;

    @Basic
    @JsonProperty("latitude")
    private Float latitude;

    @Basic
    @JsonProperty("longitude")
    private Float longitude;

    @Basic
    @JsonProperty("imageUrls")
    private String[] imageUrls;

    public Attractions(Integer aid, User user, String attr_Name, String category, String city, String description, String location, Integer fare, Float rating, String from_Timings, String upto_Timings, Float latitude, Float longitude, String[] imageUrls) {
        this.aid = aid;
        this.user = user;
        this.attr_Name = attr_Name;
        this.category = category;
        this.city = city;
        this.description = description;
        this.location = location;
        this.fare = fare;
        this.rating = rating;
        this.from_Timings = from_Timings;
        this.upto_Timings = upto_Timings;
        this.latitude = latitude;
        this.longitude = longitude;
        this.imageUrls = imageUrls;
    }

    public Attractions() {
    }

    public String getAttr_Name() {
        return attr_Name;
    }

    public void setAttr_Name(String attr_Name) {
        this.attr_Name = attr_Name;
    }

    public String getFrom_Timings() {
        return from_Timings;
    }

    public void setFrom_Timings(String from_Timings) {
        this.from_Timings = from_Timings;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUpto_Timings() {
        return upto_Timings;
    }

    public void setUpto_Timings(String upto_Timings) {
        this.upto_Timings = upto_Timings;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getFare() {
        return fare;
    }

    public void setFare(Integer fare) {
        this.fare = fare;
    }

    public Float getRating() {
        return rating;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public String[] getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(String[] imageUrls) {
        this.imageUrls = imageUrls;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
}



