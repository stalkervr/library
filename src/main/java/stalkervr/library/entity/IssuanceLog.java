package stalkervr.library.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class IssuanceLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String operation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserLibrary userLibrary;

    @ManyToOne
    @JoinColumn(name = "publication_id")
    private Publication publication;

    @Basic
    @Column(columnDefinition = "date")
    private Instant dateOfIssue;

    @Basic
    @Column(columnDefinition = "date")
    private Instant dateReturn;

    public IssuanceLog(String operation,UserLibrary userLibrary, Publication publication) {
        this.operation = operation;
        this.userLibrary = userLibrary;
        this.publication = publication;
        this.dateOfIssue = Instant.now();
        this.dateReturn = Instant.now().plus(5, ChronoUnit.DAYS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssuanceLog that = (IssuanceLog) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(operation, that.operation) &&
                Objects.equals(userLibrary, that.userLibrary) &&
                Objects.equals(publication, that.publication) &&
                Objects.equals(dateOfIssue, that.dateOfIssue) &&
                Objects.equals(dateReturn, that.dateReturn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, operation, userLibrary, publication, dateOfIssue, dateReturn);
    }

    @Override
    public String toString() {
        return "IssuanceLog{" +
                "id=" + id +
                ", operation='" + operation + '\'' +
                ", userLibrary=" + userLibrary +
                ", publication=" + publication +
                ", dateOfIssue=" + dateOfIssue +
                ", dateReturn=" + dateReturn +
                '}';
    }
}
