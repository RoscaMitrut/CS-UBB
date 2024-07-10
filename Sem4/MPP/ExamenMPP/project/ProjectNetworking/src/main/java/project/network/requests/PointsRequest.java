package project.network.requests;

import project.model.User;

public record PointsRequest(User player) implements Request {
}
