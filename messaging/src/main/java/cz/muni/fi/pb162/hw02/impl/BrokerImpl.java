package cz.muni.fi.pb162.hw02.impl;

import cz.muni.fi.pb162.hw02.mesaging.broker.Broker;
import cz.muni.fi.pb162.hw02.mesaging.broker.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BrokerImpl implements Broker {
    private final HashMap<String, ArrayList<Message>> data = new HashMap<>();
    private static long id = 0;

    /**
     * Creates new broker
     */
    public BrokerImpl() {
    }

    @Override
    public Collection<String> listTopics() {
        return data.keySet();
    }

    @Override
    public Collection<Message> push(Collection<Message> messages) {
        HashSet<Message> result = new HashSet<>();
        for (Message message : messages) {
            Message newMessage = new MessageImpl(id, message.topics(), message.data());
            result.add(newMessage);
            id += 1;
            for (String topic : message.topics()) {
                if (!data.containsKey(topic)) {
                    ArrayList<Message> newMessages = new ArrayList<>();
                    data.put(topic, newMessages);
                }
                data.get(topic).add(newMessage);
            }
        }
        return result;
    }

    @Override
    public Collection<Message> poll(Map<String, Long> offsets, int num, Collection<String> topics) {
        Set<Message> result = new HashSet<>();
        int counter;
        Long id;
        for (String topic : topics) {
            counter = 0;
            ArrayList<Message> messages = data.getOrDefault(topic, null);
            if (messages == null) {
                continue;
            }
            for (Message message : messages) {
                if (counter >= num) {
                    break;
                }
                id = offsets.get(topic);
                if ((id == null || message.id() > id)) {
                    ++counter;
                    result.add(message);
                }
            }
        }
        return result;

    }
}
