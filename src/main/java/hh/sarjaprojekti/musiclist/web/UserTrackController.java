package hh.sarjaprojekti.musiclist.web;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import hh.sarjaprojekti.musiclist.domain.Genre;
import hh.sarjaprojekti.musiclist.domain.GenreRepository;
import hh.sarjaprojekti.musiclist.domain.Track;
import hh.sarjaprojekti.musiclist.domain.TrackRepository;
import hh.sarjaprojekti.musiclist.domain.User;
import hh.sarjaprojekti.musiclist.domain.UserRepository;
import hh.sarjaprojekti.musiclist.domain.UserTrack;
import hh.sarjaprojekti.musiclist.domain.UserTrackRepository;

@Controller
public class UserTrackController {

    @Autowired
    private UserTrackRepository userTrackRepository;

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GenreRepository genreRepository;

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
    }

    @GetMapping("/user/tracks")
    public String getUserTracks(Model model,
                                @RequestParam(required = false) Long genreId,
                                @RequestParam(required = false) String userStatus) {
        User currentUser = getCurrentUser();
        List<UserTrack> filteredUserTracks;
        model.addAttribute("genres", genreRepository.findAll());

        if (genreId != null && userStatus != null && !userStatus.isEmpty()) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + genreId));
            filteredUserTracks = userTrackRepository.findByUserAndUserStatusAndTrack_Genre(currentUser, userStatus, genre);
            model.addAttribute("selectedGenreId", genreId);
            model.addAttribute("selectedUserStatus", userStatus);
        } else if (genreId != null) {
            Genre genre = genreRepository.findById(genreId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid genre ID: " + genreId));
            filteredUserTracks = userTrackRepository.findByUserAndTrack_Genre(currentUser, genre);
            model.addAttribute("selectedGenreId", genreId);
        } else if (userStatus != null && !userStatus.isEmpty()) {
            filteredUserTracks = userTrackRepository.findByUserAndUserStatus(currentUser, userStatus);
            model.addAttribute("selectedUserStatus", userStatus);
        } else {
            filteredUserTracks = userTrackRepository.findByUser(currentUser);
        }

        model.addAttribute("userTracks", filteredUserTracks);
        return "userTracks";
    }

    @PostMapping("/tracks/update")
    public String updateUserTrackStatus(@RequestParam Long id, @RequestParam String userStatus) {
        userTrackRepository.findById(id).ifPresent(userTrack -> {
            userTrack.setUserStatus(userStatus);
            userTrackRepository.save(userTrack);
        });
        return "redirect:/user/tracks";
    }

    @PostMapping("/tracks/removefromuser")
    public String removeFromUserList(@RequestParam Long id) {
        userTrackRepository.findById(id).ifPresent(userTrackRepository::delete);
        return "redirect:/user/tracks";
    }

    @PostMapping("/tracks/addtouser")
    public String addToUserList(@RequestParam Long id) {
        User currentUser = getCurrentUser();
        Track track = trackRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid track ID: " + id));
        userTrackRepository.findByUserAndTrack(currentUser, track)
                .orElseGet(() -> userTrackRepository.save(new UserTrack(currentUser, track, "Liked")));
        return "redirect:/tracklist";
    }
}
