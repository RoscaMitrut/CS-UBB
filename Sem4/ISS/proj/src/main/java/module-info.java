module com.proj {
	requires javafx.controls;
	requires javafx.fxml;

	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires org.kordamp.bootstrapfx.core;
	requires java.sql;

	opens com.proj to javafx.fxml;
	exports com.proj;

	opens com.proj.domain to javafx.fxml;
	exports com.proj.domain;

	opens com.proj.controller to javafx.fxml;
	exports com.proj.controller;

	opens com.proj.service to javafx.fxml;
	exports com.proj.service;
}