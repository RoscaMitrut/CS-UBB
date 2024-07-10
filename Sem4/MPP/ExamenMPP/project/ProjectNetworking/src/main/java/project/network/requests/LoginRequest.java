package project.network.requests;

import project.model.User;

public record LoginRequest(User user) implements Request {
}
