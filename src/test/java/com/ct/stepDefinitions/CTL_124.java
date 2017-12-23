package com.ct.stepDefinitions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.ct.pages.CT_CloanJDBCProject;
import com.ct.utilities.DBUtil;
import com.ct.utilities.DBUtil.DBType;
import com.ct.utilities.Driver;
import com.ct.utilities.PageUtils;

import cucumber.api.java.en.*;

public class CTL_124 {
	
	WebDriver driver=Driver.getInstance();
	CT_CloanJDBCProject user = new CT_CloanJDBCProject();
	
	private String url="jdbc:oracle:thin:@ec2-54-244-62-153.us-west-2.compute.amazonaws.com:1521:xe";
	private String name="ctlqa";
	private String password="ctl987";
	Connection connection=null;
	Statement statement=null;
	ResultSet result=null;
	
	public static String oracleQuery = "Select p.FIRST_NAME, p.LAST_NAME, p.MIDDLE_INIT,to_char(p.DATE_OF_BIRTH,'DD-MON-YYYY'),"
			                           + "p.GENDER,p.SSN_NR "
			                           + "from CTLDEV.PERSON p "
			                           + "JOIN CTLDEV.CORRELATIONS c ON p.SSN_NR = c.SSN_NR "
			                           + "where c.APPLICATION_ID=";
	
	
	public static String id;
	
	@Given("^as User gets loan application ID$")
	public void as_User_gets_loan_application_ID() throws Throwable {
	   id = user.idMessage.getText();
	}

	@Given("^User should have personal information in DataBase$")
	public void user_should_have_personal_information_in_DataBase() throws Throwable {
		
		
		String appId = PageUtils.getId(id);
		
		System.out.println(oracleQuery+appId);
		connection=DriverManager.getConnection(url, name, password);
		statement=connection.createStatement();
		result=statement.executeQuery(oracleQuery+appId);
		
		List<String> column=new ArrayList<>();
		column.add("FIRST_NAME");
		column.add("LAST_NAME");
		column.add("MIDDLE_INIT");
		column.add("TO_CHAR(P.DATE_OF_BIRTH,'DD-MON-YYYY')");
		column.add("GENDER");
		column.add("SSN_NR"); 
		while(result.next()){
			for(String a:column){
			System.out.println(result.getString(a));
		}
	}	
		
		
		
	    
	}

	@Then("^verify information$")
	public void verify_information() throws Throwable {
	    
	}

}