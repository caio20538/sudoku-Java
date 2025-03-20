package br.com.dio.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotifierService {
    //Objetos q vão ouvir esse evento para ouvir precisa implementar a interface
    private final Map<EventEnum, List<EventListenner>> listenner = new HashMap<>(){{
        put(EventEnum.CLEAR_SPACE, new ArrayList<>());
    }};

    public void subscriber(final EventEnum eventType, EventListenner listenner){
        var selectedListenners = this.listenner.get(eventType);
        selectedListenners.add(listenner);
    }

    public void notify(final EventEnum eventType){
        listenner.get(eventType).forEach(l -> l.update(eventType));
    }
}
