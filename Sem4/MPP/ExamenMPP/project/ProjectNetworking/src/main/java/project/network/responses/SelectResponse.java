package project.network.responses;

import project.services.GameStatus;
public record SelectResponse(GameStatus status) implements Response {
}
