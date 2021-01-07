package utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Reports {

	public static ExtentReports report;
	public static ExtentTest test;
	public static String RepName = "";
	public static String responseFilePath = "";
	public static String runtimeValuefilePath = "";

	public Reports() {

		RepName = new SimpleDateFormat("ddMMyyHHmmss").format(new Date());
		String sysPath = System.getProperty("user.dir") + File.separator + "Reports" + File.separator + "Reports_"
				+ RepName;
		responseFilePath = sysPath + File.separator + "Responses" + File.separator;

		new File(sysPath).mkdir();
		new File(Reports.responseFilePath.substring(0, Reports.responseFilePath.length() - 1)).mkdir();

		report = new ExtentReports(sysPath + File.separator + "ApiTestAutomationReport.html"); // Initialize HTML file
																								// for Report

		report.loadConfig(new File("Extent-config.xml")); // Load Extent Configuration

		report.flush();

	}

	public void startTest(String TCName) {

		TCName = "<font color=#8467D7><b>" + TCName;

		test = report.startTest(TCName);
		report.flush();
	}

	public void stopTest(String TCName) {
		report.endTest(test);
	}

	public void fail(String StepName, String Description) {

		StepName = "<font color='#43C6DB'>" + StepName;
		test.log(LogStatus.FAIL, StepName, "<b><font color='red'>" + Description);
		report.flush();
	}

	public void info(String StepName, String Description) {

		StepName = "<font color='#43C6DB'>" + StepName;
		test.log(LogStatus.INFO, StepName, Description);
		report.flush();
	}

	public void pass(String StepName, String Description) {

		StepName = "<font color='#43C6DB'>" + StepName;

		test.log(LogStatus.PASS, StepName, "<b><font color='Green'>" + Description);

		report.flush();
	}

	public String NewWindowPopUpHTMLCode() {
		return "onclick = \"window.open(this.href,'newwindow', 'width=" + "500" + ",height=" + "800"
				+ "');return false;\"";
	}

	public void ReportSuccessResponse(String currentResponseFileRelativePath) {

		try {
			pass("Response File Path",
					"Response is verified successfully" + "<div align='right' style='float:right'><a "
							+ NewWindowPopUpHTMLCode() + " target='_blank' href=" + currentResponseFileRelativePath
							+ ">Response Json</a></div>");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void ReportFailedResponse(String currentResponseFileRelativePath) {

		try {
			fail("Response File Path",
					"Response failed in verification" + "<div align='right' style='float:right'><a "
							+ NewWindowPopUpHTMLCode() + " target='_blank' href=" + currentResponseFileRelativePath
							+ ">Response Json</a></div>");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
