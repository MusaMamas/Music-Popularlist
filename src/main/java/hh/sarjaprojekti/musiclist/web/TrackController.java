package hh.sarjaprojekti.musiclist.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import hh.sarjaprojekti.musiclist.domain.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class TrackController {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTrackRepository userTrackRepository;

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @GetMapping("/addtrack")
    public String getAddTrackForm(Model model) {
        model.addAttribute("track", new Track());
        model.addAttribute("genres", genreRepository.findAll());
        return "addtrack";
    }

    @PostMapping("/addtrack")
    public String addTrack(@ModelAttribute Track track) {
        trackRepository.save(track);
        return "redirect:/tracklist";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("track", trackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid track ID: " + id)));
        model.addAttribute("genres", genreRepository.findAll());
        return "edittrack";
    }

    @PostMapping("/update")
    public String updateTrack(@ModelAttribute Track track) {
        trackRepository.save(track);
        return "redirect:/tracklist";
    }

    @PostMapping("/tracks/delete/{id}")
    public String deleteTrack(@PathVariable Long id) {
        trackRepository.deleteById(id);
        return "redirect:/tracklist";
    }

    @GetMapping("/tracks/{id}")
    public String getTrackDetails(@PathVariable Long id, Model model) {
        model.addAttribute("track", trackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid track ID: " + id)));
        return "trackdetails";
    }

    @GetMapping("/genre/{id}")
    public String getTracksByGenre(@PathVariable("id") Long genreId, Model model) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + genreId));
        List<Track> genreTracks = trackRepository.findByGenre(genre);
        User currentUser = getCurrentUser();
        List<UserTrack> userTracks = userTrackRepository.findByUser(currentUser);
        Map<Long, UserTrack> userTrackMap = userTracks.stream()
                .collect(Collectors.toMap(ut -> ut.getTrack().getId(), ut -> ut));
        model.addAttribute("genre", genre);
        model.addAttribute("tracks", genreTracks);
        model.addAttribute("userTrackMap", userTrackMap);
        model.addAttribute("genreFilter", true);
        return "tracklist";
    }

    @GetMapping("/tracklist")
    public String getTracks(Model model, @RequestParam(required = false) Long genreId) {
        User currentUser = getCurrentUser();
        List<Track> filteredTracks;
        model.addAttribute("genres", genreRepository.findAll());

        if (genreId != null) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + genreId));
            filteredTracks = trackRepository.findByGenre(genre);
            model.addAttribute("selectedGenreId", genreId);
        } else {
            filteredTracks = (List<Track>) trackRepository.findAll();
        }

        List<UserTrack> userTracks = userTrackRepository.findByUser(currentUser);
        Map<Long, UserTrack> userTrackMap = userTracks.stream()
                .collect(Collectors.toMap(ut -> ut.getTrack().getId(), ut -> ut));
        model.addAttribute("tracks", filteredTracks);
        model.addAttribute("userTrackMap", userTrackMap);
        return "tracklist";
    }
}
