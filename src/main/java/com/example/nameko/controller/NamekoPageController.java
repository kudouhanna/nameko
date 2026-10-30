
package com.example.nameko.controller;

import com.example.nameko.dto.Nameko;
import com.example.nameko.service.NamekoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
public class NamekoPageController {

    private final NamekoService namekoService;

    public NamekoPageController(NamekoService namekoService) {
        this.namekoService = namekoService;
    }

    // なめこ図鑑一覧
    @GetMapping("/namekos")
    public String showNamekos(Model model) {
        var namekos = namekoService.findAll();

        model.addAttribute("namekos", namekos);
        return "nameko-list";
    }

    // なめこ新規登録画面
    @GetMapping("/namekos/new")
    public String showCreateForm(Model model) {
        model.addAttribute("nameko", new Nameko());
        return "nameko-form";
    }

    // なめこ新規登録処理
    @PostMapping("/namekos/new")
    public String createNameko(
            @ModelAttribute Nameko nameko) throws IOException {

        // 選択した画像を保存
        String filename = saveImage(nameko.getImageFile());

        // 保存した画像のファイル名を設定
        if (filename != null) {
            nameko.setImage(filename);
        }

        // なめこの情報をデータベースに保存
        namekoService.saveNameko(nameko);

        return "redirect:/namekos";
    }

    // なめこ詳細画面
    @GetMapping("/namekos/{id}")
    public String showDetail(
            @PathVariable long id, Model model) {

        Nameko nameko = namekoService.findById(id);
        model.addAttribute("nameko", nameko);

        return "nameko-detail";
    }

    // なめこ編集画面
    @GetMapping("/namekos/{id}/edit")
    public String showEditForm(
            @PathVariable long id, Model model) {

        Nameko nameko = namekoService.findById(id);
        model.addAttribute("nameko", nameko);

        return "nameko-form";
    }

    // なめこ編集処理
    @PostMapping("/namekos/{id}/edit")
    public String updateNameko(
            @PathVariable long id,
            @ModelAttribute Nameko nameko) throws IOException {

        // 編集前の情報を取得
        Nameko oldNameko = namekoService.findById(id);

        // 新しい画像を選択した場合だけ保存
        String filename = saveImage(nameko.getImageFile());

        if (filename != null) {
            // 新しい画像に変更
            nameko.setImage(filename);
        } else {
            // 画像を選択していない場合は以前の画像を維持
            nameko.setImage(oldNameko.getImage());
        }

        // なめこの情報を更新
        namekoService.update(nameko, id);

        return "redirect:/namekos/" + id;
    }

    // なめこ削除処理
    @PostMapping("/namekos/{id}/delete")
    public String deleteNameko(@PathVariable long id) {
        namekoService.delete(id);
        return "redirect:/namekos";
    }

    // 画像を保存する処理
    private String saveImage(
            MultipartFile imageFile) throws IOException {

        // 画像が選択されていない場合
        if (imageFile == null || imageFile.isEmpty()) {
            return null;
        }

        // 元のファイル名から拡張子を取得
        String originalFilename = imageFile.getOriginalFilename();
        String extension = "";

        if (originalFilename != null
                && originalFilename.contains(".")) {
            extension = originalFilename.substring(
                    originalFilename.lastIndexOf(".")
            );
        }

        // ファイル名が重複しないようにUUIDを使用
        String filename = UUID.randomUUID() + extension;

        Path uploadPath = Paths.get(
                System.getProperty("user.dir"),
                "uploads",
                "images"
        ).toAbsolutePath().normalize();

        // 保存先フォルダがなければ作成
        Files.createDirectories(uploadPath);

        // 画像を保存
        Path savePath = uploadPath.resolve(filename);
        imageFile.transferTo(savePath);

        // 保存したファイル名を返す
        return filename;
    }
}
