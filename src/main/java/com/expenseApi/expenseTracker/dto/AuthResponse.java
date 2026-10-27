package com.expenseApi.expenseTracker.dto;

public record AuthResponse(

    Integer userId,

    String name,

    String email,

    String token,

    String tokenType,

    String message

) {
}
