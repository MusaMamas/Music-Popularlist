package hh.sarjaprojekti.musiclist.domain;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

@Entity
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String artist;
    private int releaseYear;
    private String description;
    private int duration; // in seconds or minutes
    private int likes; // number of likes
    private String userStatus; // e.g. "Liked", "Disliked", "Neutral"

    @ManyToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "track")
    private List<UserTrack> userTracks;

    public Track() {
        // Default constructor
    }

    public Track(Long id, String title, String artist, int releaseYear, String description, int duration,
                 int likes, Genre genre) {
        this.title = title;
        this.artist = artist;
        this.releaseYear = releaseYear;
        this.description = description;
        this.duration = duration;
        this.likes = likes;
        this.genre = genre;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getArtist() { return artist; }
    public void setArtist(String artist) { this.artist = artist; }

    public int getReleaseYear() { return releaseYear; }
    public void setReleaseYear(int releaseYear) { this.releaseYear = releaseYear; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public int getLikes() { return likes; }
    public void setLikes(int likes) { this.likes = likes; }

    public String getUserStatus() { return userStatus; }
    public void setUserStatus(String userStatus) { this.userStatus = userStatus; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public List<UserTrack> getUserTracks() { return userTracks; }
    public void setUserTracks(List<UserTrack> userTracks) { this.userTracks = userTracks; }

    @Override
    public String toString() {
        return "Track [id=" + id + ", title=" + title + ", artist=" + artist +
               ", releaseYear=" + releaseYear + ", duration=" + duration +
               ", likes=" + likes + ", genre=" + genre + "]";
    }
}