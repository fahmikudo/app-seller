package id.web.fahmikudo.test.appseller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Entity
@Table(name = "barang")
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "barang_id"))
public class Barang extends BaseEntity {

    @Column(name = "barang_name", nullable = false)
    private String barangName;

    @Column(name = "barang_category")
    private String barangCategory;

    @Column(name = "barang_stock")
    private int barangStock;

    @Column(name = "barang_price")
    private double barangPrice;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id", nullable = false)
    @JsonIgnore
    private Seller sellers;

    public Barang() {
    }
}
