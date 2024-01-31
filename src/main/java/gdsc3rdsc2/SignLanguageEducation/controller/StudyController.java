package gdsc3rdsc2.SignLanguageEducation.controller;

import gdsc3rdsc2.SignLanguageEducation.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;

    @PostMapping("/sentence")
    public Map<String, Long> choiceSentence(@RequestBody String sentence){
        return studyService.choiceSentence(sentence);
    }

    @GetMapping("/video/{videoId}")
    public ResponseEntity<ResourceRegion> getVideo(@PathVariable Long videoId, @RequestHeader HttpHeaders headers){
        Optional<HttpRange> range = headers.getRange().stream().findFirst();
        ResourceRegion resourceRegion = studyService.getVideo(videoId, range);

        return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
                .contentType(MediaTypeFactory.getMediaType(resourceRegion.getResource()).orElse(MediaType.APPLICATION_OCTET_STREAM))
                .body(resourceRegion);
    }

}
