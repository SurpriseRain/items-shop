package com.jlsoft.framework.pi.api;

public abstract class AbstractConvertor extends AbstractDataProcessor implements IConvertor {

    public AbstractConvertor() {
        super();
    }

    public Object doProcess(Object data) throws Exception {
        if (data == null) {
            throw new Exception("Null record not permitted.");
        }
        Object result = convert(data);
        return (Object) result;
    }

    protected abstract Object convert(Object record) throws Exception;
}
