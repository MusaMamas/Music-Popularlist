package hh.sarjaprojekti.musiclist.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TrackRepository extends CrudRepository<Track, Long> {
    List<Track> findByGenre(Genre genre);
    List<Track> findByLikes(int Likes);
}
