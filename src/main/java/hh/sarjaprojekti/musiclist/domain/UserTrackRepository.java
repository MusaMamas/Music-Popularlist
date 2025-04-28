package hh.sarjaprojekti.musiclist.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserTrackRepository extends CrudRepository<UserTrack, Long> {

    List<UserTrack> findByUser(User user);

    Optional<UserTrack> findByUserAndTrack(User user, Track track);

    List<UserTrack> findByUserAndUserStatus(User user, String userStatus);

    List<UserTrack> findByUserAndTrack_Genre(User user, Genre genre);

    List<UserTrack> findByUserAndUserStatusAndTrack_Genre(User user, String userStatus, Genre genre);
}
