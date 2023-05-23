package cz.muni.fi.pb162.hw02.impl;

import cz.muni.fi.pb162.hw02.mesaging.broker.Broker;
import cz.muni.fi.pb162.hw02.mesaging.client.Client;

import java.util.Collection;

public class ClientImpl implements Client {
    private final Broker broker;

    /**
     * Creates new client
     * @param broker broker associated with client
     */
    public ClientImpl(Broker broker) {
        this.broker = broker;
    }
    @Override
    public Broker getBroker() {
        return broker;
    }
    @Override
    public Collection<String> listTopics() {
        return broker.listTopics();
    }
}
