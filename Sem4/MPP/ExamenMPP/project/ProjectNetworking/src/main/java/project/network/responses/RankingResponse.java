package project.network.responses;


import project.model.Joc;

public record RankingResponse(Joc[] games) implements Response {
}
