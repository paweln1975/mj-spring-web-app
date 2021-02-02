package pl.paweln.mjspringwebapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publisher")
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

    public Publisher() {
    }

    public Publisher(String name, String addressLine1, String zip, String city,  String state) {
        this.addressLine1 = addressLine1;
        this.city = city;
        this.name = name;
        this.state = state;
        this.zip = zip;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
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
