/*
 * Docker - containerization
 * Docker hub is a repository which contains n number of images(or softwares).
 * 1 image can create multiple containers and it can be used as grid environment.
 * after installing docker hub and run "docker version" and "docker compose version" commands in cmd prompt.
 
 * Basic Commands
 		* docker version
 		* docker -v
 		* docker info
 		* docker -- help
 		* docker login
 
 * Image related commands 
  		* docker images --> displays list of images on the machine
  		* docker pull ubuntu --> pulls ubuntu image
  		* docker rmi <image id> --> removes image
  		
  * Container commands
  		* docker ps --> will show the containers which are running.
  		* docker run <image name> --> creating a container from downloaded image. If image is not downloaded, it will download during run time.
  		* docker start <image name> --> Starting the container.
  		* docker stop <image name> --> stop the container.
  		* docker rm <container id/name> --> removing the container.
  		* docker run -it <image name> --> interaction with container
  		 
  * System commands
  		* docker stats
  		* docker system df
  		* docker system prune -f --> removes all the containers from the system.
  
  --> Selenium grid setup with docker containers:
  	
  	1) Pull Docker images:
  		* docker pull selenium/hub
  		* docker pull selenium/node-firefox
  		* docker pull selenium/node-chrome
  	
  	2) Verify Images:
  		* docker images
  	
  	3) Running Docker containers:
  		* docker network create <name> --> here name given is 'grid'
  		* docker run -d -p 4442-4444:4442-4444 --net grid --name selenium-hub selenium/hub
  		* docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub -e SE_EVENT_BUS_PUBLISH_PORT=4442 -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 selenium/node-chrome
  		* docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub -e SE_EVENT_BUS_PUBLISH_PORT=4442 -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 selenium/node-firefox

 	4) Run master.xml with hub or node names
 	5) Stopping the containers
 		* docker network rm grid --> removes the grid network.
 	
 --> Disadvantages:
 		* Manual work to enter commands
 		* Solution to above disadvantage --> docker-compose.yaml file
 */


package testCases;

import java.io.File;
import java.io.FileReader;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

	public static WebDriver driver; //conflict for 2 webdriver instances, so making a common webdriver by adding static keyword.
	public Logger logger;
	public Properties p;
	
	@BeforeClass(groups= {"Regression", "Master", "Sanity"})
	@Parameters({"os","browser"})
	public void setup(String os, String br) throws InterruptedException, IOException {
		
		//Loading config.properties file.
		FileReader file=new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		
		logger=LogManager.getLogger(this.getClass());
		
		/*
		//Selenium Grid - did not install as it would cause harm to the system.
		//Execution environment is remote.
		if(p.getProperty("execution_env").equalsIgnoreCase("remote")){
			DesiredCapabilities capabilities=new DesiredCapabilities();
			
			//os
			if(os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.WIN11);
			}
			else {
				System.out.println("No matching os");
				return;
			}
			
			//browser
			switch(br.toLowerCase()) {
			case "chrome": capabilities.setBrowserName("chrome"); break;
			case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
			default: System.out.println("No matching browser"); break;
			}
			driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
			
			
		} */
		
		//Execution environment is local.
		if(p.getProperty("execution_env").equalsIgnoreCase("local")) {
		switch(br.toLowerCase()){
		
		case "chrome": driver=new ChromeDriver(); break;
		case "edge": driver=new EdgeDriver(); break;
		default: System.out.println("Invalid browser name.."); break;
			
		}
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL")); // reading URL from properties file.
		driver.manage().window().maximize();
		Thread.sleep(5000);
	}
	
	@AfterClass(groups= {"Regression", "Master", "Sanity"})
	public void tearDown() {
		driver.quit();
		
	}
	
	//random data generator
	
		public String randomData() {
			
			String generatedString= RandomStringUtils.randomAlphabetic(5);
			return generatedString;
		}
		
		public String randomNumeric() {
			String generatedNum= RandomStringUtils.randomNumeric(8);
			return generatedNum;
		}
		
		public String randomAlphanumeric() {
			String generatedString= RandomStringUtils.randomAlphabetic(5);
			String generatedNum= RandomStringUtils.randomNumeric(8);
			return (generatedString +"@"+generatedNum);
		}
		
		public String captureScreen(String tname) throws IOException{
			String timeStamp=new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
			TakesScreenshot take_ss=(TakesScreenshot) driver;
			File sourceFile=take_ss.getScreenshotAs(OutputType.FILE);
			
			String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\"+ tname+"_"+timeStamp+".png";
			File targetFile=new File(targetFilePath);
			
			sourceFile.renameTo(targetFile);
			return targetFilePath;
		}
}
