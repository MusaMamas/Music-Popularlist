package hh.sarjaprojekti.musiclist.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "user_tracks")
public class UserTrack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "track_id")
    private Track track;

    private String userStatus; // e.g., "Liked", "Disliked", "Neutral", "Favorite"

    public UserTrack() {}

    public UserTrack(User user, Track track, String userStatus) {
        this.user = user;
        this.track = track;
        this.userStatus = userStatus;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Track getTrack() { return track; }
    public void setTrack(Track track) { this.track = track; }

    public String getUserStatus() { return userStatus; }
    public void setUserStatus(String userStatus) { this.userStatus = userStatus; }
}