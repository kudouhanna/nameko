package com.example.nameko.dto;
import lombok.Data;
import java.time.LocalDateTime;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Nameko {
    private long id;
    private String name;
    // レア度
    private String rarity;
    // 説明
    private String description;
    private String image;
    // 登録フォームから受け取る画像ファイル
    private MultipartFile imageFile;
    private LocalDateTime createdAt;
}