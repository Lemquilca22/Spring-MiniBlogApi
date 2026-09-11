package com.miniblog.api.service;

import com.miniblog.api.dto.PostCreateDto;
import com.miniblog.api.dto.PostResponse;
import com.miniblog.api.dto.PostResponseDto;
import com.miniblog.api.exception.ResourceNotFoundException;
import com.miniblog.api.exception.UnauthorizedAccessException;
import com.miniblog.api.model.Post;
import com.miniblog.api.model.User;
import com.miniblog.api.repository.PostRepository;
import com.miniblog.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        // 1. Determinar la dirección del ordenamiento (ASC o DESC)
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // 2. Construir las reglas de paginación
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        // 3. Consultar la base de datos con paginación
        Page<Post> postsPage = postRepository.findAll(pageable);

        // 4. Extraer y mapear la lista de entidades a DTOs
        List<PostResponseDto> content = postsPage.getContent().stream()
                .map(this::mapToDto)
                .toList();

        // 5. Empaquetar el contenido junto con la metainformación de paginación
        return PostResponse.builder()
                .content(content)
                .pageNo(postsPage.getNumber())
                .pageSize(postsPage.getSize())
                .totalElements(postsPage.getTotalElements())
                .totalPages(postsPage.getTotalPages())
                .last(postsPage.isLast())
                .build();
    }

    public PostResponseDto createPost(PostCreateDto dto, String username) {
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "username", username));

        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(author)
                .build();

        Post savedPost = postRepository.save(post);

        return mapToDto(savedPost);
    }

    private PostResponseDto mapToDto(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .authorUsername(post.getAuthor().getUsername())
                .createdAt(post.getCreatedAt())
                .build();
    }

    // --- ACTUALIZACIÓN DE PUBLICACIÓN ---

    @Transactional
    public PostResponseDto updatePost(Long id, PostCreateDto dto, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicación", "id", id));

        if (!post.getAuthor().getUsername().equals(username)) {
            throw new UnauthorizedAccessException("No tienes permisos para modificar esta publicación");
        }

        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());

        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    // --- ELIMINAR PUBLICACIÓN ---
    @Transactional
    public void deletePost(Long id, String username) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Publicación", "id", id));

        if (!post.getAuthor().getUsername().equals(username)) {
            throw new UnauthorizedAccessException("No tienes permisos para eliminar esta publicación");
        }

        postRepository.delete(post);
    }
}