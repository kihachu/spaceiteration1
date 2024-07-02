module com.kihachu.spaceiteration1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;
    requires jim3dsModelImporterJFX;

    opens com.kihachu.spaceiteration1 to javafx.fxml;
    exports com.kihachu.spaceiteration1;
}