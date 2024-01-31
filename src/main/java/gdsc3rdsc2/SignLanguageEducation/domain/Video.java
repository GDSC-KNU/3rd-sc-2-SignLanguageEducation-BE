package gdsc3rdsc2.SignLanguageEducation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Video {

    @Id
    @GeneratedValue
    private Long id;

    private String url;
}
