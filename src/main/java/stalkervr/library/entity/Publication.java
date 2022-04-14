package stalkervr.library.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.TermVector;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Objects;

@Entity
@Indexed
@Getter
@Setter
@NoArgsConstructor

public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column
    @Field(termVector = TermVector.YES)
    private String name;

    @Column(length = 1500, columnDefinition = "text")
    @Field(termVector = TermVector.YES)
    private String description;

    @Column
    private Integer bookCount;

    @Column
    private Integer bookIssued;

    public Publication(String name, String description, Integer bookCount) {
        this.name = name;
        this.description = description;
        this.bookCount = bookCount;
        this.bookIssued = getBookCount();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Publication that = (Publication) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(bookCount, that.bookCount) &&
                Objects.equals(bookIssued, that.bookIssued);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, bookCount, bookIssued);
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", bookCount=" + bookCount +
                ", bookIssued=" + bookIssued +
                '}';
    }
}
