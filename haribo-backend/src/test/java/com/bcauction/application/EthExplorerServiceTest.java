package com.bcauction.application;

import com.bcauction.domain.wrapper.Block;
import com.bcauction.domain.wrapper.EthereumTransaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EthExplorerServiceTest {

    @Autowired
    private IEthereumService ethExplorerService;

    @Test
    public void test블록검색()
    {
        String 블록Id = "503";
        Block 블록 = this.ethExplorerService.블록검색(블록Id);

        assert String.valueOf(블록.getBlockNo()).equals(블록Id);
    }

    @Test
    public void 최근블록조회()
    {
        List<Block> 블록리스트 = this.ethExplorerService.최근블록조회();

        assert 블록리스트.size() == 20;
    }

    @Test
    public void test최근트랜잭션조회()
    {
        List<EthereumTransaction> 트랜잭션리스트 = this.ethExplorerService.최근트랜잭션조회();
        String 블록Id = (트랜잭션리스트.size() > 0) ? 트랜잭션리스트.get(0).getBlockId() : "0";

        if(트랜잭션리스트.size() > 0)
            assert 트랜잭션리스트.get(0).getBlockId() != null;
    }

    @Test
    public void test트랜잭션검색()
    {
        EthereumTransaction tx = this.ethExplorerService.트랜잭션검색("0xca00d3af3e89495eba77b7a2e44a5d80bab716695570ab63eff17c12e40ac7f2");

        assert tx != null;
        assert Integer.valueOf(tx.getBlockId()) > 0;
    }

}
