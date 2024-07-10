package project.model;

public interface Entity<ID> {
	void setId(ID id);
	ID getId();
}
