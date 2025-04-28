package hh.sarjaprojekti.musiclist.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genreid;

    private String name;

    @OneToMany(mappedBy = "genre")
    @JsonIgnore
    private List<Track> tracks;

    public Genre() {}

    public Genre(String name) {
        this.name = name;
    }

    public Long getGenreid() { return genreid; }
    public void setGenreid(Long genreid) { this.genreid = genreid; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Track> getTracks() { return tracks; }
    public void setTracks(List<Track> tracks) { this.tracks = tracks; }

    @Override
    public String toString() {
        return "Genre [genreid=" + genreid + ", name=" + name + "]";
    }
}
