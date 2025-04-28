package hh.sarjaprojekti.musiclist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import hh.sarjaprojekti.musiclist.domain.Genre;
import hh.sarjaprojekti.musiclist.domain.GenreRepository;
import hh.sarjaprojekti.musiclist.domain.Track;
import hh.sarjaprojekti.musiclist.domain.TrackRepository;
import hh.sarjaprojekti.musiclist.domain.User;
import hh.sarjaprojekti.musiclist.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MusiclistApplication {

	private static final Logger log = LoggerFactory.getLogger(MusiclistApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MusiclistApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(TrackRepository trackRepository, GenreRepository genreRepository) {
		return (args) -> {
			Genre pop = genreRepository.save(new Genre("Pop"));
			Genre rock = genreRepository.save(new Genre("Rock"));
			Genre rap = genreRepository.save(new Genre("Rap"));

			trackRepository.save(new Track(null, "Blinding Lights", "The Weeknd", 2019,
					"One of the best-selling singles of all time.", 200, 12000, pop));
			trackRepository.save(new Track(null, "Bohemian Rhapsody", "Queen", 1975,
					"A rock opera in six minutes.", 354, 8500, rock));
			trackRepository.save(new Track(null, "Lose Yourself", "Eminem", 2002,
					"Theme song from 8 Mile, Grammy winner.", 326, 9000, rap));
			trackRepository.save(new Track(null, "Shape of You", "Ed Sheeran", 2017,
					"Global hit with catchy beat.", 240, 15000, pop));
			trackRepository.save(new Track(null, "Smells Like Teen Spirit", "Nirvana", 1991,
					"Grunge anthem of the 90s.", 301, 7200, rock));

			log.info("Genres added:");
			genreRepository.findAll().forEach(genre -> log.info(genre.toString()));

			log.info("Tracks added:");
			trackRepository.findAll().forEach(track -> log.info(track.toString()));
		};
	}

	@Bean
	public CommandLineRunner addDefaultUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return (args) -> {
			if (userRepository.findByUsername("admin").isEmpty()) {
				User admin = new User("admin", passwordEncoder.encode("admin"), "admin@example.com", "ADMIN");
				userRepository.save(admin);
			}
			if (userRepository.findByUsername("user").isEmpty()) {
				User user = new User("user", passwordEncoder.encode("user"), "user@example.com", "USER");
				userRepository.save(user);
			}
			if (userRepository.findByUsername("user1").isEmpty()) {
				User user = new User("user1", passwordEncoder.encode("user1"), "user1@example.com", "USER");
				userRepository.save(user);
			}
		};
	}
}
