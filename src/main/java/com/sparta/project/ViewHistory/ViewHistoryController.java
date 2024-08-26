package com.sparta.project.ViewHistory;

import com.sparta.project.User.User;
import com.sparta.project.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viewhistories")
public class ViewHistoryController {

    @Autowired
    private ViewHistoryService viewHistoryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<ViewHistoryDto>> getAllViewHistories() {
        List<ViewHistoryDto> viewHistories = viewHistoryService.findAllViewHistories();
        return ResponseEntity.ok(viewHistories);
    }

    @PostMapping("/{videoId}/track")
    public ResponseEntity<Void> trackVideoView(@PathVariable Long videoId, @RequestParam Long watchTime) {
        User currentUser = userService.getCurrentUser(); // 현재 로그인한 사용자 가져오기
        viewHistoryService.trackVideoView(videoId, currentUser, watchTime);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ViewHistoryDto> getViewHistoryById(@PathVariable Long id) {
        ViewHistoryDto viewHistoryDto = viewHistoryService.entityToDto(viewHistoryService.findViewHistoryById(id));
        return ResponseEntity.ok(viewHistoryDto);
    }

    @PutMapping("/{id}/last-watched")
    public ResponseEntity<ViewHistoryDto> updateLastWatchedTime(@PathVariable Long id, @RequestBody Long lastWatchedTime) {
        ViewHistory viewHistory = viewHistoryService.findViewHistoryById(id);
        ViewHistoryDto updatedHistory = viewHistoryService.updateLastWatchedTime(viewHistory, lastWatchedTime);
        return ResponseEntity.ok(updatedHistory);
    }
}
