package com.sparta.videoservice.controller;

import com.sparta.videoservice.model.Advertisement;
import com.sparta.videoservice.service.AdvertisementCommandService;
import com.sparta.videoservice.service.AdvertisementQueryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ads")
public class AdvertisementController {

    private final AdvertisementCommandService advertisementCommandService;
    private final AdvertisementQueryService advertisementQueryService;

    public AdvertisementController(AdvertisementCommandService advertisementCommandService, AdvertisementQueryService advertisementQueryService) {
        this.advertisementCommandService = advertisementCommandService;
        this.advertisementQueryService = advertisementQueryService;
    }

    // 조회 작업: 광고 목록 가져오기
    @GetMapping
    public List<Advertisement> getAllAds() {
        return advertisementQueryService.getAllAds();
    }

    // 조회 작업: 광고 단건 조회
    @GetMapping("/{adId}")
    public Advertisement getAd(@PathVariable Long adId) {
        return advertisementQueryService.getAdById(adId);
    }

    // 쓰기 작업: 광고 생성
    @PostMapping
    public Advertisement createAd(@RequestBody Advertisement ad) {
        return advertisementCommandService.createAd(ad);
    }

    // 쓰기 작업: 광고 삭제
    @DeleteMapping("/{adId}")
    public String deleteAd(@PathVariable Long adId) {
        advertisementCommandService.deleteAd(adId);
        return "Advertisement deleted successfully";
    }

    // 쓰기 작업: 광고 업데이트
    @PutMapping("/{adId}")
    public Advertisement updateAd(@PathVariable Long adId, @RequestBody Advertisement ad) {
        ad.setId(adId); // 광고 ID 설정
        return advertisementCommandService.updateAd(ad);
    }
}
