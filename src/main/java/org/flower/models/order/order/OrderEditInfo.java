package org.flower.models.order.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEditInfo {

    /*
    * 주문 수정을 위한 DTO 클래스
    * */

    private Long orderNo;
    private LocalDate pickupDate;
    private LocalTime pickupTime;
    private String flowertype;
    private String flowercolor;
    private String pricerange;
    private String message;
    private String orderStatus;
}
