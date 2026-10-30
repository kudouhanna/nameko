package com.example.nameko;

import java.nio.file.Paths;
import java.nio.file.Files;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        var uploadPath = Paths.get(
                System.getProperty("user.dir"),
                "uploads",
                "images"
        ).toAbsolutePath().normalize();

        String imagePath = uploadPath.toUri().toString();

        System.out.println("画像フォルダの参照先：" + uploadPath);
        System.out.println("画像フォルダの存在：" + Files.exists(uploadPath));
        System.out.println("確認対象ファイルの存在：" +
                Files.exists(uploadPath.resolve(
                        "0234b5ba-e20f-45f3-ac4e-f4b3e4c458b8.jpg"
                )));

        registry.addResourceHandler("/images/**")
                .addResourceLocations(imagePath);
    }
}