package com.codeaz.Task;

import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Equals;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TaskApplicationTests {


	@Test
	void contextLoads() {
	}
	@Test
	void itShouldReturnResult(){
		Calculator cal= new Calculator();

		//given
		int a=90, b=10;

		//when
		int res= cal.add(a,b);

		//then
		assertEquals(100, res);
	}
	@Test
	void itShouldThrowException(){
		assertThrows(IllegalArgumentException.class, ()->{
			new Calculator().checkNumber(-1);
		});
	}
	@Test
	void itShouldReturnValue(){
		assertEquals(1,new Calculator().checkNumber(1));
	}

	class Calculator {
		int add(int a, int b){
			return a+b;
		}
		int checkNumber(int a){
			if(a<0){
				throw new IllegalArgumentException("a must be greater than 0");
			}else{
				return a;
			}
		}
	}

}
