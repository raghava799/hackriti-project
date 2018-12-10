package com.alacriti.hackriti.delegate;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.alacriti.hackriti.utils.constants.Excelconstants;
import com.alacriti.hackriti.vo.EmployeeVO;


public class ExcelReader 
{
	 String bucketName = "parkingslotbucket";
     String key = "Parking.xlsx";
	static Logger log = Logger.getLogger(ExcelReader.class.getName());
	
	
	public Map<Integer, ArrayList<EmployeeVO>> getEmpValues(File file) throws EncryptedDocumentException, InvalidFormatException, IOException 
	{
		AmazonS3 s3 = AmazonS3ClientBuilder.defaultClient();       
		S3Object object = s3.getObject(new GetObjectRequest(bucketName, key));
		
		log.info("object :"+object+" path : "+object.getKey());
			     
	    log.info("file :"+file);
	   
		EmployeeVO employeeVO=null;
		ArrayList<EmployeeVO> twoWheelerempPref = new ArrayList<EmployeeVO>();
		ArrayList<EmployeeVO> fourWheelerempPref = new ArrayList<EmployeeVO>();
		Map<Integer,ArrayList<EmployeeVO>> rempPref = new HashMap<Integer,ArrayList<EmployeeVO>>();
		//String SAMPLE_XLSX_FILE_PATH = "https://s3.amazonaws.com/parkingslotbucket/Parking.xlsx";
		Workbook workbook = WorkbookFactory.create(file);
		// Retrieving the number of sheets in the Workbook
		log.info("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");
	    for(Sheet sheet: workbook) 
	    {
	    	log.info("=> " + sheet.getSheetName());
	    }
	    // Getting the Sheet at index zero
	    Sheet sheet = workbook.getSheetAt(0);
        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();
        XSSFRow row;
        XSSFCell cell;

        int rows; // No of rows
        rows = sheet.getPhysicalNumberOfRows();

        int cols = 0; // No of columns
        int tmp = 0;
        int cblacks=0;
        // This trick ensures that we get the data properly even if it doesn't start from first few rows
        for(int i = 0; i <= 10 || i <= rows; i++) 
        {
            row = (XSSFRow) sheet.getRow(i);
            if(row != null) {
                tmp = sheet.getRow(i).getPhysicalNumberOfCells();
                if(tmp >= cols){
                	cols = tmp;
                }else
                {
                	rows++;
                	cblacks++;
                }
            }
            cols++;
        }
        cols=cols+cblacks;
        for(int r = 0; r <= rows; r++) 
        {
            row = (XSSFRow) sheet.getRow(r+1);
            if(row != null) 
            {
                for(int c = 0; c < cols; c+=2) 
                {
                    cell = row.getCell(c);
                    String cellValue = dataFormatter.formatCellValue(cell);
                    if(cell == null)
                    {
                        break;
                    }else
                    {
                    	  employeeVO =new EmployeeVO();
                    	  employeeVO.setEmployeeRollId(cellValue);
                    	  cell = row.getCell(c+1);
                    	  cellValue = dataFormatter.formatCellValue(cell);
                    	  if(cell == null)
                          {
                    		  break;
                          }else
                          {
                    	  employeeVO.setPreference(cellValue);
          	              //log.info("emp :" +employeeVO.getEmployeeName()+ "\t"+employeeVO.getPreference());
                          }
                    }
                }
                if(Excelconstants.TWO_WHEELER.equalsIgnoreCase(employeeVO.getPreference()))
                {
                	twoWheelerempPref.add(employeeVO);
                	//log.info("twoArg :"+twoArg+" two :"+twoWheelerempPref.get(twoArg).getEmployeeRollId()+" "+twoWheelerempPref.get(twoArg).getPreference());
                }else if(Excelconstants.FOUR_WHEELER.equalsIgnoreCase(employeeVO.getPreference()))
                {
                	fourWheelerempPref.add(employeeVO);
                	//log.info("fourArg :"+fourArg+" two :"+twoWheelerempPref.get(fourArg).getEmployeeRollId()+" "+twoWheelerempPref.get(fourArg).getPreference());
                }
        }   
	     // Closing the workbook
	     workbook.close();
	     //objectData.close();
        }
        rempPref.put(Excelconstants.TWO_WHEELER_ARG,twoWheelerempPref);
        rempPref.put(Excelconstants.FOUR_WHEELER_ARG,fourWheelerempPref);
        for(EmployeeVO employeeVO1 : twoWheelerempPref)
		{
			System.out.println("twoWheelerempPref :"+employeeVO1.getEmployeeRollId());
		}
        for(EmployeeVO employeeVO1 : fourWheelerempPref)
		{
			System.out.println("fourWheelerempPref :"+employeeVO1.getEmployeeRollId());
		}
       return rempPref;
	}
}
