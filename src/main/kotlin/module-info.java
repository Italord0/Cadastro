module com.github.italord0.cadastro {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires kotlin.stdlib;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.github.italord0.cadastro to javafx.fxml;
    exports com.github.italord0.cadastro;
    exports com.github.italord0.cadastro.controller;
    exports com.github.italord0.cadastro.model;
}