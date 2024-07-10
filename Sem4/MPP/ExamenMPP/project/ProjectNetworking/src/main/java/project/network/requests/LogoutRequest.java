package project.network.requests;

import project.model.User;

public record LogoutRequest(User user) implements Request{}

