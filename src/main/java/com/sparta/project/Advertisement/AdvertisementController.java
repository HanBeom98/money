package com.sparta.project.Advertisement;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

/**
 * AdvertisementController는 광고와 관련된 API 요청을 처리합니다.
 * 광고 생성, 조회, 업데이트, 삭제 및 조회수 증가 등의 엔드포인트를 제공합니다.
 */
@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    // 모든 광고를 조회하여 반환
    @GetMapping
    public ResponseEntity<List<AdvertisementDto>> getAllAdvertisements() {
        List<AdvertisementDto> advertisements = advertisementService.findAllAdvertisements();
        return ResponseEntity.ok(advertisements);
    }

    // 광고를 생성하는 엔드포인트
    @PostMapping
    public ResponseEntity<AdvertisementDto> createAdvertisement(@Valid @RequestBody AdvertisementDto advertisementDto) {
        AdvertisementDto createdAd = advertisementService.saveAdvertisement(advertisementDto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdAd.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdAd);
    }

    // 광고를 ID로 조회하여 반환
    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementDto> getAdvertisementById(@PathVariable Long id) {
        AdvertisementDto advertisementDto = advertisementService.findAdvertisementById(id);
        return ResponseEntity.ok(advertisementDto);
    }

    // 광고의 조회수를 증가시키는 엔드포인트
    @PatchMapping("/{id}/view")
    public ResponseEntity<Void> incrementAdViewCount(@PathVariable Long id) {
        advertisementService.incrementAdViewCount(id);
        return ResponseEntity.noContent().build();
    }

    // 광고를 업데이트하는 엔드포인트
    @PutMapping("/{id}")
    public ResponseEntity<AdvertisementDto> updateAdvertisement(@PathVariable Long id, @Valid @RequestBody AdvertisementDto advertisementDto) {
        advertisementDto.setId(id);
        AdvertisementDto updatedAd = advertisementService.saveAdvertisement(advertisementDto);
        return ResponseEntity.ok(updatedAd);
    }

    // 광고를 삭제하는 엔드포인트
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable Long id) {
        advertisementService.deleteAdvertisement(id);
        return ResponseEntity.noContent().build();
    }
}
