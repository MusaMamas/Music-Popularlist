package hh.sarjaprojekti.musiclist.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hh.sarjaprojekti.musiclist.domain.Track;
import hh.sarjaprojekti.musiclist.domain.TrackRepository;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class TrackRestController {

    @Autowired
    private TrackRepository trackRepository;

    @GetMapping("/tracks")
    public List<Track> getAllTracks() {
        return (List<Track>) trackRepository.findAll();
    }

    @GetMapping("/tracks/{id}")
    public Optional<Track> getTrackById(@PathVariable Long id) {
        return trackRepository.findById(id);
    }

    @PostMapping("/tracks")
    public Track createTrack(@RequestBody Track track) {
        return trackRepository.save(track);
    }

    @PutMapping("/tracks/{id}")
    public Track updateTrack(@PathVariable Long id, @RequestBody Track trackDetails) {
        return trackRepository.findById(id).map(track -> {
            track.setTitle(trackDetails.getTitle());
            track.setArtist(trackDetails.getArtist());
            track.setReleaseYear(trackDetails.getReleaseYear());
            track.setDescription(trackDetails.getDescription());
            track.setDuration(trackDetails.getDuration());
            track.setLikes(trackDetails.getLikes());
            track.setUserStatus(trackDetails.getUserStatus());
            track.setGenre(trackDetails.getGenre());
            return trackRepository.save(track);
        }).orElseThrow(() -> new IllegalArgumentException("Invalid track ID: " + id));
    }

    @DeleteMapping("/tracks/{id}")
    public void deleteTrack(@PathVariable Long id) {
        trackRepository.deleteById(id);
    }
}