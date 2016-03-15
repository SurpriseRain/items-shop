package com.jlsoft.framework.pi.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Message {

    /**
     * Key under which a message history will (optionally) be stored in the
     * metadata.
     */
    public static final String MESSAGE_HISTORY_KEY = "MessageHistory";
    /**
     * Constant to be used in message history for message processors that were
     * not given a name
     */
    public static final String UNNAMED_MESSAGE_PROCESSOR = "UNNAMED_MESSAGE_PROCESSOR";
    /**
     * A reference to the object that initiated the processing, this is not
     * necessarily the caller of {@link IMessageProcessor#process} nor is it
     * necessarily a IMessageProcessor.
     */
    private Object sender;
    /**
     * The data that should be processed as a single transaction.
     */
    private Object[] data;
    /**
     * Optional metadata that describes or relates to this message.
     */
    private Map metadata;

    /**
     * Constructor.
     */
    public Message(final Object[] data, final Object sender, final Map metadata) {
        this.data = data;
        this.sender = sender;
        this.metadata = metadata != null ? metadata : new HashMap();
    }

    /**
     * Constructor.
     */
    public Message(final List data, final Object sender, final Map metadata) {
        this((Object[]) data.toArray(new Object[data.size()]), sender, metadata);
    }

    /**
     * Constructor.
     */
    public Message(final Object data, final Object sender, final Map metadata) {
        this(new Object[]{data}, sender, metadata);
    }

    public Object getSender() {
        return sender;
    }

    /**
     * @return the input data to be processed by an {@link IMessageProcessor}.
     */
    public Object[] getData() {
        return data;
    }

    /**
     * @return metadata that describes or relates to this message.
     */
    public Map getMetadata() {
        return metadata;
    }

    /**
     * Sets metadata that describes or relates to this message.
     *
     * @param metadata
     */
    public void setMetadata(Map metadata) {
        this.metadata = metadata;
    }

}
