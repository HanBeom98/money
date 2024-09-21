package com.sparta.videoservice.repository;

import com.sparta.videoservice.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true) // 읽기 전용 트랜잭션
public interface AdvertisementReadRepository extends JpaRepository<Advertisement, Long> {
    // 질의(읽기) 작업만 처리
}
