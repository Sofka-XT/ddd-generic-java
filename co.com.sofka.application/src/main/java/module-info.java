module co.com.sofka.application {
    exports co.com.sofka.application;
    requires io.github.classgraph;
    requires java.logging;

    requires co.com.sofka.infraestructure;
    requires co.com.sofka.business;
    requires co.com.sofka.domain;
}