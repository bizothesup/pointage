/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.horus.pointage.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author HORUS
 */

@Stateless
public class Reporting {
    
    public void imprimer() throws JRException{
         JasperReport jasperReport = JasperCompileManager
               .compileReport("C:/StyledTextReport/StyledTextReport.jrxml");
         
         // Parameters for report
       Map<String, Object> parameters = new HashMap<String, Object>();
       
       
        // DataSource
       // This is simple example, no database.
       // then using empty datasource.
       JRDataSource dataSource = new JREmptyDataSource();
       
       JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
       
        // Make sure the output directory exists.
       File outDir = new File("C:/jasperoutput");
       outDir.mkdirs();
       
       // Export to PDF.
       JasperExportManager.exportReportToPdfFile(jasperPrint,
               "C:/jasperoutput/StyledTextReport.pdf");
        
       System.out.println("Done!");
 
    }
    
}
