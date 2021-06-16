package spring.cloud.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.cloud.task.listener.TaskExecutionListener;
import org.springframework.cloud.task.listener.annotation.AfterTask;
import org.springframework.cloud.task.listener.annotation.BeforeTask;
import org.springframework.cloud.task.listener.annotation.FailedTask;
import org.springframework.cloud.task.repository.TaskExecution;

import java.text.Format;
import java.time.LocalDateTime;

@SpringBootApplication
@EnableTask
@Slf4j
public class SpringCloudTaskApplication implements CommandLineRunner , TaskExecutionListener {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudTaskApplication.class, args);
    }

    @Override
    public void run(String ...args) throws Exception{
        log.info("Run Function calling...");
        System.out.println("LocalDateTime.now() = " + LocalDateTime.now());
    }

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
