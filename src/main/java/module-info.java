module org.bookhub.bookhub {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;

    opens org.bookhub to javafx.fxml;
    opens org.bookhub.auth to javafx.fxml;
    opens org.bookhub.controller to javafx.fxml;
    opens org.bookhub.controller.home to javafx.fxml;
    opens org.bookhub.controller.libro to javafx.fxml;
    opens org.bookhub.controller.prestamos to javafx.fxml;
    opens org.bookhub.controller.autores to javafx.fxml;
    opens org.bookhub.controller.usuarios to javafx.fxml;
    opens org.bookhub.models to javafx.base;

    exports org.bookhub;
    exports org.bookhub.auth;
    exports org.bookhub.controller;
    exports org.bookhub.controller.home;
    exports org.bookhub.controller.libro;
    exports org.bookhub.controller.prestamos;
    exports org.bookhub.controller.autores;
    exports org.bookhub.controller.usuarios;
}
