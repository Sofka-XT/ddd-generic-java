module co.com.sofka.infraestructure {
    exports co.com.sofka.infraestructure;
    exports co.com.sofka.infraestructure.handle;
    exports co.com.sofka.infraestructure.repository;
    exports co.com.sofka.infraestructure.asyn;

    requires co.com.sofka.domain;
    requires co.com.sofka.business;
    requires com.google.gson;

}