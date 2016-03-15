package com.jlsoft.framework.pi.api;

public abstract class AbstractDataProcessor implements IChainProcessor {

    protected IChainProcessor processor = null;

    public AbstractDataProcessor() {
        super();
    }

    public IChainProcessor getProcessor() {
        return processor;
    }

    public IChainProcessor setProcessor(IChainProcessor processor) {
        if (this.processor == null) {
            this.processor = processor;
        } else {
            this.processor.setProcessor(processor);
        }
        return this;
    }

    public Object process(Object data) throws Exception {
        Object result = doProcess(data);

        if (processor != null) {
            result = processor.process(result);
        }

        if (result == null) {
            result = new Object();
        }

        return (Object) result;
    }

    protected abstract Object doProcess(Object record) throws Exception;
}
