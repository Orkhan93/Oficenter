package az.digitalhands.oficenter.controller;

import az.digitalhands.oficenter.request.CollectionRequest;
import az.digitalhands.oficenter.response.CollectionResponse;
import az.digitalhands.oficenter.response.CollectionResponseList;
import az.digitalhands.oficenter.service.CollectionService;
import az.digitalhands.oficenter.wrapper.CollectionWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collections")
@RequiredArgsConstructor
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<CollectionResponse> createCollection(@RequestBody CollectionRequest collectionRequest,
                                                               @PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(collectionService.createCollection(collectionRequest, userId));
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<CollectionResponse> updateCollection(@RequestBody CollectionRequest collectionRequest,
                                                               @PathVariable(name = "userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(collectionService.updateCollection(collectionRequest, userId));
    }

    @GetMapping("/getAll")
    public ResponseEntity<CollectionResponseList> getAllCollections() {
        return ResponseEntity.status(HttpStatus.OK).body(collectionService.getAllCollection());
    }

    @GetMapping("/get/{collectionId}")
    public ResponseEntity<CollectionResponse> getCollectionById(@PathVariable(name = "collectionId") Long collectionId) {
        return ResponseEntity.status(HttpStatus.OK).body(collectionService.getCollectionById(collectionId));
    }

    @DeleteMapping("/{userId}/delete/{collectionId}")
    public void deleteCollection(@PathVariable(name = "userId") Long userId,
                                 @PathVariable(name = "collectionId") Long collectionId) {
        collectionService.deleteById(userId, collectionId);
    }

    @DeleteMapping("/{userId}/deleteAll")
    public void deleteAllCollections(@PathVariable(name = "userId") Long userId) {
        collectionService.deleteAllCollections(userId);
    }

}