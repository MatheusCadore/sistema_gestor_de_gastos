module com.gestorgastos.sistema_gestor_gastos {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    opens com.gestorgastos.sistema_gestor_gastos to javafx.fxml;
    exports com.gestorgastos.sistema_gestor_gastos;
}