module co.com.sofka.application {
    requires java.logging;
    requires io.github.classgraph;

    requires co.com.sofka.infraestructure;
    requires co.com.sofka.business;
    requires co.com.sofka.domain;

    exports co.com.sofka.application;

}