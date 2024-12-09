package io.github.fighting_game;

import java.util.Objects;

public class Transition {
    public String to;
    public boolean isImmediate;

    Transition(String to, boolean isImmediate){
        this.to = to;
        this.isImmediate = isImmediate;
    }

    Transition(String to){
        this(to, true);
    }

    public boolean is_immediate(){
        return isImmediate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, isImmediate);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;

        if(obj == null || getClass() != obj.getClass()) return false;

        Transition other = (Transition) obj;

        return isImmediate = other.isImmediate &&
            Objects.equals(to, other.to);
    }
}
