package project.network.requests;


import project.model.User;

public record GameRequest(User player) implements Request {
}
