package gdsc3rdsc2.SignLanguageEducation.domain;

import gdsc3rdsc2.SignLanguageEducation.domain.domainenum.Concern;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Sentence {
    @Id
    @GeneratedValue
    private Long id;

    private Concern concern;

    private String sentence;
}
