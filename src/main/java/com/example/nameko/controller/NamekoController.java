
package com.example.nameko.controller;

import com.example.nameko.dto.Nameko;
import com.example.nameko.service.NamekoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/namekos")
public class NamekoController {

    private final NamekoService namekoService;

    public NamekoController(NamekoService namekoService) {
        this.namekoService = namekoService;
    }

    // なめこの一覧取得
    @GetMapping
    public ResponseEntity<List<Nameko>> findAllNamekos() {
        List<Nameko> namekos = namekoService.findAll();
        return ResponseEntity.ok(namekos);
    }

    // なめこの新規登録
    @PostMapping
    public ResponseEntity<Nameko> saveNameko(
            @RequestBody Nameko nameko,
            UriComponentsBuilder uriBuilder) {

        Nameko createdNameko = namekoService.saveNameko(nameko);

        URI location = uriBuilder
                .path("/api/namekos/{id}")
                .buildAndExpand(createdNameko.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdNameko);
    }

    // なめこを1件取得
    @GetMapping("/{id}")
    public ResponseEntity<Nameko> findById(@PathVariable Long id) {
        try {
            Nameko nameko = namekoService.findById(id);
            return ResponseEntity.ok(nameko);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // なめこを削除
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        namekoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // なめこの情報を更新
    @PutMapping("/{id}")
    public ResponseEntity<Nameko> update(
            @PathVariable long id,
            @RequestBody Nameko nameko) {

        Nameko updatedNameko = namekoService.update(nameko, id);
        return ResponseEntity.ok(updatedNameko);
    }
}
