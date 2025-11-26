# Advanced Programming Exam Study Guide

## Table of Contents
1. [Threads](#threads)
2. [Spring Boot & Thymeleaf](#spring-boot--thymeleaf)
3. [Spring Framework Beans](#spring-framework-beans)
4. [Database Integration with JPA](#database-integration-with-jpa)
5. [Processes & Threads Concepts](#processes--threads-concepts)
6. [Key Code Examples](#key-code-examples)

---

## Threads

### Two Ways to Create Threads

#### 1. Implementing Runnable Interface
```java
public class RunThreadImplementRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println("Thread - RunThreadImplementRunnable");
    }
}

// In main:
Thread t = new Thread(new RunThreadImplementRunnable());
t.start();  // Use start(), NOT run()
```

#### 2. Extending Thread Class
```java
public class RunThreadExtendThread extends Thread {
    @Override
    public void run() {
        System.out.println("Thread - RunThreadExtendThread");
    }
}

// In main:
RunThreadExtendThread threadExtend = new RunThreadExtendThread();
threadExtend.start();
```

### Anonymous Runnable with Lambda
```java
Thread t = new Thread(() -> {
    System.out.println("Thread - I love Java with lambdas!");
});
t.start();
```

### Passing Data to Threads

#### Using Constructor
```java
public class MyRunnableWithParameter implements Runnable {
    private int data;
    
    public MyRunnableWithParameter(int d) { 
        data = d; 
    }
    
    @Override
    public void run() {
        System.out.printf("Passed in data is: %d\n", data);
    }
}

Thread t = new Thread(new MyRunnableWithParameter(777));
t.start();
```

#### Using Final Variables
```java
final String message = "I love threads with final variables!";
Thread t = new Thread(() -> {
    System.out.println(message);
});
t.start();
```

#### Using Effectively Final Variables
```java
String message = "I love threads!";  // No reassignment = effectively final
Thread t = new Thread(() -> {
    System.out.println(message);  // OK - effectively final
});
t.start();

// This would cause compile error:
String message2 = "Hello";
message2 = "Changed";  // NOT effectively final
Thread t2 = new Thread(() -> {
    System.out.println(message2);  // COMPILE ERROR
});
```

### Thread Control Methods

#### join() - Wait for Thread to Finish
```java
Thread t = new Thread(new RunThreadImplementRunnable());
t.start();
try {
    t.join();  // Current thread waits until t finishes
} catch (InterruptedException e) {
    e.printStackTrace();
}
```

#### sleep() - Pause Thread
```java
Thread t = new Thread(() -> {
    System.out.println("Going to sleep");
    Thread.sleep(1000);  // Sleep 1 second (1000 milliseconds)
    System.out.println("Waking up!");
});
t.start();
```

### Synchronization

#### Synchronized Method
```java
public synchronized void myMethod() {
    // Only one thread can execute this at a time
    // Critical section code
}
```

#### Synchronized Block
```java
Object myLock = new Object();  // Lock must be accessible to all threads

public void myMethod() {
    // Other code (not synchronized)
    
    synchronized(myLock) {
        // Critical section code
        // Only one thread can execute this at a time
    }
    
    // Other code (not synchronized)
}
```

### Thread Pools and Executors

#### Benefits
- Reuse threads instead of creating/destroying them
- Computationally efficient
- Like leaving a car running vs starting/stopping it repeatedly

#### Creating and Using ExecutorService
```java
ExecutorService exec = Executors.newSingleThreadExecutor();

exec.submit(() -> {
    String tName = Thread.currentThread().getName();
    System.out.printf("Message from %s\n", tName);
});

// When completely done with threads:
exec.shutdown();
```

#### Types of Executors
- `newSingleThreadExecutor()` - One thread, reused
- `newFixedThreadPool(n)` - Fixed number of threads
- `newCachedThreadPool()` - Reuses threads, terminates unused ones

#### Waiting for Completion
```java
exec.shutdown();  // Stop accepting new tasks
try {
    exec.awaitTermination(1, TimeUnit.MINUTES);  // Wait up to 1 minute
} catch (InterruptedException ex) {
    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
}
```

#### Proper Shutdown Pattern
```java
try {
    System.out.println("attempt to shutdown executor");
    executor.shutdown();
    executor.awaitTermination(5, TimeUnit.SECONDS);
} catch (InterruptedException e) {
    System.err.println("tasks interrupted");
} finally {
    if (!executor.isTerminated()) {
        System.err.println("cancel non-finished tasks");
    }
    executor.shutdownNow();
    System.out.println("shutdown finished");
}
```

### CountDownLatch - Wait for Multiple Threads

```java
// In main thread:
final CountDownLatch finishedSignal = new CountDownLatch(10);

// Create and run 10 threads here...

try {
    finishedSignal.await();  // Wait until count reaches 0
} catch (InterruptedException ex) {
}

// In each thread:
// Do work...
finishedSignal.countDown();  // Decrement counter when done
```

### GUI Threading

#### Problem
- Main thread handles all GUI controls
- Long operations make GUI unresponsive

#### Solution
- Use other threads for long operations
- Use `Platform.runLater()` to update GUI from other threads

```java
// From another thread:
Platform.runLater(() -> myTextField.setText("abc"));
```

---

## Spring Boot & Thymeleaf

### HTML Basics

#### Document Structure
```html
<!doctype html>
<html>
<head>
    <title>My Webpage</title>
    <style>
        h1 {color: fuchsia;}
        p {color: blue;}
    </style>
</head>
<body>
    <h1>Main Heading</h1>
    <p>Paragraph text</p>
</body>
</html>
```

#### Common HTML Elements

**Headings**
```html
<h1>Highest-level heading</h1>
<h2>Second-level heading</h2>
```

**Paragraphs and Line Breaks**
```html
<p>First paragraph</p>
<p>Second paragraph</p>

First line.<br>Next line.
```

**Lists**
```html
<ul>
    <li>Orange</li>
    <li>Apple</li>
    <li>Strawberry</li>
</ul>
```

**Links**
```html
<a href="/details">Show Details</a>
<a href="https://www.farmingdale.edu/">Go to Farmingdale Website</a>
```

**Images**
```html
<img src="mypic.jfif" alt="My Pic">
```

**Comments**
```html
<!-- This is a comment -->
```

### Spring MVC Architecture

**Model-View-Controller Pattern:**
- **Model**: Internal data representations
- **View**: Interface presenting information (HTML)
- **Controller**: Links model and view

```
Web Server → Controller → Model → View
```

### Maven Dependencies

```xml
<!-- Spring Boot Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Spring Boot Thymeleaf -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

### Controller Basics

#### Simple Controller
```java
@Controller
public class SimpleController {
    
    @GetMapping("/")
    public String index() {
        return "index";  // Returns index.html from templates/
    }
    
    @GetMapping("/hello")
    public String hello() {
        return "hello";  // Returns hello.html from templates/
    }
}
```

#### Controller with Model Data
```java
@Controller
public class IndexController {
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("student", new Student());
        return "index";
    }
}
```

### Thymeleaf Setup

```html
<html lang="en" xmlns:th="http://www.thymeleaf.org">
```

### Displaying Dynamic Content

#### Using th:text with Span
```html
<!-- In Controller -->
model.addAttribute("student", new Student());

<!-- In HTML -->
<span th:text="${student.name}"></span>
```

#### Adding Additional Text
```html
<span th:text="${student.name} + ' is awesome'"></span>
<span th:text="${student.name} + ' is awesome ' + ${student.id}"></span>
```

### Passing Data in URLs

#### URL with Query Parameters
```
/student?name=Juan&age=20
```

#### Links with Parameters
```html
<!-- Plain HTML -->
<a href="/student?name=Juan">View Juan</a>

<!-- Thymeleaf with constant -->
<a th:href="@{/student(name=Juan)}">View Juan</a>

<!-- Thymeleaf with variable -->
<a th:href="@{/student(name=${targetName})}">View Student</a>
```

### Forms and Data Entry

#### Basic Form
```html
<form action="/student" method="get">
    <input type="number" name="id" placeholder="Enter Id" required />
    <button type="submit">Find</button>
</form>
```

#### Form Input Types
- `type="text"` - String data
- `type="number"` - Numeric data
- `required` - Field must be filled

#### Controller Handling Form Data
```java
@Controller
public class IndexController {
    
    @GetMapping("/")
    public String index(Model model) {
        return "index";  // Data entry page
    }
    
    @GetMapping("/student")
    public String student(@RequestParam int id, Model model) {
        model.addAttribute("id", id);
        return "student";  // Display page
    }
}
```

#### Displaying Received Data
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<body>
    Id = <span th:text="${id}"></span>
</body>
</html>
```

### Displaying Collections

#### Dynamic List
```java
// Controller
@GetMapping("/")
public String index(Model model) {
    model.addAttribute("studentCollection", new StudentCollection());
    return "index";
}
```

```html
<!-- HTML -->
<ul>
    <li th:each="s : ${studentCollection.students}">
        <span th:text="${s.name}"></span>
    </li>
</ul>
```

#### Dynamic Table
```html
<table>
    <thead>
        <tr>
            <th>Name</th>
            <th>Id</th>
        </tr>
    </thead>
    <tbody>
        <tr th:each="s : ${studentCollection.students}">
            <td th:text="${s.name}"></td>
            <td th:text="${s.id}"></td>
        </tr>
    </tbody>
</table>
```

#### Table with Grid Lines
```html
<head>
    <style>
        table, td, th {
            border: 1px solid #000000;
        }
    </style>
</head>
```

### Multiple Controllers and Mappings

#### HTTP Methods
- **GET**: Retrieve data (data in URL, size limit)
- **POST**: Send data (data in request body, no size limit)

#### Controller with Request Mapping
```java
@Controller
@RequestMapping("/students")
public class StudentsController {
    
    @GetMapping  // Maps to /students
    public String listStudents(Model model) {
        return "students";
    }
    
    @GetMapping("/otherstuff")  // Maps to /students/otherstuff
    public String otherStuff() {
        return "otherstuff";
    }
    
    @PostMapping  // Maps to /students (POST)
    public String addStudent(...) {
        // Code to add student
        return "redirect:/students";
    }
}
```

#### Home Controller
```java
@Controller
@RequestMapping("/")
public class HomeController {
    
    @GetMapping
    public String home() {
        return "home";  // or "redirect:/students"
    }
}
```

---

## Spring Framework Beans

### What is a Bean?

**Bean**: A Java object managed by the Spring IoC (Inversion of Control) Container

```
┌─────────────────────────────────────┐
│  IoC Container (Application Context) │
│  ┌──────┐  ┌──────┐  ┌──────┐      │
│  │ Bean │  │ Bean │  │ Bean │      │
│  └──────┘  └──────┘  └──────┘      │
└─────────────────────────────────────┘

Objects NOT in container = NOT beans
```

### When to Use Beans

**Use beans for:**
- Repositories
- Services
- Controllers
- Stateless components

**Don't use beans for:**
- Individual data objects (Employee, CustomerOrder)
- Stateful objects

### Creating Beans with @Component

```java
@Component
class SharedData {
    String data = "I love Spring";
}

@Component
class UseSharedData {
    // Spring automatically creates instances
}
```

### Dependency Injection with @Autowired

#### Constructor Injection (Preferred)
```java
@Component
class UseSharedData {
    SharedData sharedData;
    
    @Autowired  // Optional if only one constructor
    public UseSharedData(SharedData sharedData) {
        this.sharedData = sharedData;
    }
}

@Component
class SharedData {
}
```

#### Field Injection
```java
@Component
class UseSharedData {
    @Autowired
    SharedData sharedData;  // Spring injects automatically
}
```

**Important:** Do NOT call `new` on autowired beans!

### Bean Types and Annotations

| Annotation | Purpose |
|------------|---------|
| `@Component` | Generic bean |
| `@Service` | Business logic |
| `@Controller` | Web requests → returns views (HTML) |
| `@RestController` | REST APIs → returns JSON/XML |
| `@Repository` | Data access |

### Complete Bean Example

#### 1. Create Bean Class
```java
@Component
public class SharedData {
    String data = "I love Spring";
    
    public SharedData() {
        System.out.println("SharedData constructor called");
    }
}
```

#### 2. Create Class Using Bean
```java
@Component
public class UseSharedData {
    SharedData sharedData;
    
    @Autowired
    public UseSharedData(SharedData sharedData) {
        System.out.println("UseSharedData constructor called: " + sharedData.data);
    }
}
```

### Reading from Resource Files

```java
try {
    String resourceName = "input.txt";  // File in resources/ directory
    Resource resource = new ClassPathResource(resourceName);
    File file = resource.getFile();
    FileReader fr = new FileReader(file);
    Scanner infile = new Scanner(fr);
    String data = infile.nextLine();
    infile.close();
    fr.close();
} catch (IOException e) {
    throw new RuntimeException(e);
}
```

### Displaying All Beans in Container

```java
@Component
public class BeanPrinter implements CommandLineRunner {
    
    @Autowired
    private ApplicationContext applicationContext;
    
    @Override
    public void run(String... args) {
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            Object bean = applicationContext.getBean(beanName);
            System.out.println("**Bean: " + beanName + " : " + bean.getClass().getName());
        }
    }
}
```

---

## Database Integration with JPA

### ORM (Object-Relational Mapping)

**Purpose**: Work with relational databases using objects

```
Java Class ←→ Database Table
Instance   ←→ Row
Fields     ←→ Columns
```

### Spring Database Stack

```
Application
    ↓
Spring Data JPA (easiest to use)
    ↓
JPA (specification)
    ↓
Hibernate (ORM implementation)
    ↓
Relational Database
```

### Maven Dependencies

```xml
<!-- Spring Boot JPA Starter -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- H2 Database -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

### Database Configuration (application.properties)

```properties
# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.defer-datasource-initialization=true
```

#### Database Storage Options

**In-Memory** (data lost on restart):
```properties
spring.datasource.url=jdbc:h2:mem:testdb
```

**File-Based** (data persists):
```properties
spring.datasource.url=jdbc:h2:file:./testdb
```

### Entity Class

```java
import jakarta.persistence.*;

@Entity
@Table(name = "persons")
public class Person {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private int age;
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
```

#### Entity Annotations

| Annotation | Purpose |
|------------|---------|
| `@Entity` | Marks class as database table |
| `@Table(name="...")` | Specifies table name |
| `@Id` | Marks primary key field |
| `@GeneratedValue` | Auto-generates values |
| `@Column(name="...")` | Maps field to different column name |

### Repository Interface

```java
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
    // Spring automatically generates implementation
    // Includes CRUD methods: save, findAll, findById, delete, etc.
}
```

**Key Points:**
- Extends `JpaRepository<EntityType, PrimaryKeyType>`
- Spring auto-generates implementation class
- Auto-generated class is a bean
- Includes CRUD methods

### Initial Data (data.sql)

Create `data.sql` in `src/main/resources/`:

```sql
INSERT INTO persons (name, age) VALUES ('Juan', 20);
INSERT INTO persons (name, age) VALUES ('Ailya', 21);
INSERT INTO persons (name, age) VALUES ('Liam', 19);
```

### Displaying Database Data

#### Controller
```java
@Controller
public class PersonController {
    
    @Autowired
    private PersonRepository personRepository;
    
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("persons", personRepository.findAll());
        return "index";
    }
}
```

#### HTML (index.html)
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Person List</title>
</head>
<body>
    <h1>Persons</h1>
    <ul>
        <li th:each="p : ${persons}">
            <span th:text="${p.name}"></span> - <span th:text="${p.age}"></span>
        </li>
    </ul>
</body>
</html>
```

### Inserting Data from Form

#### Controller
```java
@Controller
public class PersonController {
    
    @Autowired
    private PersonRepository personRepository;
    
    @GetMapping("/addperson")
    public String addPerson(Model model) {
        model.addAttribute("person", new Person());
        return "addperson";
    }
    
    @PostMapping("/insert")
    public String addPerson(@ModelAttribute Person person) {
        personRepository.save(person);  // Insert into database
        return "redirect:/";  // Redirect to home page
    }
}
```

#### HTML Form (addperson.html)
```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<body>
    <form action="/insert" method="post" th:object="${person}">
        <input type="text" th:field="*{name}" placeholder="Name" required />
        <input type="number" th:field="*{age}" placeholder="Age" required />
        <button type="submit">Add</button>
    </form>
</body>
</html>
```

### H2 Console

**Access:** `http://localhost:8080/h2-console`

**Login:**
- JDBC URL: `jdbc:h2:mem:testdb` (or file path)
- Username: `sa`
- Password: (leave blank)

---

## Processes & Threads Concepts

### Operating System Role

**OS manages:**
- CPU scheduling
- Memory (RAM) usage
- Hard drive access
- All devices

### Process vs Thread

**Process:**
- Instance of a running program
- Active entity requiring resources
- Created when you click to run a program
- Has its own memory space

**Thread:**
- Portion of a process
- Runs independently
- Shares process resources
- Lightweight

```
Process 1
├── Thread 1
├── Thread 2
└── Thread 3

Process 2
├── Thread 1
└── Thread 2
```

### Concurrency vs Parallelism

**Concurrency:**
- Two processes making progress at once
- Can work with single CPU (time-slicing)
- OS switches between processes rapidly

**Parallelism:**
- Two processes executing simultaneously in real time
- Requires multiple CPUs or cores
- True simultaneous execution

### CPU Cores

```
┌─────────────────┐
│      CPU        │
│  ┌────┐ ┌────┐ │
│  │Core│ │Core│ │
│  │ 1  │ │ 2  │ │
│  └────┘ └────┘ │
│  ┌────┐ ┌────┐ │
│  │Core│ │Core│ │
│  │ 3  │ │ 4  │ │
│  └────┘ └────┘ │
└─────────────────┘
```

Each core can run a thread independently.

### Synchronous vs Asynchronous

**Synchronous:**
- Call method on same thread
- Caller waits until method finishes
- Everything stops until return

**Asynchronous:**
- Worker method runs in background
- Calling thread not blocked
- Worker notifies caller when done

### Critical Section

**Definition:** Code section that only one thread can enter at a time

**Examples:**
- Security scanner (one person at a time)
- One-lane bridge (one car at a time)

**Implementation:**
```java
public synchronized void criticalMethod() {
    // Only one thread at a time
}

// OR

Object lock = new Object();
synchronized(lock) {
    // Only one thread at a time
}
```

### Thread Synchronization

**Purpose:** Ensure only one thread accesses critical section at a time

**Java mechanisms:**
- `synchronized` keyword
- Locks
- Semaphores

### Deadlock

**Definition:** Multiple processes stuck waiting for each other

**Example:**
```
Thread 1: Has Lock A, Waiting for Lock B
Thread 2: Has Lock B, Waiting for Lock A
→ DEADLOCK!
```

#### Deadlock Code Example
```java
Object lockA = new Object();
Object lockB = new Object();

// Thread 1
void methodForThread1() {
    synchronized(lockA) {
        // Time passes...
        synchronized(lockB) {
            // Code requiring both locks
        }
    }
}

// Thread 2
void methodForThread2() {
    synchronized(lockB) {  // Different order!
        // Time passes...
        synchronized(lockA) {
            // Code requiring both locks
        }
    }
}
```

#### Deadlock Solution
**Always acquire locks in the same order:**

```java
// Both threads acquire locks in same order
void methodForThread1() {
    synchronized(lockA) {
        synchronized(lockB) {
            // Code
        }
    }
}

void methodForThread2() {
    synchronized(lockA) {  // Same order!
        synchronized(lockB) {
            // Code
        }
    }
}
```

### Producer-Consumer Problem

```
Producer → [Buffer] → Consumer
```

- **Producer:** Puts data in buffer
- **Consumer:** Reads and removes data from buffer
- **Both modify the buffer** → Requires synchronization

### Readers-Writers Problem

```
Writer → [Buffer] → Multiple Readers
```

- **Writers:** Modify buffer
- **Readers:** Read only (don't modify)
- **Multiple simultaneous readers allowed**
- **When writer is writing, readers blocked**

#### Differences from Producer-Consumer
1. Readers don't modify buffer (consumers do)
2. Multiple readers allowed (only one consumer)

---

## Key Code Examples

### Thread Example with Executor
```java
ExecutorService exec = Executors.newSingleThreadExecutor();

exec.submit(() -> {
    String tName = Thread.currentThread().getName();
    System.out.printf("Message from %s\n", tName);
});

exec.shutdown();
```

### Spring Boot Controller with Database
```java
@Controller
@RequestMapping("/students")
public class StudentController {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "students";
    }
    
    @PostMapping
    public String addStudent(@ModelAttribute Student student) {
        studentRepository.save(student);
        return "redirect:/students";
    }
}
```

### Thymeleaf Form with Object Binding
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<body>
    <form action="/insert" method="post" th:object="${person}">
        <input type="text" th:field="*{name}" placeholder="Name" required />
        <input type="number" th:field="*{age}" placeholder="Age" required />
        <button type="submit">Submit</button>
    </form>
    
    <table>
        <tr th:each="person : ${persons}">
            <td th:text="${person.name}"></td>
            <td th:text="${person.age}"></td>
        </tr>
    </table>
</body>
</html>
```

---

## Quick Reference Tables

### Thread Methods
| Method | Purpose |
|--------|---------|
| `start()` | Start thread execution |
| `join()` | Wait for thread to finish |
| `sleep(ms)` | Pause thread for milliseconds |
| `run()` | Method containing thread code |

### Spring Annotations
| Annotation | Purpose |
|------------|---------|
| `@Controller` | Web controller (returns views) |
| `@RestController` | REST API controller |
| `@Service` | Business logic bean |
| `@Repository` | Data access bean |
| `@Component` | Generic bean |
| `@Autowired` | Dependency injection |
| `@GetMapping` | Handle HTTP GET |
| `@PostMapping` | Handle HTTP POST |
| `@RequestMapping` | Map controller to path |

### JPA Annotations
| Annotation | Purpose |
|------------|---------|
| `@Entity` | Mark class as table |
| `@Table(name="...")` | Specify table name |
| `@Id` | Mark primary key |
| `@GeneratedValue` | Auto-generate values |
| `@Column(name="...")` | Map to column |

### Thymeleaf Syntax
| Syntax | Purpose |
|--------|---------|
| `th:text="${var}"` | Display variable |
| `th:each="item : ${list}"` | Loop over collection |
| `th:field="*{field}"` | Bind form field |
| `th:object="${obj}"` | Bind form to object |
| `th:href="@{/path}"` | Create link |

---

## Study Tips

1. **Understand the flow:** Request → Controller → Model → View
2. **Know when to use `@Autowired`:** Only for Spring-managed beans
3. **Remember thread safety:** Use `synchronized` for shared data
4. **Practice writing controllers:** They're central to Spring Boot
5. **Understand JPA mappings:** Class ↔ Table, Field ↔ Column
6. **Know the difference:** GET (retrieve) vs POST (send data)
7. **Master Thymeleaf basics:** `th:text`, `th:each`, `th:field`

Good luck on your exam! 🚀