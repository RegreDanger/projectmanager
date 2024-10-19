module ProjectManager {
	exports ui;
	exports domain.utils.enums;
	exports domain;
	exports domain.utils.interfaces;
	exports com.regre;
	
	requires java.sql;
	requires transitive javafx.base;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
}