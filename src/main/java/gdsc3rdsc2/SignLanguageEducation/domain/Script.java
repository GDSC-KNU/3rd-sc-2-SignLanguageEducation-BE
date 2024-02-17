package gdsc3rdsc2.SignLanguageEducation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Script {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @ElementCollection
    @CollectionTable(name = "script_sentence", joinColumns = @JoinColumn(name = "script_id"))
    private List<String> sentences;
}
