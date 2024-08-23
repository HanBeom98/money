package com.sparta.project.ViewHistory;// ViewHistoryController.java

import com.sparta.project.ViewHistory.ViewHistory;
import com.sparta.project.ViewHistory.ViewHistoryDto;
import com.sparta.project.ViewHistory.ViewHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/viewhistories")
public class ViewHistoryController {

    @Autowired
    private ViewHistoryService viewHistoryService;

    @GetMapping
    public ResponseEntity<List<ViewHistoryDto>> getAllViewHistories() {
        List<ViewHistoryDto> viewHistories = viewHistoryService.findAllViewHistories();
        return ResponseEntity.ok(viewHistories);
    }

    @PostMapping
    public ResponseEntity<ViewHistoryDto> createViewHistory(@RequestBody ViewHistoryDto viewHistoryDto) {
        ViewHistoryDto createdHistory = viewHistoryService.saveViewHistory(viewHistoryDto);
        return ResponseEntity.ok(createdHistory);
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