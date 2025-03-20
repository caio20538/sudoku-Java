package br.com.dio.model;

import java.util.Collection;
import java.util.List;

import static br.com.dio.model.GameStatusEnum.*;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

public class Board {
    private final List<List<Space>> space;

    public Board(List<List<Space>> space) {
        this.space = space;
    }

    public List<List<Space>> getSpace() {
        return space;
    }

    public GameStatusEnum getStatus(){
        //permite acessar a lista dentro da lista, verifica se o valor é fixo ou nulo
        //noneMatch nenhuma, anyMatch alguma ocorrencia na lista.

        if (space.stream().flatMap(Collection::stream).
                noneMatch(s -> !s.isFixed() && nonNull(s.getActual()))){
            return NON_STARTED;
        }
        return space.stream().flatMap(Collection::stream).
                anyMatch(s -> isNull(s.getActual()))? INCOMPLETE : COMPLETE;
    }

    public boolean hasErrors(){
        if (getStatus() == NON_STARTED)
            return false;

        return space.stream().flatMap(Collection::stream).
                anyMatch(s -> nonNull(s.getActual()) && s.getActual().equals(s.getExpected()));
    }

    //18:05

}
