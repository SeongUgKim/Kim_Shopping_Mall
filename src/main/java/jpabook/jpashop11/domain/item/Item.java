package jpabook.jpashop11.domain.item;

import jpabook.jpashop11.domain.Category;
import jpabook.jpashop11.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    //==비지니스 로직==//

    /**
     * stock increment
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * stock decrement
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("Need more stock");
        }
        this.stockQuantity = restStock;
    }
}
