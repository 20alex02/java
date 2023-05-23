package cz.muni.fi.pb162.hw02.impl;

import cz.muni.fi.pb162.hw02.mesaging.broker.Broker;
import cz.muni.fi.pb162.hw02.mesaging.broker.Message;
import cz.muni.fi.pb162.hw02.mesaging.client.Consumer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConsumerImpl extends ClientImpl implements Consumer {
    private Map<String, Long> offsets = new HashMap<>();

    /**
     * Creates new consumer
     * @param broker broker associated with consumer
     */
    public ConsumerImpl(Broker broker) {
        super(broker);
    }

    @Override
    public Collection<Message> consume(int num, String... topics) {
        ArrayList<Message> messages = new ArrayList<>(consume(offsets, num, topics));
        messages.sort(Comparator.comparing(Message::id));
        Long messageId;
        Long[] messageIds;
        for (String topic : topics) {
            messageIds = messages.stream()
                    .filter(message -> message.topics().contains(topic))
                    .map(Message::id)
                    .toArray(Long[]::new);
            if (messageIds.length == 0) {
                continue;
            }
            messageId = num <= messageIds.length ? messageIds[num - 1] : messageIds[messageIds.length - 1];
            offsets.put(topic, messageId);
        }
        return messages;
    }

    @Override
    public Collection<Message> consume(Map<String, Long> offsets, int num, String... topics) {
        return getBroker().poll(offsets, num, Set.of(topics));
    }

    @Override
    public Map<String, Long> getOffsets() {
        return Collections.unmodifiableMap(offsets);
    }

    @Override
    public void setOffsets(Map<String, Long> offsets) {
        this.offsets = new HashMap<>(offsets);
    }

    @Override
    public void clearOffsets() {
        offsets = new HashMap<>();
    }

    @Override
    public void updateOffsets(Map<String, Long> offsets) {
        this.offsets.putAll(offsets);
//        for (String topic : offsets.keySet()) {
//            this.offsets.put(topic, offsets.get(topic));
//        }
    }
}
