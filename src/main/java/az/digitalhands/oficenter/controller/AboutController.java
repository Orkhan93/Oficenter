package az.digitalhands.oficenter.controller;

import az.digitalhands.oficenter.request.AboutRequest;
import az.digitalhands.oficenter.response.AboutResponse;
import az.digitalhands.oficenter.service.AboutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/about")
public class AboutController {
    private final AboutService aboutService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<AboutResponse> createAbout(@RequestBody AboutRequest aboutRequest,
                                                     @PathVariable Long userId) {
        return aboutService.add(aboutRequest, userId);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<AboutResponse> updateAbout(@RequestBody AboutRequest aboutRequest,
                                                     @PathVariable Long userId) {
        return aboutService.update(aboutRequest, userId);
    }

    @DeleteMapping("/{userId}/delete/{aboutId}")
    public void deleteAbout(@PathVariable Long userId,
                            @PathVariable Long aboutId) {
        aboutService.deleteById(userId, aboutId);
    }

}
