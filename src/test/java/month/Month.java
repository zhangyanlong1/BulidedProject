package month;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.DomainEvents;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.utils.StringUtils;
import com.zhangyanlong.entity.Domain;
import com.zhangyanlong.service.DomainService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-beans.xml")
public class Month {
	
	@Autowired
	DomainService domainService;
	
	
	//测试添加
	@Test
	public void add() {
		Domain domain = new Domain();
		domain.setText("123");
		domain.setUrl("....");
		domain.setUser_id(72);
		int addDomain = domainService.addDomain(domain);
	}
	
	//测试删除
	@Test
	public void del() {
		boolean delDomain = domainService.delDomain(31);
		System.out.println(delDomain);
	}
	
	//测试查询
	@Test
	public void select() {
		List<Domain> select = domainService.select(72);
		for (Domain domain : select) {
			System.out.println(domain);
		}
	}
	
}
