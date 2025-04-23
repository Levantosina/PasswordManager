package com.password.auth;

public record AuthenticationRequest(
        String userName,
        String password)
{ }