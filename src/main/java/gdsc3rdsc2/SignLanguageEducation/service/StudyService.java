package gdsc3rdsc2.SignLanguageEducation.service;

import gdsc3rdsc2.SignLanguageEducation.domain.Sentence;
import gdsc3rdsc2.SignLanguageEducation.domain.Video;
import gdsc3rdsc2.SignLanguageEducation.domain.domainenum.Concern;
import gdsc3rdsc2.SignLanguageEducation.domain.projection.ScriptProjection;
import gdsc3rdsc2.SignLanguageEducation.repository.ScriptRepository;
import gdsc3rdsc2.SignLanguageEducation.repository.SentenceRepository;
import gdsc3rdsc2.SignLanguageEducation.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpRange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StudyService {
    private final VideoRepository videoRepository;
    private final SentenceRepository sentenceRepository;
    private final ScriptRepository scriptRepository;

    final long CHUNK_SIZE = 1000000L;

    public Map<String, Long> selectSentence(String sentence) {
        //use ai
        Map<String, Long> map = new HashMap<>();
        //단어와 비디오 아이디 매핑

        return map;
    }

    public ResourceRegion getVideo(Long videoId, Optional<HttpRange> range){
        Video video = videoRepository.findById(videoId).get();

        UrlResource videoResource = null;
        long contentLength = 0;
        try {
            videoResource = new UrlResource(video.getUrl());
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
}
