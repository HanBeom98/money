package com.sparta.project.ViewHistory;

import com.sparta.project.User.UserService;
import com.sparta.project.ViewHistory.ViewHistoryDto;
import com.sparta.project.ViewHistory.ViewHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/viewhistories")
public class ViewHistoryController {

    @Autowired
    private ViewHistoryService viewHistoryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public Mono<ResponseEntity<List<ViewHistoryDto>>> getAllViewHistories() {
        return viewHistoryService.findAllViewHistories()
                .collectList()
                .map(ResponseEntity::ok);
    }

    @PostMapping("/{videoId}/track")
    public Mono<ResponseEntity<Void>> trackVideoView(@PathVariable Long videoId,
                                                     @RequestParam Long watchTime,
                                                     ServerWebExchange exchange) { // ServerWebExchange를 추가
        return userService.getCurrentUser()
                .flatMap(currentUser -> viewHistoryService.trackVideoView(videoId, currentUser, watchTime, exchange))
                .then(Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ViewHistoryDto>> getViewHistoryById(@PathVariable Long id) {
        return viewHistoryService.findViewHistoryById(id)
                .map(viewHistoryService::entityToDto)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}/last-watched")
    public Mono<ResponseEntity<ViewHistoryDto>> updateLastWatchedTime(@PathVariable Long id, @RequestBody Long lastWatchedTime) {
        return viewHistoryService.findViewHistoryById(id)
                .flatMap(viewHistory -> viewHistoryService.updateLastWatchedTime(viewHistory, lastWatchedTime))
                .map(ResponseEntity::ok);
    }
}
