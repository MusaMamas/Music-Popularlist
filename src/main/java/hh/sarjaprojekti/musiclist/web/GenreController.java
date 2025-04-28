package hh.sarjaprojekti.musiclist.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import hh.sarjaprojekti.musiclist.domain.Genre;
import hh.sarjaprojekti.musiclist.domain.GenreRepository;

@Controller
public class GenreController {

    @Autowired
    private GenreRepository genreRepository;

    @GetMapping("/genrelist")
    public String listGenres(Model model) {
        model.addAttribute("genres", genreRepository.findAll());
        return "genrelist";
    }

    @GetMapping("/addgenre")
    public String showAddGenreForm(Model model) {
        model.addAttribute("genre", new Genre());
        return "addgenre";
    }

    @PostMapping("/addgenre")
    public String addGenre(@ModelAttribute Genre genre) {
        genreRepository.save(genre);
        return "redirect:/genrelist";
    }
}
