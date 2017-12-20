package com.ct.stepDefinitions;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.ct.pages.CT_HomePage;
import com.ct.utilities.CTContstants;
import com.ct.utilities.Driver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class personalStepInfo {
	CT_HomePage homePage = new CT_HomePage();
	WebDriver driver = Driver.getInstance();
	String[] chars=
		{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", 
				"p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

	@Given("^User navigates to the following url: \"([^\"]*)\"$")
	public void user_navigates_to_the_following_url(String arg1) throws Throwable {
		Driver.getInstance().get(CTContstants.QA_URL);

	}

	@Given("^Enters First Name and Last Name$")
	public void enters_First_Name_and_Last_Name() throws Throwable {
		Driver.getInstance().switchTo().defaultContent();
		Driver.getInstance().switchTo().parentFrame();
		Driver.getInstance().switchTo().frame(1);
		homePage.firstName.sendKeys("Adam");
		homePage.lastName.sendKeys("Smith");

	}

	@Then("^Verify Data type: String characters a-Z$")
	public void verify_Data_type_String_characters_a_Z() throws Throwable {
		homePage.firstName.clear();
		homePage.lastName.clear();
		Thread.sleep(5000);
		homePage.firstName.sendKeys(chars);
		
		if(chars.equals(chars)){
		for(int i=0; i<chars.length; i++){
			System.out.print(chars[i].toString());
			
		}
		}else{
			System.out.println("Did NOT enter the charackters from A-Z");
		}
		
		homePage.lastName.sendKeys(chars);
		
		

	}

	@Then("^Field type: text box$")
	public void field_type_text_box() throws Throwable {
		String textBox1=homePage.firstName.getAttribute("type");
		System.out.println(textBox1);
		Assert.assertTrue(textBox1.equals("text"));
		
		String textBox2=homePage.middleName.getAttribute("type");
		Assert.assertTrue(textBox2.equals("text"));
		
		
		String textBox3=homePage.lastName.getAttribute("type");
		Assert.assertTrue(textBox3.equals("text"));
		
		
		
	}

	@Then("^Accepted special characters \\(apostrophe, hyphen\\)$")
	public void accepted_special_characters_apostrophe_hyphen() throws Throwable {
		homePage.firstName.sendKeys("'"+"-");
		homePage.lastName.sendKeys("'"+"-");
	}

	@Then("^User selects the gender$")
	public void user_selects_the_gender() throws Throwable {
		homePage.GenderMale.click();
	}

	@Then("^Verify field input (\\d+) options, F, M$")
	public void verify_field_input_options_F_M(int arg1) throws Throwable {
		String textM = homePage.GenderMale.getAttribute("value");
		System.out.println(textM);
		Assert.assertEquals("M", textM);
		String textF = homePage.GenderFemale.getAttribute("value");
		System.out.println(textF);
		Assert.assertEquals("F", textF);

		if (textM.equals("M") && textF.equals("F")) {
			System.out.println("Success!!! Feild input options M and F are verified!");
		} else {
			System.out.println("Feild input options M and F are NOT verified!");
		}
	}

	@Then("^User enters Date of Birth$")
	public void user_enters_Date_of_Birth() throws Throwable {
		homePage.DateOfBirth.click();
		Select dateOfBirth = new Select(homePage.selectMonth);
		dateOfBirth.selectByVisibleText("Apr");

		dateOfBirth = new Select(homePage.selectYear);
		dateOfBirth.selectByVisibleText("1990");
		driver.findElement(By.xpath("(//div[@id='ui-datepicker-div']/table/tbody/tr/td)[14]")).click();

	}

	@Then("^Verify Watermark format is MM/DD/YYYY$")
	public void verify_Watermark_format_is_MM_DD_YYYY() throws Throwable {
		String verifyFormat = homePage.dateFormat.getText();

		if (verifyFormat.equalsIgnoreCase("MM/DD/YYYY")) {
			System.out.println("Success!!! Month-Date-Year format is verified!");
		} else {
			System.out.println("Exeption! Month-Date-Year format is DID NOT match the requirement");
		}
	}

	@Then("^Data type: integer$")
	public void data_type_integer() throws Throwable {
		homePage.DateOfBirth.click();
		List <WebElement> nomericList= 
				driver.findElements(By.xpath("//div[@id='ui-datepicker-div']/table/tbody/tr/td"));
		
		Object[] i=nomericList.toArray();
		if(Arrays.equals(i, chars)){
			System.out.println("Arrays are equals");
		}else{
			System.out.println("calendar of date anly integer format");
		}

		for(WebElement list:nomericList){
			System.out.print(list.getText());
			if(i.equals(chars)){
				System.out.println("Exception! The nomeric list of date format DID NOT match the requirement!!!");
			}
		}
		 Assert.assertFalse(chars.equals(i));
	}

	@Then("^User enters SSN$")
	public void user_enters_SSN() throws Throwable {
		homePage.PersonalFileID.sendKeys("12345678");
	}

	@Then("^Verify Data type integer only$")
	public void verify_Data_type_integer_only() throws Throwable {
		homePage.PersonalFileID.clear();
		homePage.PersonalFileID.sendKeys("hello world!");
		homePage.NextButton.click();
		WebElement a=driver.findElement(By.xpath("//div/p[@id='error-Number']"));
		System.out.println(a.getText());
		String ErrorMessage=a.getText();
		
		if(ErrorMessage.equals("Enter a valid number.")){
			System.out.println("Success! SSN integer format was verified!");
		}
	}

	@Then("^Watermark format \"([^\"]*)\" \\.$")
	public void watermark_format(String arg1) throws Throwable {
		homePage.PersonalFileID.clear();
		homePage.PersonalFileID.sendKeys(arg1);
		homePage.NextButton.click();
		WebElement a=driver.findElement(By.xpath("//div/p[@id='error-Number']"));
		String ErrorMessage=a.getText();
		Assert.assertTrue(ErrorMessage.contains("Enter a valid number."));
		
	}

}
