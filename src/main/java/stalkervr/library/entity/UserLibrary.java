package stalkervr.library.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserLibrary {

    @Id
    @Column(nullable = false, unique = true)
    private Long id;

    public UserLibrary(Long id) {
        this.id = id;
    }
}
