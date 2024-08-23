package com.sparta.project.Advertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

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
        return ResponseEntity.ok(createdAd);
    }

    // 광고를 ID로 조회하여 반환
    @GetMapping("/{id}")
    public ResponseEntity<AdvertisementDto> getAdvertisementById(@PathVariable Long id) {
        AdvertisementDto advertisementDto = advertisementService.findAdvertisementById(id);
        return ResponseEntity.ok(advertisementDto);
    }

    // 광고의 조회수를 증가시키는 엔드포인트 (POST에서 PATCH로 변경)
    @PatchMapping("/{id}/view")
    public ResponseEntity<Void> incrementAdViewCount(@PathVariable Long id) {
        advertisementService.incrementAdViewCount(id);
        return ResponseEntity.noContent().build();
    }

    // 광고를 업데이트하는 엔드포인트 추가
    @PutMapping("/{id}")
    public ResponseEntity<AdvertisementDto> updateAdvertisement(@PathVariable Long id, @Valid @RequestBody AdvertisementDto advertisementDto) {
        advertisementDto.setId(id);
        AdvertisementDto updatedAd = advertisementService.saveAdvertisement(advertisementDto);
        return ResponseEntity.ok(updatedAd);
    }
}
