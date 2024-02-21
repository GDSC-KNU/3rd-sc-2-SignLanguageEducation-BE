package gdsc3rdsc2.SignLanguageEducation.postconstruct;

import gdsc3rdsc2.SignLanguageEducation.domain.Sentence;
import gdsc3rdsc2.SignLanguageEducation.domain.domainenum.Concern;
import gdsc3rdsc2.SignLanguageEducation.repository.SentenceRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "sentenceConstruct")
@RequiredArgsConstructor
@Slf4j
public class SentenceConstruct {
    private final SentenceRepository sentenceRepository;

    @PostConstruct
    public void init(){
        log.info("SentenceConstruct init");

        Sentence sentence1 = Sentence.builder()
                .concern(Concern.SOCCER)
                .sentence("축구 국가대표 팀이 아시안컵에서 4등을 했다")
                .build();

        Sentence sentence2 = Sentence.builder()
                .concern(Concern.SOCCER)
                .sentence("어제 축구 경기는 정말 명장면이 많았어")
                .build();

        Sentence sentence3 = Sentence.builder()
                .concern(Concern.BASKETBALL)
                .sentence("농구 경기를 직접 보는건 즐거워")
                .build();

        Sentence sentence4 = Sentence.builder()
                .concern(Concern.TRANSPORTATION)
                .sentence("여기에서 버스로 갈아타야해")
                .build();

        Sentence sentence5 = Sentence.builder()
                .concern(Concern.TRANSPORTATION)
                .sentence("사거리에서 좌회전 해야해요")
                .build();
        sentenceRepository.saveAll(List.of(sentence1, sentence2, sentence3, sentence4, sentence5));
    }
}
