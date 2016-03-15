package com.jlsoft.utils;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class PubTransaction {
	/**
	 * @todo 获取O2O库事务控制
	 * @param txManager_o2o
	 * @return
	 * @throws TransactionException
	 */
	public static TransactionStatus getTransactionStatus_o2o(DataSourceTransactionManager txManager_o2o) throws TransactionException {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = txManager_o2o.getTransaction(def);
        return status;
    }
}
