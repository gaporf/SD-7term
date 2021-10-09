package ru.ifmo.rain.akimov.main.vk;

public class VkClientException extends RuntimeException {
    VkClientException(final String errorMessage) {
        super(errorMessage);
    }

    VkClientException(final String errorMessage, final Exception e) {
        super(errorMessage, e);
    }
}
