# spring-cloud-task

The goal of Spring Cloud Task is to provide the functionality of creating short-lived microservices for Spring Boot application.

In Spring Cloud Task, we've got the flexibility of running any task dynamically, allocating resources on demand and retrieving the results after the Task completion.

Tasks is a new primitive within Spring Cloud Data Flow allowing users to execute virtually any Spring Boot application as a short-lived task.

## Developing a Simple Task Application
Adding Relevant Dependencies
To start, we can add dependency management section with spring-cloud-task-dependencies:

```javascript
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-task-dependencies</artifactId>
            <version>2.2.3.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```
This dependency management manages versions of dependencies through the import scope.

We need to add the following dependencies:

```javascript
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-task</artifactId>
</dependency>
```

The @EnableTask Annotation
To bootstrap the functionality of Spring Cloud Task, we need to add @EnableTask annotation:

```javascript
@SpringBootApplication
@EnableTask
public class TaskDemo {
    // ...
}
```

## Implementation
In Spring Boot, we can execute any Task just before application finishes its startup. We can use ApplicationRunner or CommandLineRunner interfaces to create a simple Task.

We need to implement the run method of these interfaces and declare the implementing class as a bean:

```javascript
@Component
public static class HelloWorldApplicationRunner implements CommandLineRunner {
 
    @Override
    public void run(ApplicationArguments arg0) throws Exception {
        System.out.println("Hello World from Spring Cloud Task!");
    }
}
```

## Life-cycle of a Spring Cloud Task
In the beginning, we create an entry in the TaskRepository. This's the indication that all beans are ready to be used in the Application and the run method of Runner interface is ready to be executed.

Upon completion of the execution of the run method or in any failure of ApplicationContext event, TaskRepository will be updated with another entry.

During the task life-cycle, we can register listeners available from TaskExecutionListener interface. We need a class implementing the interface having three methods – onTaskEnd, onTaksFailed and onTaskStartup triggered in respective events of the Task.

```javascript
@Component
public static class HelloWorldApplicationRunner implements CommandLineRunner , TaskExecutionListener  {
 
    // ....
    @Override
    public void onTaskStartup(TaskExecution taskExecution) {
        log.info("Run Function calling...");
        System.out.println("Task Started LocalDateTime.now() = " + LocalDateTime.now() + " -> Task id : "+taskExecution.getTaskName());
    }

    @Override
    public void onTaskEnd(TaskExecution taskExecution) {
        log.info("Run Function calling...");
        System.out.println("Task Ended LocalDateTime.now() = " + LocalDateTime.now() + " -> Task id : "+taskExecution.getTaskName());
    }

    @Override
    public void onTaskFailed(TaskExecution taskExecution, Throwable throwable) {
        log.info("Run Function calling...");
        System.out.println("Task Failed LocalDateTime.now() = " + LocalDateTime.now() + " -> Task id : "+taskExecution.getTaskName());
    }
    
}
```

## Using InBuild Annotaion

```javascript
@Component
public static class HelloWorldApplicationRunner implements CommandLineRunner , TaskExecutionListener  {
 
    // ....
    @BeforeTask
    public void onStartup(TaskExecution taskExecution) {
        log.info("Run Function calling...");
        System.out.println("Task Started LocalDateTime.now() = " + LocalDateTime.now() + " -> Task id : "+taskExecution.getTaskName());
    }

    @AfterTask
    public void onEnd(TaskExecution taskExecution) {
        log.info("Run Function calling...");
        System.out.println("Task Ended LocalDateTime.now() = " + LocalDateTime.now() + " -> Task id : "+taskExecution.getTaskName());
    }

    @FailedTask
    public void onFailed(TaskExecution taskExecution, Throwable throwable) {
        log.info("Run Function calling...");
        System.out.println("Task Failed LocalDateTime.now() = " + LocalDateTime.now() + " -> Task id : "+taskExecution.getTaskName());
    }
    
}
```

## Conclusion
In this tutorial, we've explored how Spring Cloud Task performs and how to configure it to log its events in a database. We also observed how Spring Batch job is defined and stored in the TaskRepository. Lastly, we explained how we can trigger Task from within Spring Cloud Stream.
