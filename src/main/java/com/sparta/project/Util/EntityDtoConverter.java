package com.sparta.project.Util;

import com.sparta.project.Advertisement.Advertisement;
import com.sparta.project.Advertisement.AdvertisementDto;
import com.sparta.project.User.User;
import com.sparta.project.User.UserDto;
import com.sparta.project.User.UserType;
import com.sparta.project.Video.Video;
import com.sparta.project.Video.VideoDto;
import com.sparta.project.ViewHistory.ViewHistory;
import com.sparta.project.ViewHistory.ViewHistoryDto;

public class EntityDtoConverter {

    public static AdvertisementDto convertToDto(Advertisement advertisement) {
        AdvertisementDto dto = new AdvertisementDto();
        dto.setId(advertisement.getId());
        dto.setAdContent(advertisement.getAdContent());
        dto.setViewCount(advertisement.getViewCount());
        dto.setVideoId(advertisement.getVideo().getId());
        return dto;
    }

    public static Advertisement convertToEntity(AdvertisementDto dto, Video video) {
        Advertisement advertisement = new Advertisement();
        advertisement.setId(dto.getId());
        advertisement.setAdContent(dto.getAdContent());
        advertisement.setViewCount(dto.getViewCount() != null ? dto.getViewCount() : 0L);
        advertisement.setVideo(video);
        return advertisement;
    }

    public static UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setProfilePicture(user.getProfilePicture());
        dto.setRole(user.getRole().name());
        dto.setUserType(user.getUserType().name());
        return dto;
    }

    public static User convertToEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setProfilePicture(dto.getProfilePicture());

        // Role이 null일 경우 기본값으로 Role.USER 설정
        if (dto.getRole() != null) {
            user.setRole(User.Role.valueOf(dto.getRole()));
        } else {
            user.setRole(User.Role.USER); // 기본값 설정
        }

        user.setUserType(UserType.valueOf(dto.getUserType()));
        if (dto.getPassword() != null) {
            user.setPassword(dto.getPassword());
        }
        return user;
    }

    public static VideoDto convertToDto(Video video) {
        VideoDto dto = new VideoDto();
        dto.setId(video.getId());
        dto.setTitle(video.getTitle());
        dto.setDescription(video.getDescription());
        dto.setUrl(video.getUrl());
        dto.setViews(video.getViews() != null ? video.getViews() : 0L);
        return dto;
    }

    public static Video convertToEntity(VideoDto dto, User uploader) {
        Video video = new Video();
        video.setId(dto.getId());
        video.setTitle(dto.getTitle());
        video.setDescription(dto.getDescription());
        video.setUrl(dto.getUrl());
        video.setViews(dto.getViews() != null ? dto.getViews() : 0L);
        video.setUploader(uploader);
        return video;
    }

    public static ViewHistoryDto convertToDto(ViewHistory viewHistory) {
        ViewHistoryDto dto = new ViewHistoryDto();
        dto.setId(viewHistory.getId());
        dto.setVideoId(viewHistory.getVideo().getId());
        dto.setUserId(viewHistory.getUser().getId());
        dto.setWatchTime(viewHistory.getWatchTime());
        dto.setLastWatchedTime(viewHistory.getLastWatchedTime());
        dto.setViewDate(viewHistory.getViewDate());
        return dto;
    }

    public static ViewHistory convertToEntity(ViewHistoryDto dto, Video video, User user) {
        ViewHistory viewHistory = new ViewHistory();
        viewHistory.setId(dto.getId());
        viewHistory.setVideo(video);
        viewHistory.setUser(user);
        viewHistory.setWatchTime(dto.getWatchTime());
        viewHistory.setLastWatchedTime(dto.getLastWatchedTime());
        viewHistory.setViewDate(dto.getViewDate());
        return viewHistory;
    }
}
