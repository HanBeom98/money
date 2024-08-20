package com.sparta.project.ViewHistory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viewhistories")
public class ViewHistoryController {

    @Autowired
    private ViewHistoryService viewHistoryService;

    @GetMapping
    public List<ViewHistory> getAllViewHistories() {
        return viewHistoryService.findAllViewHistories();
    }

    @PostMapping
    public ViewHistory createViewHistory(@RequestBody ViewHistory viewHistory) {
        return viewHistoryService.saveViewHistory(viewHistory);
    }

    @GetMapping("/{id}")
    public ViewHistory getViewHistoryById(@PathVariable Long id) {
        return viewHistoryService.findViewHistoryById(id);
    }

    // 비디오 재생 시점 업데이트 API
    @PutMapping("/{id}/last-watched")
    public ViewHistory updateLastWatchedTime(@PathVariable Long id, @RequestBody Long lastWatchedTime) {
        ViewHistory viewHistory = viewHistoryService.findViewHistoryById(id);
        return viewHistoryService.updateLastWatchedTime(viewHistory, lastWatchedTime);
    }
}
