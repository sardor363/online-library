package com.sardor.unsplash.service;

import com.sardor.unsplash.entity.Author;
import com.sardor.unsplash.entity.Book;
import com.sardor.unsplash.entity.RequestBook;
import com.sardor.unsplash.payload.ApiResponse;
import com.sardor.unsplash.payload.ConfirmDto;
import com.sardor.unsplash.repository.AuthorRepository;
import com.sardor.unsplash.repository.BookRepository;
import com.sardor.unsplash.repository.RequestBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfirmService {

    @Autowired
    RequestBookRepository requestBookRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    public ApiResponse confirm(Integer requestId, ConfirmDto confirmDto) {

        if (confirmDto.isBool()) {
            if (!requestBookRepository.existsById(requestId)) return new ApiResponse("request book not found", false);
            RequestBook optionalRequest = requestBookRepository.getById(requestId);
            Book book = new Book();
            book.setName(optionalRequest.getName());
            book.setPdfFile(optionalRequest.getPdfFile());
            book.setDescription(optionalRequest.getDescription());
            book.setCategory(optionalRequest.getCategory());
            book.setPhoto(optionalRequest.getPhoto());
            book.setFirstPublished(optionalRequest.getFirstPublished());
            book.setLanguage(optionalRequest.getLanguage());

            Optional<Author> optionalAuthor = authorRepository.findById(confirmDto.getAuthorId());
            if (optionalAuthor.isEmpty()) return new ApiResponse("author not found");
            book.setAuthor(optionalAuthor.get());

            bookRepository.save(book);
            requestBookRepository.deleteById(requestId);
            return new ApiResponse("saved", true);
        }
        requestBookRepository.deleteById(requestId);
        return new ApiResponse("rejected", false);
    }
}
