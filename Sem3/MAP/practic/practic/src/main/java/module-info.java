module com.example.practic {
	requires javafx.controls;
	requires javafx.fxml;

	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires org.kordamp.bootstrapfx.core;
	requires java.sql;

	opens com.example.practic to javafx.fxml;
	exports com.example.practic;

	opens com.example.practic.domain to javafx.fxml;
	exports com.example.practic.domain;

	opens com.example.practic.controller to javafx.fxml;
	exports com.example.practic.controller;

	opens com.example.practic.service to javafx.fxml;
	exports com.example.practic.service;
}