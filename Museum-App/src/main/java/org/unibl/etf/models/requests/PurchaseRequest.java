package org.unibl.etf.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.unibl.etf.models.entities.enums.CardType;

import javax.validation.constraints.NotBlank;
import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String cardNumber;
    @NotBlank
    private CardType cardType;
    @NotBlank
    private Date expirationDate;
    @NotBlank
    private String pin;
    @NotBlank
    private Double requestedAmount;

}
