package com.sparta.project;

import com.sparta.project.Statistics.VideoStatistics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class VideoStatisticsTest {

    private VideoStatistics videoStatistics;

    @BeforeEach
    public void setUp() {
        // 기본 생성자 사용 후 개별 필드 설정
        videoStatistics = new VideoStatistics();
        videoStatistics.setStartDate(LocalDate.of(2024, 8, 1));
        videoStatistics.setEndDate(LocalDate.of(2024, 8, 7));
        videoStatistics.setPeriodType(VideoStatistics.PeriodType.WEEKLY);
        videoStatistics.setViews(100L);
        videoStatistics.setWatchTime(200L);
    }

    @Test
    public void testGetStartDate() {
        assertEquals(LocalDate.of(2024, 8, 1), videoStatistics.getStartDate());
    }

    @Test
    public void testGetEndDate() {
        assertEquals(LocalDate.of(2024, 8, 7), videoStatistics.getEndDate());
    }

    @Test
    public void testGetPeriodType() {
        assertEquals(VideoStatistics.PeriodType.WEEKLY, videoStatistics.getPeriodType());
    }

    @Test
    public void testGetViews() {
        assertEquals(100L, videoStatistics.getViews());
    }

    @Test
    public void testGetWatchTime() {
        assertEquals(200L, videoStatistics.getWatchTime());
    }

    @Test
    public void testSetStartDate() {
        videoStatistics.setStartDate(LocalDate.of(2024, 8, 2));
        assertEquals(LocalDate.of(2024, 8, 2), videoStatistics.getStartDate());
    }

    @Test
    public void testSetEndDate() {
        videoStatistics.setEndDate(LocalDate.of(2024, 8, 8));
        assertEquals(LocalDate.of(2024, 8, 8), videoStatistics.getEndDate());
    }

    @Test
    public void testSetPeriodType() {
        videoStatistics.setPeriodType(VideoStatistics.PeriodType.DAILY);
        assertEquals(VideoStatistics.PeriodType.DAILY, videoStatistics.getPeriodType());
    }

    @Test
    public void testSetViews() {
        videoStatistics.setViews(150L);
        assertEquals(150L, videoStatistics.getViews());
    }

    @Test
    public void testSetWatchTime() {
        videoStatistics.setWatchTime(250L);
        assertEquals(250L, videoStatistics.getWatchTime());
    }
}
