package com.bcauction.infrastructure.repository;

import com.bcauction.domain.Transaction;
import com.bcauction.domain.exception.RepositoryException;
import com.bcauction.domain.repository.ITransactionRepository;
import com.bcauction.infrastructure.repository.factory.TransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TransactionRepository implements ITransactionRepository {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Transaction> 목록조회() {
        StringBuilder sbSql =  new StringBuilder("SELECT * FROM Transaction ");
        try {
            return this.jdbcTemplate.query(sbSql.toString(),
                    new Object[]{}, (rs, rowNum) -> TransactionFactory.생성(rs));
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public Transaction 조회(String hash) {
        StringBuilder sbSql =  new StringBuilder("SELECT * FROM Transaction WHERE hash=?");
        try {
            return this.jdbcTemplate.queryForObject(sbSql.toString(),
                    new Object[] { hash }, (rs, rowNum) -> TransactionFactory.생성(rs) );
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public List<Transaction> 조회By주소(final String address)
    {
        StringBuilder sbSql =  new StringBuilder("SELECT * FROM Transaction WHERE from_hash=? OR to_hash=?");
        try {
            return this.jdbcTemplate.query(sbSql.toString(),
                                           new Object[] { address, address }, (rs, rowNum) -> TransactionFactory.생성(rs) );
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }

    @Override
    public long 추가(Transaction Transaction) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("hash", Transaction.getHash());
            paramMap.put("nonce", Transaction.getNonce());
            paramMap.put("block_hash", Transaction.getBlockHash());
            paramMap.put("block_number", Transaction.getBlockNumber());
            paramMap.put("transaction_index", Transaction.getTransactionIndex());
            paramMap.put("from_hash", Transaction.getFrom());
            paramMap.put("to_hash", Transaction.getTo());
            paramMap.put("value", Transaction.getValue());
            paramMap.put("gas_price", Transaction.getGasPrice());
            paramMap.put("gas", Transaction.getGas());
            paramMap.put("input", Transaction.getInput());
            paramMap.put("creates", Transaction.getCreates());
            paramMap.put("public_key", Transaction.getPublicKey());
            paramMap.put("raw", Transaction.getRaw());
            paramMap.put("r", Transaction.getR());
            paramMap.put("s", Transaction.getS());
            paramMap.put("v", Transaction.getV());
            paramMap.put("save_time", LocalDateTime.now());

            this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                    .withTableName("Transaction")
                    .usingGeneratedKeyColumns("id");

            Number newId = simpleJdbcInsert.executeAndReturnKey(paramMap);
            return newId.longValue();

        } catch (Exception e) {
            throw new RepositoryException(e, e.getMessage());
        }
    }
}
