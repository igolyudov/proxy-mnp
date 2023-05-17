package ml.bigbrains.proxymnp;

import lombok.extern.slf4j.Slf4j;
import ml.bigbrains.proxymnp.model.MNPData;
import ml.bigbrains.proxymnp.service.NiirClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ProxyMnpApplicationTests {

	@Autowired
	private NiirClient niirClient;
	@Test
	void contextLoads() {
	}


	@Test
	public void testGetDataNullRequest() throws Exception
	{
		MNPData data = niirClient.getMNPData(null);
		assertNull(data);
	}

	@Test
	public void testGetDataEmptyRequest() throws Exception
	{
		MNPData data = niirClient.getMNPData("");
		assertNull(data);
	}

	@Test
	public void testGetDataEmpty() throws Exception
	{
		MNPData data = niirClient.getMNPData("98100101011");
		assertNotNull(data);
		assertEquals("98100101011", data.getNumber());
		assertEquals( "", data.getOperator().trim());
		assertEquals( "", data.getRegion().trim());
	}

	@Test
	public void testGetData() throws Exception
	{
		MNPData data = niirClient.getMNPData("9810010101");
		assertNotNull(data);
		assertEquals("9810010101", data.getNumber());
		assertEquals( "\"ВымпелКом\" ПАО", data.getOperator());
		assertEquals( "г. Санкт-Петербург и Ленинградская область", data.getRegion());
	}

	@Test
	public void testGetDataWithUrl() throws Exception
	{
		MNPData data = niirClient.getMNPData("9850010101");
		assertNotNull(data);
		assertEquals("9850010101", data.getNumber());
		assertEquals( "\"Мобильные ТелеСистемы\" ПАО", data.getOperator());
		assertEquals( "г. Москва и Московская область", data.getRegion());
	}

	@Test
	public void testGetDataWrongRequest() throws Exception
	{
		MNPData data = niirClient.getMNPData("8005555555");
		assertNotNull(data);
		assertEquals("8005555555", data.getNumber());
		assertEquals( "", data.getOperator().trim());
		assertEquals( "", data.getRegion().trim());
	}

}
