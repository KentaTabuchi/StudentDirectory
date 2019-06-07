package com.kenta.tabuchi;

import com.kenta.tabuchi.repositories.StudentRepository;

/**
 * This is the method that makes some sample data for test.
 */
public class TestData {

	public void setSampleDataToDB(StudentRepository repository){
		Student student1 = new Student();
		student1.setName("山田 太郎");
		student1.setNamePhonetic("やまだ　たろう");
		student1.setBirthday("1956/12/10");
		student1.setPhone("090-1111-2222");
		student1.setAddress("東京都江東区古谷町一丁目2番3号");
		student1.setEmail("taro.yamada@google.com");
		student1.setGraduation("1974");
		repository.saveAndFlush(student1);
		
		Student student2 = new Student();
		student2.setName("鈴木 花子");
		student2.setNamePhonetic("すずき　はなこ");
		student2.setBirthday("1964/05/03");
		student2.setPhone("080-1234-3456");
		student2.setAddress("大阪府大阪市都島区柏町５丁目４番２２号");
		student2.setEmail("suzuki.hanako@exchange.com");
		student2.setGraduation("1982");
		repository.saveAndFlush(student2);
		
		Student student3 = new Student();
		student3.setName("山田 花子");
		student3.setNamePhonetic("やまだ　はなこ");
		student3.setBirthday("1970/01/30");
		student3.setPhone("0120-9999-5555");
		student3.setAddress("北海道旭川市斗亜流町３丁目１０番５号");
		student3.setEmail("hanako.yamada@yahoo.co.jp");
		student3.setGraduation("1986");
		repository.saveAndFlush(student3);
	}
}
