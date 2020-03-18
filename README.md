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
       <version>0.2.*</version>
     </dependency>
```
> Aun esta en fase de desarrollo y de validación, trabajar con la versión 0.2 para experimentar y poner en practica los conceptos de DDD, aun no se recomienda llevarlo a producción. 

## Conceptos e implementación 

### Agregado
El agregado role trabaja con la entidad de usuarios, el comportamiento del rol sería asignar usuarios a un rol. Cuando se crea el objeto Role se crear con el nombre y el Id del rol, la lista de usuario se crea de forma vacía. 
```java
public class Role extends AggregateRoot<RoleId> {  
    private RoleName roleName;  
    private Set<User> users;  
    
    public Role(RoleId entityId, RoleName roleName) {  
        super(entityId);  
        this.roleName = roleName;  
        this.users = new HashSet<>();  
    }  
    public void assignUser(User user){  
        users().add(user);  
    }  
    private RoleName roleName(){  
        return roleName;  
    }  
    public Set<User> users(){  
        return users;  
    }  
    public static Role from(RoleId entityId, RoleName roleName, Set<User> users){  
        var role = new Role(entityId, roleName);  
        role.users = users;  
        role.roleName = roleName;  
        return role;  
  }  
}
```
Vamos a implementar un Agregado usando eventos de Dominio orientados a comportamientos.
### Agregado orientado a Eventos
```java
public class User extends AggregateEvent<UserId> {  
    protected UserName userName;  
    protected UserPassword userPassword;  
    
    public User(UserId userId, UserName aUserName, UserPassword aUserPassword) {  
        this(userId);//initialize object base  
        var userPassword = Objects.requireNonNull(aUserPassword);  
        var userName = Objects.requireNonNull(aUserName);  
        appendChange(new UserCreated(userId, userName, userPassword)).apply();  
    }  
    private User(UserId userId) {  
        super(userId);  
        registerEntityBehavior(new UserBehaviors(this));  
    }  
    public static User from(UserId userId, List<DomainEvent> eventList) {  
        User user = new User(userId);  
        eventList.forEach(user::applyEvent);  
     return user;  
    }  
    public void updateUserPassword(UserPassword aUserPassword) {  
        var userPassword = Objects.requireNonNull(aUserPassword);  
        appendChange(new UserPasswordUpdated(this.entityId, userPassword)).apply();  
     }  
} 
```
### Comportamientos orientado a Eventos
```java
public final class UserBehaviors extends EventBehaviors<User> {  
    {  
        add((UserPasswordUpdated event) -> {//change status  
           if (entity.userPassword.equals(event.userPassword)) {  
                throw new IllegalArgumentException("The password are equal");  
            }  
            entity.userPassword = event.getUserPassword();  
         });  
  
       add((UserCreated domainEvent) -> { //change status  
         if (domainEvent.getUserPassword().value().length() < 4) {  
                throw new IllegalArgumentException("The password must be greater than 4 characters");  
         }  
         if (domainEvent.getUserName().value().length() < 5) {  
                throw new IllegalArgumentException("The username must be greater than 5 characters");  
         }  
         entity.userName = domainEvent.getUserName();  
         entity.userPassword = domainEvent.getUserPassword();  
     });  
   }  
    protected UserBehaviors(User entity) {  
        super(entity);  
    }  
}
```

### Objetos de valor
Un objeto de valor es un objeto inmutable que representa un valor de la entidad. A diferencia de la entidad es que el VO (Value Object) no tiene un identidad que la represente. 
```java
public class RoleName implements ValueObject<String> {  
    private final String name;  
    public RoleName(String name) {  
        this.name = Objects.requireNonNull(name);  
     }  
     @Override  
     public String value() {  
        return name;  
     }  
}
```

### Evento de Dominio
Un evento de dominio esta relacionado con un Agregado Id (que es un VO), este evento es la consecuencia de un comando que ejecuta un comportamiento de un Agregado un Entidad. 
```java
public class UserCreated extends DomainEvent {  
    final UserName userName;  
    final UserPassword userPassword;  
  
    public UserCreated(UserId userId, UserName userName,UserPassword userPassword) {  
        super("user.created", userId);  
        this.userName = userName;  
        this.userPassword = userPassword;  
    }  
    public UserName getUserName() {  
        return userName;  
    }  
    public UserPassword getUserPassword() {  
        return userPassword;  
    }  
}
```

### Aplicar el agregado
Cuando se crear nuevo objeto se instancia el agregado y se le asigna el identificado que relaciona la entidad. Además de los argumentos de creación. Un agregado puede tener múltiples constructores que permiten crear el agregado.  
```java
UserId userId = UserId.create();  
User user = new User(userId, request.userName, request.userPassword);  
```
Ya para el caso de Actualizar un agregado se reconstruye el agregado según los eventos persistidos, si se tiene un agregado no orientado a eventos se reconstruye con los valores necesarios. Cuando se reconstruya se aplica el comportamiento de la entidad.
```java
var user = User.from(userId, repository.getEventsBy(userId));  
user.updateUserPassword(request.newPassword);  
emit().onSuccess(new ResponseEvents(user.getUncommittedChanges()));
user.markChangesAsCommitted();
```
Al final de ejecutar el comportamiento se determina si existe cambios dentro del agregado para emitir los eventos a una capa superior. Después se marca los cambios como confirmados. 

> Se puede aplicar aquí casos de uso, comandos y eventos para aplicar el agregado. 

### Entidades
Una entidad tiene comportamientos, pero no lanza eventos como son los agregados, si tenemos una entidad suelta sin relación con el agregado entonces solo se aplica para cambiar los estados de la misma. Toda entidad depende de una ID, tal cual como el Agregado Root.
```java
public class Group extends Entity<GroupId> {  
  
    public Group(GroupId entityId) {  
        super(entityId);  
    }  
  
    public void changeName(GroupName groupName){  
        //....  
     }  
}
```

> Mas adelante estaríamos realizando un tutorial para aplicarlo en una arquitectura distribuida y con CQRS+ES con una arquitectura EDA. 

## Contribución

1. Realizar el fork
2. Crea tu rama de características: git checkout -b feature/my-feat
3. Confirma tus cambios: `git commit -am "Agrega alguna característica"`
4. Empuje a la rama: git push origin feature/my-feat
5. Presentar una Merge Requests


## License

Sofka Domain-Drive Desing is Open Source software released under the [Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0.html).
