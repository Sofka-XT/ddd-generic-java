[![Build Status](https://travis-ci.com/Sofka-XT/ddd-generic-java.svg?branch=master)](https://travis-ci.com/Sofka-XT/ddd-generic-java)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/bee9638a0f88436f8663fa2067c454a8)](https://www.codacy.com/manual/Sofka-XT/ddd-generic-java?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Sofka-XT/ddd-generic-java&amp;utm_campaign=Badge_Grade)

## Sofka Domain-Driven Design 
Sofka introduce una librería a la comunidad open source para diseñar aplicaciones orientadas al dominio. Esta librería proporciona abstracciones que permiten adoptar el concepto de forma correcta, el estilo que se propone es totalmente discutible para ser mejorado, se espera que al momento de aplicar esta librería se tenga claro los conceptos tácticos de DDD. 

### ¿Qué es DDD?

*DDD (Domain-Driven Desing)* es un método de diseño para descubrir el negocio de dominio de forma clara, apoyado de patrones de diseño y estilos de arquitectura centrados en el dominio. 

### ¿Porqué DDD?

DDD es necesario cuando hablamos de modelamiento del negocio para grandes organizaciones, con el objetivo de diseñar software al rededor del dominio y no en solucionar un problema en particular. Ahora bien DDD no se aplica bien para el caso de un CRUD, dado que no estaría orientado al dominio organizacional, sino a resolver un problema en particular.  

### ¿Qué resuelve la librería?

Desde el punto de vista táctico, se requiere aplicar algunos conceptos fundamentales, para poder aplicar DDD. Todo esos conceptos se tiene en la librería para interfaces o abstracciones, y además de proporcional algunos patrones de diseño que se adaptan a estilos de arquitecturas deferentes. 

#### Patrones 
- Commands y Events 
- Use Case (Request y Response)
- Handlers
- Publisher y Subscriber
- Repository 
- Aggregate
- Event Sourcing

### Adoptar las arquitecturas
- Por eventos (EDA)
- Por commands, events y queries (CQRS)
- Por capas

## Instalación 

Generic dependency for ddd Java - [https://mvnrepository.com/artifact/co.com.sofka/domain-driven-design](https://mvnrepository.com/artifact/co.com.sofka/domain-driven-design)
```
    <dependency>
       <groupId>co.com.sofka</groupId>
       <artifactId>domain-driven-design</artifactId>
       <version>0.6.5</version>
       <type>pom</type>
     </dependency>
```

Si se require dividir los conceptos se puede usar de forma independeiente de la siguiente manera:

```
    <dependency>
       <groupId>co.com.sofka</groupId>
       <artifactId>domain</artifactId>
       <version>0.6.5</version>
     </dependency>
```
```
    <dependency>
       <groupId>co.com.sofka</groupId>
       <artifactId>business</artifactId>
       <version>0.6.5</version>
     </dependency>
```
```
    <dependency>
       <groupId>co.com.sofka</groupId>
       <artifactId>infrastructure</artifactId>
       <version>0.6.5</version>
     </dependency>
```
```
    <dependency>
       <groupId>co.com.sofka</groupId>
       <artifactId>application</artifactId>
       <version>0.6.5</version>
     </dependency>
```
> Aun esta en fase de desarrollo y de validación, trabajar con la versión 0.6 para experimentar y poner en practica los conceptos de DDD, aun no se recomienda llevarlo a producción. 

## Conceptos e implementación 

### Entidades
Una entidad tiene comportamientos, pero no lanza eventos como son los agregados, si tenemos una entidad suelta sin relación con el agregado entonces solo se aplica para cambiar los estados de la misma. Toda entidad depende de una ID, tal cual como el Agregado Root.
```java
public class Student extends Entity<StudentIdentity> {

    protected Name name;
    protected Gender gender;
    protected DateOfBirth dateOfBirth;
    protected Score score;

    protected Student(StudentIdentity studentIdentity, Name name, Gender gender, DateOfBirth dateOfBirth) {
        super(studentIdentity);
        this.name = name;
        this.gender =gender;
        this.dateOfBirth = dateOfBirth;
        this.score = new Score(0);
    }

    private Student(StudentIdentity studentIdentity){
        super(studentIdentity);
    }

    public static Student form(StudentIdentity studentIdentity, Name name, Gender gender, DateOfBirth dateOfBirth){
        var student = new Student(studentIdentity);
        student.name = name;
        student.gender = gender;
        student.dateOfBirth = dateOfBirth;
        return student;
    }

    public String name() {
        return name.value();
    }

    public String gender() {
        return gender.value();
    }

    public String dateOfBirth() {
        return dateOfBirth.value();
    }

    public Score.Values score() {
        return score.value();
    }

    public void updateScore(Score score){
        this.score = score;
    }

    public void updateName(Name name){
        this.name = name;
    }

    public void updateDateOfBirth(DateOfBirth dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    public void updateGender(Gender gender){
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
       return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
```

> Mas adelante estaríamos realizando un tutorial para aplicarlo en una arquitectura distribuida y con CQRS+ES con una arquitectura EDA. 

### Agregado orientado a Eventos
```java
public class Team extends AggregateEvent<TeamIdentity> {
    protected Name name;
    protected Set<Student> students;
    public Team(TeamIdentity identity, Name name) {
        this(identity);
        appendChange(new CreatedTeam(name)).apply();
    }

    private Team(TeamIdentity identity) {
        super(identity);
        subscribe(new TeamBehavior(this));
    }

    public static Team from(TeamIdentity aggregateId, List<DomainEvent> list) {
        Team team = new Team(aggregateId);
        list.forEach(team::applyEvent);
        return team;
    }


    public void addNewStudent(Name name, Gender gender, DateOfBirth dateOfBirth) {
        StudentIdentity studentIdentity = new StudentIdentity();
        appendChange(new AddedStudent(studentIdentity, name, gender, dateOfBirth)).apply();
    }

    public void removeStudent(StudentIdentity studentIdentity) {
        appendChange(new RemovedStudent(studentIdentity)).apply();
    }

    public void updateName(Name newName) {
        appendChange(new UpdatedName(newName)).apply();
    }

    public void updateStudentName(StudentIdentity studentIdentity, Name name) {
        appendChange(new UpdatedStudent(studentIdentity, name)).apply();
    }

    public void applyScoreToStudent(StudentIdentity studentIdentity, Score score) {
        appendChange(new UpdateScoreOfStudent(studentIdentity, score)).apply();
    }

    public Set<Student> students() {
        return students;
    }

    public String name() {
        return name.value();
    }
}
```

### Comportamientos orientado a Eventos
```java
 public class TeamBehavior extends EventBehavior {
        protected TeamBehavior(Team entity) {
            give((CreatedTeam event) -> {
                entity.students = new HashSet<>();
                entity.name = event.getName();
            });

            give((AddedStudent event) -> {
                var student = new Student(
                        event.getStudentIdentity(),
                        event.getName(),
                        event.getGender(),
                        event.getDateOfBirth()
                );
                entity.students.add(student);
            });

            give((RemovedStudent event) -> entity.students
                    .removeIf(e -> e.identity().equals(event.getIdentity())));

            give((UpdatedName event) -> entity.name = event.getNewName());

            give((UpdatedStudent event) -> {
                var studentUpdate = getStudentByIdentity(entity, event.getStudentIdentity());
                studentUpdate.updateName(event.getName());
            });

            give((UpdateScoreOfStudent event) -> {
                var studentUpdate = getStudentByIdentity(entity, event.getStudentIdentity());
                studentUpdate.updateScore(event.getScore());
            });
        }

        private Student getStudentByIdentity(Team entity, Identity identity) {
            return entity.students.stream()
                    .filter(e -> e.identity().equals(identity))
                    .findFirst()
                    .orElseThrow();
        }
   }
```

### Objetos de valor
Un objeto de valor es un objeto inmutable que representa un valor de la entidad. A diferencia de la entidad es que el VO (Value Object) no tiene un identidad que la represente. 
```java
public class DateOfBirth  implements ValueObject<String> {
    private final LocalDate date;
    private final String format;

    public DateOfBirth(int day, int month, int year) {
        try {
            date = LocalDate.of(year, month, day);
            if(date.isAfter(LocalDate.now())){
                throw new IllegalArgumentException("No valid the date of birth");
            }
        } catch (DateTimeException e){
            throw new IllegalArgumentException(e.getMessage());
        }
        format = generateFormat();
    }

    private String generateFormat(){
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    @Override
    public String value() {
        return format;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateOfBirth that = (DateOfBirth) o;
        return Objects.equals(format, that.format);
    }

    @Override
    public int hashCode() {
        return Objects.hash(format);
    }
}
```

### Evento de Dominio
Un evento de dominio esta relacionado con un Agregado Id (que es un VO), este evento es la consecuencia de un comando que ejecuta un comportamiento de un Agregado un Entidad. 
```java
public class AddedStudent extends DomainEvent  {

    private final StudentIdentity studentIdentity;
    private final Name name;
    private final Gender gender;
    private final DateOfBirth dateOfBirth;

    public AddedStudent(StudentIdentity studentIdentity, Name name, Gender gender, DateOfBirth dateOfBirth) {
        super("training.team.addedstudent");
        this.studentIdentity = studentIdentity;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public Name getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public DateOfBirth getDateOfBirth() {
        return dateOfBirth;
    }

    public StudentIdentity getStudentIdentity() {
        return studentIdentity;
    }

}
```

### Comandos
```java
public class CreateTeam implements Command {
    private final Name name;

    public CreateTeam(Name name) {
        this.name = name;
    }

    public Name getName() {
        return name;
    }

}
```

### Aplicar el agregado
Cuando se crear nuevo objeto se instancia el agregado y se le asigna el identificado que relaciona la entidad. Además de los argumentos de creación. Un agregado puede tener múltiples constructores que permiten crear el agregado.  
```java
@CommandHandles
@CommandType(name = "training.team.create",  aggregate = "team")
public class CreateTeamHandle extends UseCaseExecutor {
    private static Logger logger = Logger.getLogger(CreateTeamHandle.class.getName());

    private RequestCommand<CreateTeam> request;

    @Override
    public void accept(Map<String, String> args) {
        logger.info(" ----- Executor CreateTeamHandle ------");
        request = new RequestCommand<>(
                new CreateTeam(new Name(args.get("name")))
        );
    }

    @Override
    public void run() {
        runUseCase(new UseCase<UseCase.RequestValues, ResponseEvents>() {
            @Override
            public void executeUseCase(RequestValues objectInput) {
                var id = new TeamIdentity();
                var command = request.getCommand();
                var team = new Team(id, command.getName());
                emit().onSuccess(new ResponseEvents(team.getUncommittedChanges()));
            }
        }, request);
    }
}
```
Ya para el caso de Actualizar un agregado se reconstruye el agregado según los eventos persistidos, si se tiene un agregado no orientado a eventos se reconstruye con los valores necesarios. Cuando se reconstruya se aplica el comportamiento de la entidad.
```java
@BusinessLogin
public class CreateStudentUseCase extends UseCase<RequestCommand<AddNewStudent>, ResponseEvents> {
    private final DomainEventRepository repository;
    
    public CreateStudentUseCase(DomainEventRepository repository) {
        this.repository = repository;
    }
    @Override
    protected void executeUseCase(RequestCommand<AddNewStudent> request) {
        Team team = reclaimTeam();

        var students = Optional.ofNullable(team.students())
                .orElseThrow(() -> new NecessaryDependency("the students is need for this use case"));

        if(students.size() > 5){
            emit().onError(new NoMoreStudentAllowed());
        } else {
            emit().onSuccess(aNewStudentAddedFor(team));
        }
    }

    private Team reclaimTeam() {
        var aggregateId = request().getCommand().aggregateRootId();
        var events = repository.getEventsBy(aggregateId);
        return Team.from(TeamIdentity.of(aggregateId), events);
    }

    private ResponseEvents aNewStudentAddedFor(Team team) {
        var command = request().getCommand();
        team.addNewStudent(
                command.getName(),
                command.getGender(),
                command.getDateOfBirth()
        );
       return new ResponseEvents(team.getUncommittedChanges());
    }
}
```
Al final de ejecutar el comportamiento se determina si existe cambios dentro del agregado para emitir los eventos a una capa superior. Después se marca los cambios como confirmados. 

> Se puede aplicar aquí casos de uso, comandos y eventos para aplicar el agregado. 

### Queries
```java
@QueryHandles
public class StudentsByTeam extends ViewModelExecutor<ViewModel> {
    @Override
    public ViewModel apply(Map<String, String> params) {
        var query = new ByStudentIdentity();
        query.setAggregateRootId(params.get("teamId"));

        return getDataMapped("teams", StudentViewModel.class)
                .applyAsElement(query);
    }
}
```
## Contribución

1. Realizar el fork
2. Crea tu rama de características: `git checkout -b feature/my-feat`
3. Confirma tus cambios: `git commit -am "Agrega alguna característica"`
4. Empuje a la rama: `git push origin feature/my-feat`
5. Presentar una Pull Requests

> También pueden colaborar creando issue para dar evolución del proyecto.

## License

Sofka Domain-Drive Desing is Open Source software released under the [Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0.html).
