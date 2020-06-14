package com.equity.demo.ut;

import org.springframework.http.MediaType;
import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.equity.demo.controller.TransactionsController;
import com.equity.demo.model.Order;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc // 不启动服务器,使用mockMvc进行测试http请求。启动了完整的Spring应用程序上下文，但没有启动服务器
public class TransControllerTest extends BaseSpringBootTest {

	@Autowired
	private TransactionsController transactionsController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(transactionsController).build();
	}

	@Test
	public void getAllPositions() throws Exception {
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/getRTPositions"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

		logger.info(mvcResult.getResponse().getContentAsString());
	}
	
	 /**
     * 
     * @throws Exception 
     */
    @Test
    public void orderTest() throws Exception {
    	Order order = new Order();
    	order.setTradeID(1);
    	order.setTransactionID(1L);
    	order.setQuantity(50);
    	order.setSecurityCode("REL");
    	order.setCommand("INSERT");
    	order.setTradeMark("Buy");
    	
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(order)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        
        logger.info(mvcResult.getResponse().getContentAsString());
    }
}
