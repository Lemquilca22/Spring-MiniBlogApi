package com.miniblog.api.controller;

import com.miniblog.api.dto.PostCreateDto;
import com.miniblog.api.dto.PostResponse;
import com.miniblog.api.dto.PostResponseDto;
import com.miniblog.api.service.PostService;
import io.swagger.v3.oas.annotations.Parameter; // <--- IMPORTANTE
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<PostResponse> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        PostResponse postResponse = postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(postResponse);
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(
            @Valid @RequestBody PostCreateDto dto,
            @Parameter(hidden = true) Principal principal) { // <--- @Parameter(hidden = true)

        PostResponseDto createdPost = postService.createPost(dto, principal.getName());
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostCreateDto dto,
            @Parameter(hidden = true) Principal principal // <--- @Parameter(hidden = true)
    ) {
        PostResponseDto updatedPost = postService.updatePost(id, dto, principal.getName());
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long id,
            @Parameter(hidden = true) Principal principal // <--- @Parameter(hidden = true)
    ) {
        postService.deletePost(id, principal.getName());
        return ResponseEntity.noContent().build();
    }
}