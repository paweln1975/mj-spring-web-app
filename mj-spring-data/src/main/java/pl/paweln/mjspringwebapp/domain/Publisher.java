package pl.paweln.mjspringwebapp.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publisher")
@Getter
@Setter
@NoArgsConstructor
public class Publisher extends BaseEntity {

    @Column(name = "address_line_1")
    private String addressLine1;

    @Column(name = "city")
    private String city;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @OneToMany(mappedBy="publisher")
    private List<Book> books = new ArrayList<>();

    public Publisher(String name, String addressLine1, String zip, String city,  String state) {
        this.addressLine1 = addressLine1;
        this.city = city;
        this.name = name;
        this.state = state;
        this.zip = zip;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "addressLine1=" + addressLine1 + '\'' +
                "city=" + city + '\'' +
                "id=" + getId() + '\'' +
                "name=" + name + '\'' +
                "state=" + state + '\'' +
                "zip=" + zip + '\'' +
                '}';
    }
}
