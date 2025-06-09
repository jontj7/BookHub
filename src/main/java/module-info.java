module org.bookhub.bookhub {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens org.bookhub to javafx.fxml;
    opens org.bookhub.controller to javafx.fxml;

    exports org.bookhub;
    exports org.bookhub.controller; // ✅ Esta línea permite que otros módulos/clases accedan al controlador
}
