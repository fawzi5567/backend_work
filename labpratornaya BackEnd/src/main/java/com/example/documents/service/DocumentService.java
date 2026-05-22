package com.example.documents.service;

import com.example.documents.model.Document;
import com.example.documents.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository repo;

    public Document create(Document doc) {
        return repo.save(doc);
    }

    public Document update(Long id, Document patch) {
        Document existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        Optional.ofNullable(patch.getTitle()).ifPresent(existing::setTitle);
        Optional.ofNullable(patch.getType()).ifPresent(existing::setType);
        Optional.ofNullable(patch.getBody()).ifPresent(existing::setBody);
        Optional.ofNullable(patch.getSignedAt()).ifPresent(existing::setSignedAt);
        Optional.ofNullable(patch.getUsername()).ifPresent(existing::setUsername);

        return repo.save(existing);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public List<Document> getByUsername(String username) {
        return repo.findByUsername(username);
    }

    public List<Document> getSignedByUsername(String username) {
        return repo.findByUsernameAndSignedAtIsNotNull(username);
    }

    public List<Document> getUnsignedByUsername(String username) {
        return repo.findByUsernameAndSignedAtIsNull(username);
    }

    public List<Document> getByDateRange(LocalDateTime from, LocalDateTime to) {
        return repo.findByCreatedAtBetween(from, to);
    }
}
