package com.example.lessonscrudproject;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(
        description = "Lessons and Courses crud project with security",
        version = "3.0.9",
        contact = @Contact(
                name = "https://javb_backand",
                email = "hjorayev700@gmail.com"
        ),
        license = @License(name = "Simple project")
),
        tags = @Tag(name = "Crud project")
)
public class LessonsAndCoursesCrudApplication {

    public static void main(String[] args) {
        SpringApplication.run(LessonsAndCoursesCrudApplication.class, args);
    }


}
