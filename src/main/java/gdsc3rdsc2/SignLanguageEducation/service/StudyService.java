package gdsc3rdsc2.SignLanguageEducation.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import gdsc3rdsc2.SignLanguageEducation.domain.Sentence;
import gdsc3rdsc2.SignLanguageEducation.domain.domainenum.Concern;
import gdsc3rdsc2.SignLanguageEducation.domain.projection.ScriptProjection;
import gdsc3rdsc2.SignLanguageEducation.repository.ScriptRepository;
import gdsc3rdsc2.SignLanguageEducation.repository.SentenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpRange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService {
    private final SentenceRepository sentenceRepository;
    private final ScriptRepository scriptRepository;
    private static ProcessBuilder processBuilder;

    final long CHUNK_SIZE = 1000000L;

    public Map<String, String> selectSentence(Long id) {
        Map<String,String> a = new HashMap<>();

        sentenceRepository.findById(id).ifPresent(sentence -> {
            a.putAll(analyzeSentence(sentence.getSentence()));
        });

        return a;
    }

    public Map<String, String> analyzeSentence(String sentence) {
        //use ai
        Map<String, String> map = new HashMap<>();
        //단어와 비디오 아이디 매핑
        processBuilder = new ProcessBuilder("python","src/main/java/gdsc3rdsc2/SignLanguageEducation/AI/main.py",sentence);
        processBuilder.redirectErrorStream(true);
        try {
            Process process = processBuilder.start();

            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            ObjectMapper mapper = new ObjectMapper();

            while ((line = br.readLine()) != null) {
                line = line.replaceAll("[\\[\\]']", "");
                List<String> words = Arrays.asList(line.split(",\\s*"));
                for(int i=0;i<words.size();i+=2){
                    map.put(words.get(i), words.get(i+1));
                }
            }

            //process.destroy();
            int exitCode = process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println(e.getMessage());
        }
        return map;
    }

    public ResourceRegion getVideo(String videoId, Optional<HttpRange> range){
        String url = "file:///C:/Users/gyu27/IdeaProjects/3rd-sc-2-SignLanguageEducation-BE/video" + "/NIA_SL_" + videoId + "_F.mp4";

        UrlResource videoResource = null;
        long contentLength = 0;
        try {
            videoResource = new UrlResource(url);
            contentLength = videoResource.contentLength();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ResourceRegion resourceRegion;

        if(range.isPresent()){
            HttpRange httpRange = range.get();
            long start = httpRange.getRangeStart(contentLength);
            long end = httpRange.getRangeEnd(contentLength);
            long rangeLength = Math.min(CHUNK_SIZE, end - start + 1);

            resourceRegion = new ResourceRegion(videoResource, start, rangeLength);
            return resourceRegion;
        }

        long rangeLength = Math.min(CHUNK_SIZE, contentLength);
        resourceRegion = new ResourceRegion(videoResource, 0, rangeLength);
        return resourceRegion;
    }

    public Map<Long, String> findSentence(String concern) {
        List<Sentence> list = sentenceRepository.findByConcern(Concern.valueOf(concern));
        return list.stream().collect(HashMap::new, (m, v) -> m.put(v.getId(), v.getSentence()), HashMap::putAll);
    }

    public Map<Long, String> getScriptList() {
        List<ScriptProjection> list = scriptRepository.findAllBy();
        return list.stream().collect(HashMap::new, (m, v) -> m.put(v.getId(), v.getTitle()), HashMap::putAll);
    }

    public List<String> getScript(Long scriptId) {
        return scriptRepository.findById(scriptId).get().getSentences();
    }

    public Map<String, String> getScriptToVideo(Long scriptId, Long sentenceId) {
        Map<String, String> map = new HashMap<>();
        return analyzeSentence(getScript(scriptId).get(Math.toIntExact(sentenceId)));
    }
}
