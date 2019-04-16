package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Image {

    @Id
    @JsonProperty("imageUrl")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name="aid")
    private Attractions attraction;

    public Image() {
    }

    public Image(String imageUrl){

        this.imageUrl=imageUrl;
    }

    @Override
    public String toString() {
        return "image url is" + imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Attractions getAttraction() {
        return attraction;
    }

    public void setAttraction(Attractions attraction) {
        this.attraction = attraction;
    }
}
