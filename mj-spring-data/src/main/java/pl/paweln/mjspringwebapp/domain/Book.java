package pl.paweln.mjspringwebapp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
@Setter
@Getter
@NoArgsConstructor
public class Book extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "isbn")
    private String isbn;

    @ManyToMany
    @JoinTable (name = "author_book", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name="publisher_id")
    private Publisher publisher;

    public Book(String title, String isbn) {
        this.title = title;
        this.isbn = isbn;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + this.getId() +
                ", title='" + title + '\'' +
                ", isbn='" + isbn + '\'' +
                ", authors=" + authors +
                '}';
    }
}

