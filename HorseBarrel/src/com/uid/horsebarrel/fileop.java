package com.uid.horsebarrel;

/** 
	 * @author vamsi katepalli vxk142730
	 * @author vijaykrishn vxv140430
	 * @author pujitha sri lakshmi pxp142730
	  *  * This class is written as part of User Interface Assignment taught by 
	  * 	Dr. John Cole.
	  * Start date:11/22/2014
	 * Class CS 6301.022
	 * Professor John Cole
	 * This class is responsible for reading, writing the scores to and from
	 * the file. 
	 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;	
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import android.os.Environment;

	public class fileop {

		File file = null;
	    FileWriter fw = null;
	    int linect = 0;
	    Scanner s = null;
	    boolean bExists;
	    String strFilename = "Scores.txt";
	    
	    /**
	     * This method reads the file and stores as a list of score objects and returns the list.
	     * @author pujitha sri lakshmi pxp142730
	     * @return List<Score>
	     *  */
	    
	    public List<Score> readFile() throws IOException {
	    	File newFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"ScoreFolder");
	    	List<Score> myContactList = new ArrayList<Score>();
	    	if (!newFolder.exists()){
	    		newFolder.mkdirs();
	    	}
	    		file = new File(newFolder, strFilename);
	    		if (!file.exists()){
	    	    	file.createNewFile();
	    	    }
	    		if(file.canRead()){
	    			try {
	    				FileReader filein = new FileReader(file);
	    		        BufferedReader br = new BufferedReader(filein);
	    		        String line = null;
	    		        while((line = br.readLine()) != null){
	    		        	String[] temp = line.split("`");
	                        Score filedata = new Score(temp[0],Double.parseDouble(temp[1]));
	                        myContactList.add(filedata);
	    		        }
	    		        Collections.sort(myContactList);
	    		        Collections.reverse(myContactList);
	    		        filein.close();
	    		        br.close();
	    			}
	    			catch (FileNotFoundException ex) {
	                    Logger.getLogger(fileop.class.getName()).log(Level.SEVERE, null, ex);
	                } catch (IOException ex) {
	                    Logger.getLogger(fileop.class.getName()).log(Level.SEVERE, null, ex);
	                }
	    		}
	    		
	    	
	    	return myContactList.subList(0, 9);
	    }
	    
	    /**
	     * @author pujitha sri lakshmi pxp142730
	     * This method writes the score object to the file.
	     * @param objScore
	     * @throws IOException 
	     *  */
	    
	    public void writeFile(Score objScore){
	    	
		    try {
		    	File newFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"ScoreFolder");
		    	if (!newFolder.exists()){
		    		bExists = newFolder.mkdirs();
		        }
		    	try{
		    		file = new File(newFolder, strFilename);
		    		file.createNewFile();
		        }
		    	catch (Exception ex){
		    		System.out.println("ex: " + ex);
		        }
		    }
		    catch (Exception e){
		    	System.out.println("e: " + e);
		    }
		    if (!file.canWrite()){
		    	System.out.println("Can't write");
		    }
		    try {
		    	fw = new FileWriter(file, true);
		    	Double scores;
		    	String name;
		    	name = objScore.getName();
	            scores =  objScore.getScores();
	            fw.write(name + "`" + scores + "\n");
	            fw.close();
		    }
		    catch (Exception ex){
		    	System.out.println("Error creating PW: " + ex.getMessage());
		    }
	    }
	    
	    /**
	     * @author vijaykrishn
	     * This method takes the complete name as parameter and returns true if it deletes successfully. 
	     * @param String
	     * @return boolean
	     */
	    
	    public boolean deleteRow(String name){
	        boolean isSuccess = false;
	        try{
	        	File newFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"ContactFolder");
	        	file = new File(newFolder, "Contacts1.txt");
	        	file.createNewFile();
	            File tmpfile = new File(newFolder, "ContactsTmp.txt");
	            tmpfile.createNewFile();
	            FileInputStream filein;
	            BufferedWriter bw = new BufferedWriter(new FileWriter(tmpfile));
	            filein = new FileInputStream(file);
	            BufferedReader br = new BufferedReader(new InputStreamReader(filein));
	            String line, parts[];
	            while((line = br.readLine()) != null){
	                parts = line.split("\t");
	                if(parts[0].equals(name)){
	                    
	                }
	                else
	                    bw.write(line + "\n");
	            }
	            bw.close();
	            br.close();
	            file.delete();
	            tmpfile.renameTo(file);
	            isSuccess= true;
	        }
	        catch (FileNotFoundException ex) {
	                Logger.getLogger(fileop.class.getName()).log(Level.SEVERE, null, ex);
	            } catch (IOException ex) {
	                Logger.getLogger(fileop.class.getName()).log(Level.SEVERE, null, ex);
	            }
	        return isSuccess;
	    }
	}
	

