package gdsc3rdsc2.SignLanguageEducation.repository;

import gdsc3rdsc2.SignLanguageEducation.domain.Script;
import gdsc3rdsc2.SignLanguageEducation.domain.projection.ScriptProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScriptRepository extends JpaRepository<Script, Long> {
    List<ScriptProjection> findAllBy();
}
