package fpt.aptech.pjs4.DTOs;

import lombok.Data;

@Data
public class VnPayRequest {
    private String orderInfo;
    private String orderType;
    private int amount;
    private String bankCode;
    private String language;

}
