package com.proj.domain;

public interface Entity<ID> {
	void setId(ID id);
	ID getId();
}
