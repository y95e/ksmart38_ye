package ksmart38.mybatis.encryption;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Test01 {

	public static void main(String[] args) {
		StandardPBEStringEncryptor stringPBEConfig = new StandardPBEStringEncryptor();
		
		stringPBEConfig.setPassword("ksmart38"); 			//대칭키(암호화키)
		stringPBEConfig.setAlgorithm("PBEWithMD5AndDES");
		
		//암호화할 평문 대상		
		String jdcUrl = "jdbc:log4jdbc:mysql://localhost:3306/ksmart38db?serverTimezone=UTC&characterEncoding=UTF8";
		String userName = "ksmart38id";
		String password = "ksmart38pw";
		
		System.out.println("평문(jdcUrl)" + jdcUrl);
		System.out.println("암호문(jdcUrl)" + stringPBEConfig.encrypt(jdcUrl));
		System.out.println("평문(userName)" + userName);
		System.out.println("암호문(userName)" + stringPBEConfig.encrypt(userName));
		System.out.println("평문(password)" + password);
		System.out.println("암호문(password)" + stringPBEConfig.encrypt(password));
		
		System.out.println("복호화(password)" + stringPBEConfig.decrypt("2GG0gs/qFg1sVd2T6TM5sJ+EkOLw6fJfzEM2VPm1lb0xq5Tdha8xAn4aazGSIHqP2s4qhUDvVCTM9ERxTTAOfRGP6lURAhRFFUFPze6WWdiLXLE0kJK2EHU7/oqq/oWOwmGuqbs8Au8="));
		System.out.println("복호화(password)" + stringPBEConfig.decrypt("YP11EsgkY5rE8MWPjnCJEKHQpH98i9Os"));
		System.out.println("복호화(password)" + stringPBEConfig.decrypt("I2aNp/htjnjhXhNN47DA5dSuk3Ltx3Ng"));
		

	}

}
