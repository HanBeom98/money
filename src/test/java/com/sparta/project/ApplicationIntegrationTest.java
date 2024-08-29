package com.sparta.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.sparta.project.Video.VideoDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;  // WebFlux 환경에서 API 요청을 테스트하는 도구

    @Test
    public void testGetAllVideos() {
        webTestClient.get().uri("/api/videos")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].title").isNotEmpty();  // 응답의 첫 번째 비디오의 제목이 비어있지 않은지 확인
    }

    @Test
    public void testCreateVideo() {
        VideoDto videoDto = new VideoDto();
        videoDto.setTitle("Test Video");
        videoDto.setDescription("Test Description");

        webTestClient.post().uri("/api/videos")
                .bodyValue(videoDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isNumber()  // 생성된 비디오의 ID가 숫자인지 확인
                .jsonPath("$.title").isEqualTo("Test Video");  // 생성된 비디오의 제목이 "Test Video"인지 확인
    }

    @Test
    public void testIncrementVideoViewCount() {
        // 먼저 비디오를 생성합니다.
        VideoDto videoDto = new VideoDto();
        videoDto.setTitle("Test Video");
        videoDto.setDescription("Test Description");

        Long videoId = webTestClient.post().uri("/api/videos")
                .bodyValue(videoDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(VideoDto.class)
                .returnResult()
                .getResponseBody()
                .getId();

        // 조회수를 증가시키는 엔드포인트를 테스트합니다.
        webTestClient.patch().uri("/api/videos/{id}/view", videoId)
                .exchange()
                .expectStatus().isNoContent();

        // 다시 비디오를 조회하여 조회수가 증가했는지 확인합니다.
        webTestClient.get().uri("/api/videos/{id}", videoId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.views").isEqualTo(1);  // 조회수가 1인지 확인
    }

    @Test
    public void testDeleteVideo() {
        // 비디오를 먼저 생성합니다.
        VideoDto videoDto = new VideoDto();
        videoDto.setTitle("Video to Delete");
        videoDto.setDescription("Test Description");

        Long videoId = webTestClient.post().uri("/api/videos")
                .bodyValue(videoDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(VideoDto.class)
                .returnResult()
                .getResponseBody()
                .getId();

        // 생성된 비디오를 삭제합니다.
        webTestClient.delete().uri("/api/videos/{id}", videoId)
                .exchange()
                .expectStatus().isNoContent();

        // 삭제 후, 해당 비디오가 없는지 확인합니다.
        webTestClient.get().uri("/api/videos/{id}", videoId)
                .exchange()
                .expectStatus().isNotFound();  // 삭제된 비디오는 찾을 수 없어야 합니다.
    }
}
