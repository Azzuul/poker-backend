package cl.bennu.poker.card.model;

import cl.bennu.poker.card.enums.CardTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class Play implements Serializable {

    @Id
    @MongoId
    private String uuid;
    private List<Card> cards;
    private Integer players;
    private CardTypeEnum cardTypeEnum;

}
