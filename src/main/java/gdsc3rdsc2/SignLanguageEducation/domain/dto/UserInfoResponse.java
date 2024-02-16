package gdsc3rdsc2.SignLanguageEducation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String userName;
    private String password;
    private List<Long> scriptIds;
    private List<Long> sentenceIds;
}
