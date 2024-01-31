package gdsc3rdsc2.SignLanguageEducation.repository;

import gdsc3rdsc2.SignLanguageEducation.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
}
