
package me.daniel.domain.VO;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class AmountVO {

    private String tid;
    private Integer total, taxFree, vat, point, discount;

    public AmountVO() {
    }

    public AmountVO(String tid, Integer total, Integer taxFree, Integer vat, Integer point, Integer discount) {
        this.tid = tid;
        this.total = total;
        this.taxFree = taxFree;
        this.vat = vat;
        this.point = point;
        this.discount = discount;
    }

    public String getTid() {
        return tid;
    }

    public Integer getTaxFree() {
        return taxFree;
    }

    public Integer getTotal() {
        return total;
    }


    public Integer getVat() {
        return vat;
    }

    public Integer getPoint() {
        return point;
    }

    public Integer getDiscount() {
        return discount;
    }
}
