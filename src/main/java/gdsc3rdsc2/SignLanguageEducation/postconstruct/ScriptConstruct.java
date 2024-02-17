package gdsc3rdsc2.SignLanguageEducation.postconstruct;

import gdsc3rdsc2.SignLanguageEducation.domain.Script;
import gdsc3rdsc2.SignLanguageEducation.repository.ScriptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "scriptConstruct")
@RequiredArgsConstructor
@Slf4j
public class ScriptConstruct {
    private final ScriptRepository scriptRepository;

    public void init(){
        log.info("ScriptConstruct init");

        Script script1 = Script.builder()
                .title("국가대표 축구 경기 소회")
                .sentences(List.of(
                        "어제 축구 경기 봤어?",
                        "당연하지. 승부차기까지 긴장하면서 봤어",
                        "맞아 조현우가 멋진 선방을 보여줘서 짜릿하더라",
                        "그때는 정말 긴장했지. 선방을 보여준 걸 보면서 마음이 놓였어.",
                        "다음 경기는 같이 보는게 어때?",
                        "좋아. 엄청 기대된다."
                ))
                .build();

        Script script2 = Script.builder()
                .title("농구 흥미 묻기")
                .sentences(List.of(
                        "너 혹시 농구 좋아해?",
                        "물론이지 나는 미국에 농구선수 스테픈 커리의 팬이야!",
                        "오! 어쩌다가 그 선수를 좋아하게 됐어?",
                        " 커리의 실력과 프로 마인드를 보고 팬이 됐어",
                        "그렇구나 그러면 다음에 같이 농구할래?",
                        "아니 아쉽게도 나는 농구를 직접 하는건 선호하지 않아"
                ))
                .build();

        Script script3 = Script.builder()
                .title("축구 경험 말하기")
                .sentences(List.of(
                        "나는 어제 친구들과 축구를 했어",
                        "그렇구나 몇 명에서 했는데?",
                        "8명에서 4 대 4로 나눠서 진행했어",
                        "재밌었겠다. 너는 무슨 역할이었어?",
                        "나는 패스를 못하는 대신 반응속도가 좋아서 골키퍼를 했어",
                        "멋지다 다음에 나도 같이 할래"
                ))
                .build();

        Script script4 = Script.builder()
                .title("축구 제안하기")
                .sentences(List.of(
                        "오늘 같이 축구 하자",
                        "좋아. 어디로 가면 될까?",
                        "학교 운동장으로 4시까지 오면 돼.",
                        "어제 축구공을 새로 샀는데, 가지고 갈까?",
                        "잘 됐네. 새로운 축구공 써보자.",
                        "알았어. 집에서 축구공하고 축구화만 챙겨서 나올게."
                ))
                .build();

        scriptRepository.saveAll(List.of(script1, script2, script3, script4));
    }
}
