package org.zerock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mapper.Sample1Mapper;
import org.zerock.mapper.Sample2Mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class SampleTxServiceImple implements SampleTxService {
	
	@Setter(onMethod_ = @Autowired)
	private Sample1Mapper mapper1;
	
	@Setter(onMethod_ = @Autowired)
	private Sample2Mapper mapper2;

	
	@Transactional    // 두 mapper중 하나가 실패하면 둘다 처리되지 않게 하나로 묶어주기위해 transactional 을 작성해준다.
	@Override
	public void addData(String value) {

		log.info("mapper 1 > > > > > > > > > > ");
		mapper1.insertCol1(value);
		
		log.info("mapper 1 > > > > > > > > > > ");
		mapper2.insertCol2(value);
		
		log.info("end .................. ");
		
		
	}
	
	
	
}
