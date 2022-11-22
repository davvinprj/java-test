package com.fabrick.hiring.javatest;

import com.fabrick.hiring.javatest.constants.RestUtils;
import com.fabrick.hiring.javatest.model.BalanceInfo;
import com.fabrick.hiring.javatest.model.MoneyTransferRequest;
import com.fabrick.hiring.javatest.model.SearchTransactionParams;
import com.fabrick.hiring.javatest.model.Transaction;
import com.fabrick.hiring.javatest.service.AccountService;
import com.fabrick.hiring.javatest.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Slf4j
@SpringBootTest(classes = JavaTestApplication.class)
@RunWith(SpringRunner.class)
class JavaTestApplicationTests {

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	private final static String ACCOUNT_ID_TEST="14537780";


	@Test
	public void getBalanceTest(){
		BalanceInfo balanceInfo = accountService.getBalance(ACCOUNT_ID_TEST);
		assert balanceInfo!=null;
		assert balanceInfo.getBalance()==0.07d;

	}

	@Test
	public void postMoneyTransfer(){
		log.debug("-> TransactionsTest:: postMoneyTransfer");
		 accountService.postMoneyTransfer(buidMoneyTransfer());

	}


	@Test
	public void getTransactions() {
		log.debug("-> TransactionsTest:: getTransactions");
		SearchTransactionParams searchTransactionParams= buildTestSearchParams();

		List<Transaction> transactionList= transactionService.getTransactions(searchTransactionParams);
		assert !transactionList.isEmpty();

		log.debug("Transactions Test -> transactions: {}", transactionList);
	}

	private MoneyTransferRequest buidMoneyTransfer(){
		MoneyTransferRequest moneyTransferRequest=MoneyTransferRequest.builder()
				.accountId(ACCOUNT_ID_TEST)
				.amount(800d)
				.currency("EUR")
				.description("Money Transfer Test").receiverName("John Doe")
				.iban("IT23A0336844430152923804660").build();
		return moneyTransferRequest;
	}

	private SearchTransactionParams buildTestSearchParams() {
		Date from= null;
		try {
			from = new SimpleDateFormat(RestUtils.DATE_FORMAT).parse("21-01-01");
			Date to= new SimpleDateFormat(RestUtils.DATE_FORMAT).parse("2021-11-01");
			return SearchTransactionParams.builder().from(from).to(to).accountId(ACCOUNT_ID_TEST).build();
		} catch (ParseException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

}
