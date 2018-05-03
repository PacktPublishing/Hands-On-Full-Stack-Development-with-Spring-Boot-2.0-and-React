package com.packt.cardatabase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.packt.cardatabase.domain.Car;
import com.packt.cardatabase.domain.CarRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CarRepositoryTest {
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	private CarRepository repository;
	
	@Test
	public void saveCar() {
		Car car = new Car("Tesla", "Model X", "White", "ABC-1234", 2017, 86000);
		entityManager.persistAndFlush(car);
		
		assertThat(car.getId()).isNotNull();
	}
	
	@Test
	public void deleteCars() {
		Car car = new Car("Tesla", "Model X", "White", "ABC-1234", 2017, 86000);
		entityManager.persistAndFlush(car);
		
		repository.deleteAll();
		assertThat(repository.findAll()).isEmpty();
	}	

}
