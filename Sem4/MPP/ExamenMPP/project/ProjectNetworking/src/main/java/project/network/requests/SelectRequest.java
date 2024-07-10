package project.network.requests;

import project.model.User;

public record SelectRequest(User player, String input) implements Request {
}
