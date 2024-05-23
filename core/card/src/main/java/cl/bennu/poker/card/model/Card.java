package cl.bennu.poker.card.model;

import cl.bennu.poker.card.enums.CharacterCardTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Card {

    private CharacterCardTypeEnum characterCardTypeEnum;
    private Integer number;
    private String symbol;

    private boolean use;

}
