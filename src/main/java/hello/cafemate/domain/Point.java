package hello.cafemate.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@EqualsAndHashCode
@ToString
public class Point {
    private Long id;
    private Long customerId;
    private int amount;
    private Timestamp savedDate;

    public Point() {}

    public Point(Long id, Long customerId, int amount, Timestamp savedDate) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.savedDate = savedDate;
    }

    public Point(Long customerId, int amount, Timestamp savedDate) {
        this.customerId = customerId;
        this.amount = amount;
        this.savedDate = savedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
