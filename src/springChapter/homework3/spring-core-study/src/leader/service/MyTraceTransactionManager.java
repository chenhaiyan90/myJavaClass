package leader.service;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;

public class MyTraceTransactionManager extends DataSourceTransactionManager{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7938334635313866139L;

	@Override
	public DataSource getDataSource() {
		System.out.println("MyTraceTransactionManager  getDataSource called ");
		return super.getDataSource();
		
	}

	@Override
	protected Object doGetTransaction() {
		System.out.println("MyTraceTransactionManager  doGetTransaction called ");
		return super.doGetTransaction();
	}

	@Override
	protected boolean isExistingTransaction(Object transaction) {
		System.out.println("MyTraceTransactionManager  isExistingTransaction called ");
		return super.isExistingTransaction(transaction);
	}

	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) {
		System.out.println("MyTraceTransactionManager  doBegin called ");
		super.doBegin(transaction, definition);
	}

	@Override
	protected void doCommit(DefaultTransactionStatus status) {
		System.out.println("MyTraceTransactionManager  doCommit called ");
		super.doCommit(status);
	}

	@Override
	protected void doRollback(DefaultTransactionStatus status) {
		System.out.println("doRollback  doCommit called ");
		super.doRollback(status);
	}

	@Override
	protected void doSetRollbackOnly(DefaultTransactionStatus status) {
		System.out.println("doRollback  doSetRollbackOnly called ");
		super.doSetRollbackOnly(status);
	}

	 
	
	
}
