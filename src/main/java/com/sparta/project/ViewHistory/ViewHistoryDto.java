package com.sparta.project.ViewHistory;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ViewHistoryDto {
    private Long id;
    private Long videoId;
    private Long userId;
    private Long watchTime;
    private Long lastWatchedTime;
    private LocalDateTime viewDate;  // viewDate 필드 추가
}
