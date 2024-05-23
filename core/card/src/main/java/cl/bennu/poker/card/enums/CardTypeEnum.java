package cl.bennu.poker.card.enums;

import cl.bennu.poker.card.enums.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CardTypeEnum implements BaseEnum, Serializable {

    //@formatter:off
    PLAYER(1, "Player", 2)
    , FLOP(2, "Flop", 3)
    , TURN(3, "Turn", 1)
    , RIVER(4, "River", 1)
    ;
    //@formatter:on

    CardTypeEnum(Integer id, String name, Integer cardNumbers) {
        this.id = id;
        this.name = name;
        this.cardNumbers = cardNumbers;
    }

    private final Integer id;
    private final String name;
    private final Integer cardNumbers;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CardTypeEnum valueOf(Integer id) {
        return Arrays.stream(values()).filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

}
