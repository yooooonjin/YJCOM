package project.mybatis;


import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

import com.zaxxer.hikari.HikariConfig;

@Configuration
@PropertySource("classpath:/application.properties")
public class MybatisConfig {
	/* src/main/resources 폴더의 내부의 resource를 읽어들이기 위해서 필요한 객체 */
	@Autowired
	private ApplicationContext applicationContext;
	
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	//DataSource dataSource=new HikariDataSource();
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		//dataSource설정
		factoryBean.setDataSource(dataSource);
		//매퍼 설정
		Resource[] resources= applicationContext.getResources("classpath:/mapper/**/mapper-*.xml");
		factoryBean.setMapperLocations(resources);
		//설정정보 등록
		factoryBean.setConfiguration(mybatisConfiguration());
		return factoryBean.getObject();
	}

	/* SqlSessionTemplate sqlSession처리를 더 효율적으로 처리하기 위한 객체 */
	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	/* map-underscore-to-camel-case=true을 적용하기 위한 설정 */
	@Bean
	@ConfigurationProperties(prefix = "mybatis.configuration")
	public org.apache.ibatis.session.Configuration mybatisConfiguration(){
		return new org.apache.ibatis.session.Configuration();
	}


}
