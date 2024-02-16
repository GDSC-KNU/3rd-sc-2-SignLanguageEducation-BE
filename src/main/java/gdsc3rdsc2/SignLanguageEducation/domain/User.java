package gdsc3rdsc2.SignLanguageEducation.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@Entity
@Table(name = "USERS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;

    private String refreshToken;

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    @ElementCollection
    @CollectionTable(name = "user_scripts", joinColumns = @JoinColumn(name = "user_id"))
    private List<Long> scriptIds;

    @ElementCollection
    @CollectionTable(name = "user_sentences", joinColumns = @JoinColumn(name = "user_id"))
    private List<Long> sentenceIds;
}
