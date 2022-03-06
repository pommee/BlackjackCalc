import java.util.HashMap;

public class Card {

    enum Type {
        HEARTS, CLUBS, SPADES, DIAMONDS
    }

    enum Value {
        ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING
    }

    Type type;
    Value value;

    public Type[] getTypes() {
        return Type.values();
    }

    public Value[] getValues() {
        return Value.values();
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value + " - " + type;
    }
}
