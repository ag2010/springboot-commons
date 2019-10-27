package com.hr.service.system;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class BaseService {
	
	/**
	 * 手动回滚事物
	 */
	public void rollBack() {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}

}
