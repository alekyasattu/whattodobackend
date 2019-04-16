package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import play.data.format.Formats;

import javax.persistence.*;

@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty("sid")
    private Integer sid;

    @ManyToOne
    @JoinColumn(name="aid")
    private Attractions attraction;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @Basic
    @Column(nullable = false)
    @Formats.DateTime(pattern = "yyyy-MM-dd")
    @JsonProperty("date")
    private String date;



    public Schedule(Integer sid, Attractions attraction, User user, String date) {
        this.sid = sid;
        this.attraction = attraction;
        this.user = user;
        this.date = date;
    }

    public Schedule() {
    }


    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Attractions getAttraction() {
        return attraction;
    }

    public void setAttraction(Attractions attraction) {
        this.attraction = attraction;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
