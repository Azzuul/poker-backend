package cl.bennu.poker.card.enums;

import cl.bennu.poker.card.enums.base.BaseEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.io.Serializable;
import java.util.Arrays;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum CharacterCardTypeEnum implements BaseEnum, Serializable {

    //@formatter:off
    PIKA(1, "Pica")
    , TREBOL(2, "Trebol")
    , CORAZON(3, "Corazon")
    , DIAMANT(4, "Diamante")
    ;
    //@formatter:on

    CharacterCardTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private final Integer id;
    private final String name;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static CharacterCardTypeEnum valueOf(Integer id) {
        return Arrays.stream(values()).filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

}
