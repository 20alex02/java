package cz.muni.fi.pb162.hw02.impl;

import cz.muni.fi.pb162.hw02.mesaging.broker.Broker;
import cz.muni.fi.pb162.hw02.mesaging.broker.Message;
import cz.muni.fi.pb162.hw02.mesaging.client.Producer;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class ProducerImpl extends ClientImpl implements Producer {
    /**
     * Creates new producer
     * @param broker broker associated with producer
     */
    public ProducerImpl(Broker broker) {
        super(broker);
    }

    @Override
    public Message produce(Message message) {
        Iterator<Message> messages = produce(Collections.singleton(message)).iterator();
        return messages.hasNext() ? messages.next() : null;
    }

    @Override
    public Collection<Message> produce(Collection<Message> messages) {
        return getBroker().push(messages);
    }
}
