package id.web.fahmikudo.test.appseller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "seller")
@Getter
@Setter
@AttributeOverride(name = "id", column = @Column(name = "seller_id"))
public class Seller extends BaseEntity {

    @Column(name = "seller_name", nullable = false)
    private String sellerName;

    @Column(name = "seller_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date sallerDateofBirth;

    @Column(name = "seller_nohp")
    private String sellerNoHp;

    @Column(name = "seller_ktp")
    private String sellerKTP;

    @OneToMany(mappedBy = "sellers", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Barang> barangs;

    public Seller() {

    }
}
