package com.example.practic.observer;

public interface Observable{
	void addObserver(Observer e);

	void removeObserver(Observer e);

	void notifyObservers();
}