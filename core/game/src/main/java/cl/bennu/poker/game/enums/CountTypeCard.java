package cl.bennu.poker.game.enums;

import lombok.Getter;

@Getter
public enum CountTypeCard {
    PAIR(1),
    TRIO(3),
    QUARTET(4);

    private final int value;

    CountTypeCard(int value) {
        this.value = value;
    }

}
