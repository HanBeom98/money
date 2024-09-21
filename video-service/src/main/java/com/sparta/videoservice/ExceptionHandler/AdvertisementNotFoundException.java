package com.sparta.videoservice.ExceptionHandler;

public class AdvertisementNotFoundException extends RuntimeException {

    public AdvertisementNotFoundException(String message) {
        super(message);
    }
}
