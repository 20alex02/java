package cz.muni.fi.pb162.hw02.impl;
import cz.muni.fi.pb162.hw02.mesaging.broker.Message;
import java.util.Map;
import java.util.Set;

public class MessageImpl implements Message {
    private final Long id;
    private final Set<String> topics;
    private final Map<String, Object> data;

    /**
     * Creates new message
     * @param id message id
     * @param topics message topics
     * @param data message data
     */
    public MessageImpl(Long id, Set<String> topics, Map<String, Object> data) {
        this.id = id;
        this.topics = topics;
        this.data = data;
    }

    /**
     * Creates new message with default id
     * @param topics message topics
     * @param data message data
     */
    public MessageImpl(Set<String> topics, Map<String, Object> data) {
        this.topics = topics;
        this.data = data;
        id = null;
    }


    @Override
    public Long id() {
        return id;
    }

    @Override
    public Set<String> topics() {
        return topics;
    }

    @Override
    public Map<String, Object> data() {
        return data;
    }
}
