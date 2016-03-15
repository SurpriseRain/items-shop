
package com.jlsoft.framework.pi.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Class that encapsulate the output data from an {@link IMessageProcessor}. The
 * Response is an ordered collection of three different types of output data.
 * <ul> <li>Output data that form the sucessful processing of the input
 * data</li> <li>Discarded input data</li> <li>{@link MessageException}s that
 * are a result of unsuccessful processing of the input data</li> </ul> The
 * batches are modelled as public inner classes {@link Response.OutputBatch},
 * {@link Response.DiscardBatch} and {@link Response.ExceptionBatch} Use
 * {@link #getBatches()} to extract the ordered list of batches from a Response
 * and {@link DataBatch#getData()} to extract the output from each batch.
 *
 * @author perryj
 * @see IMessageProcessor
 */
public class Response {

    /**
     * static field that can be used to communicate an empty Response.
     */
    public static final Response EMPTY = new Response();
    private List currentBatch = null;
    private List batches = new ArrayList();
    private Set types = new HashSet();
    private Map matadata = null;

    /**
     * adds some data that is a result of sucessfully processing some input data
     * to the response.
     *
     * @param datum output data
     */
    public void addOutput(Object datum) {
        addDatum(datum);
    }

    /**
     * adds some discarded input data to the response
     *
     * @param datum discarded data
     */
    public void addDiscardedInput(Object datum) {
        addDatum(datum, DiscardBatch.class);
    }

    /**
     * adds a MessageException that is a result of unsucessfully processing some
     * input data to the response
     *
     * @param exception exception
     */
    public void addException(Exception exception) {
        addDatum(exception, ExceptionBatch.class);
    }

    /**
     * adds a list of data that is the result of sucessfully processing some
     * input data to the batch.
     *
     * @param batch list of output data
     */
    public void addOutputs(List batch) {
        addData(batch);
    }

    /**
     * adds a list of some discarded input data to the response
     *
     * @param batch list of discarded data
     */
    public void addDiscardedInputs(List batch) {
        addData(batch, DiscardBatch.class);
    }

    /**
     * adds a list of {@link MessageException}s that is a result of
     * unsucessfully processing some input data to the response
     *
     * @param batch list of MessageExceptions
     */
    public void addExceptions(List batch) {
        addData(batch, ExceptionBatch.class);
    }

    /**
     * extracts all the output data from a Response, thus disregarding other
     * types of Response data and their relative order.
     *
     * @return collated data that is the result of sucessfully processing some
     * input data
     */
    public Object[] getCollatedOutput() {
        return getCollatedBatches(OutputBatch.class);
    }

    /**
     * extracts all the discarded data from a Response, thus disregarding other
     * types of Response data and their relative order.
     *
     * @return collated discard data
     */
    public Object[] getCollatedDiscards() {
        return getCollatedBatches(DiscardBatch.class);
    }

    /**
     * extracts all the {@link MessageException}s data from a Response, thus
     * disregarding other types of Response data and their relative order.
     *
     * @return collated exception data
     */
    public Object[] getCollatedExceptions() {
        return getCollatedBatches(ExceptionBatch.class);
    }

    /**
     *
     * @return true if response contains one or more discards
     */
    public boolean containsDiscards() {
        return containsType(DiscardBatch.class);
    }

    /**
     *
     * @return true if response contains one or more {@link MessageException}
     */
    public boolean containsExceptions() {
        return types.contains(ExceptionBatch.class);
    }

    /**
     *
     * @return ordered list of {@link Response.DataBatch}
     */
    public List getBatches() {
        return Collections.unmodifiableList(batches);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (Iterator iter = batches.iterator(); iter.hasNext();) {
            List batch = (List) iter.next();
            buffer.append(buffer.length() > 0 ? "," : "");
            buffer.append(batch.toString());
        }
        return buffer.toString();
    }

    public boolean isEmpty() {
        return batches.isEmpty();
    }

    private void addData(List data) {
        addData(data, OutputBatch.class, true);
    }

    private void addData(List data, Class type) {
        addData(data, type, true);
    }

    private void addDatum(Object datum) {
        addData(datum, OutputBatch.class, false);
    }

    private void addDatum(Object datum, Class type) {
        addData(datum, type, false);
    }

    private boolean containsType(Class type) {
        return types.contains(type);
    }

    private Object[] getCollatedBatches(Class c) {
        ArrayList output = new ArrayList();
        for (Iterator iter = batches.iterator(); iter.hasNext();) {
            List batch = (List) iter.next();
            if (batch.getClass() == c) {
                output.addAll(batch);
            }
        }
        return output.toArray(new Object[output.size()]);
    }

    private void addData(Object data, Class type, boolean isMultiple) {
        if (type == null) {
            type = OutputBatch.class;
        }
        if (currentBatch == null || (currentBatch.getClass() != type)) {
            if (OutputBatch.class == type) {
                currentBatch = new OutputBatch();
            } else if (DiscardBatch.class == type) {
                currentBatch = new DiscardBatch();
            } else if (ExceptionBatch.class == type) {
                currentBatch = new ExceptionBatch();
            } else {
                throw new RuntimeException("Unable to handle data of type" + type.toString());
            }
            batches.add(currentBatch);
        }
        if (isMultiple && (data instanceof List)) {
            currentBatch.addAll((List) data);
        } else {
            currentBatch.add(data);
        }
        if (!types.contains(type)) {
            types.add(type);
        }
    }

    public class DataBatch extends ArrayList {

        protected static final long serialVersionUID = 0x01;

        public Object[] getData() {
            return toArray(new Object[size()]);
        }

        @Override
        public String toString() {
            String type = getClass().getName();

            int i = type.lastIndexOf('.');
            if (i >= 0) {
                type = type.substring(i + 1);
            }
            i = type.lastIndexOf('$');
            if (i >= 0) {
                type = type.substring(i + 1);
            }

            if (type.endsWith("Batch")) {
                type = type.substring(0, type.length() - "Batch".length());
                if (type.startsWith("Response$")) {
                    type = type.substring("Response".length());
                }
            }
            return size() + " " + type + "(s)";
        }
    }

    public class OutputBatch extends DataBatch {

        private static final long serialVersionUID = DataBatch.serialVersionUID;
    }

    public class DiscardBatch extends DataBatch {

        private static final long serialVersionUID = DataBatch.serialVersionUID;
    }

    public class ExceptionBatch extends DataBatch {

        private static final long serialVersionUID = DataBatch.serialVersionUID;
    }

    public void setMatadata(Map matadata) {
        this.matadata = matadata;
    }

    public Map getMatadata() {
        return matadata;
    }
}
