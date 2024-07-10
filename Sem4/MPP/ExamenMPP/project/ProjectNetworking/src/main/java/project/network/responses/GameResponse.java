package project.network.responses;

import project.model.Joc;

public record GameResponse(Joc game) implements Response {
}
