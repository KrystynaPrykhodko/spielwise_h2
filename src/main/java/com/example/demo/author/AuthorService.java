package com.example.demo.author;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.demo.book.BookRepository;

import jakarta.transaction.Transactional;


@Service
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public List<Author> getAuthors() {
		return authorRepository.findAll();
	}

    public void addNewAuthor(Author author) {
		Optional<Author> bookOptional = authorRepository.findAuthorByAuthorName(author.getAuthorName());
		if (bookOptional.isPresent()) {
			throw new IllegalStateException("AuthorName bereits vergeben");
		}
		authorRepository.save(author); // Author in die Datenbank speichern
		//System.out.println("Speichere Author" + author);	
	}

    public void deleteAuthor(Long id, boolean forceDelete) {
		boolean exists = authorRepository.existsById(id);
		if (!exists) {
			throw new IllegalStateException("Author mit ID " + id + " existiert nicht");
		}

        // Prüfen, ob noch Bücher mit diesem Autor existieren
        long bookCount = bookRepository.countBooksByAuthorId(id);

        if (bookCount > 0) {
            if (forceDelete) {
                // löscht alle Bücher des Authors
                bookRepository.deleteByAuthorId(id);
            } else {

            throw new IllegalStateException("Author mit ID " + id + " kann nicht gelöscht werden, weil er noch Bücher hat.");
        }  
    }
    // löscht den Athor
    authorRepository.deleteById(id);
    }

    @Transactional // explizites Speichern nicht nötig - wird automatisch gemacht, weil @Transactional alle Änderungen merkt und speichert
    public void updateAuthor(
                        Long id,
                        String authorName,
                        LocalDate birthDate,
                        String nationality) {

        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Author mit ID " + id + " existiert nicht"));

        if (authorName != null && authorName.length() > 0 && !author.getAuthorName().equals(authorName)) {
			author.setAuthorName(authorName);
		} 
        if(birthDate != null && !author.getBirthDate().equals(birthDate)) {
            author.setBirthDate(birthDate);
        }
        if (nationality != null && nationality.length() > 0 && !author.getNationality().equals(nationality)) {
            author.setNationality(nationality);
		}
    }
}
