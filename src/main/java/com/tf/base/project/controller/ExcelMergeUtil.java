package com.tf.base.project.controller;

import com.tf.base.common.domain.ProjectIndexAssess;
import com.tf.base.project.domain.AssessTable;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;

public class ExcelMergeUtil {
//    public static void main(String[] args) throws Exception {
//        TestExcel t=new TestExcel();
//        XWPFDocument document = new XWPFDocument();
//        t.megerTableCell(document);
//        t.saveDocument(document, "d:/saveFile/temp/sys_"+ System.currentTimeMillis() + ".docx");
//    }

    public void megerTableCell(XWPFTable table1, AssessTable assessTable) {
        setTableWidth(table1, "8000");
        fillTable(table1,assessTable);
        for (int c=1;c<assessTable.getColumns();c++) {
            List<ProjectIndexAssess> plist = assessTable.getListByColumn(c);
            int startRow = 0 ;
            int endRow = 0 ;
            for(ProjectIndexAssess p:plist){
                int cNum = assessTable.getChildrenNum(p);
                if(cNum>0){
                    endRow = startRow+cNum-1;
                    mergeCellsVertically(table1, c-1, startRow,endRow);
                }
                if(cNum==0){
                    startRow++;
                }else{
                    startRow=endRow+1;
                }
            }
        }
//        mergeCellsVertically(table1, 0, 0,5);
//        mergeCellsVertically(table1, 1, 0,2);
//        mergeCellsVertically(table1, 1, 3,5);
//        mergeCellsVertically(table1, 1, 1,4);
//        mergeCellsVertically(table1, 4, 2, 4);
//        mergeCellsHorizontal(table1,0,3,5);
//        mergeCellsHorizontal(table1,2,2,3);
//        mergeCellsHorizontal(table1,2,6,7);
    }

    public  void fillTable(XWPFTable table,AssessTable assessTable) {
        for (int rowIndex = 0; rowIndex < table.getNumberOfRows(); rowIndex++) {
            XWPFTableRow row = table.getRow(rowIndex);
            row.setHeight(380);
            for (int colIndex = 0; colIndex < row.getTableCells().size(); colIndex++) {
                XWPFTableCell cell = row.getCell(colIndex);
                ProjectIndexAssess info = assessTable.getValueByRowAndColumn(rowIndex, colIndex);
                String value = info.getIndexName() + "【值：" + (info.getIndexValue()==null?"":info.getIndexValue())
                        +"; 权重：" +(info.getWeightCurrent()==null?"":info.getWeightCurrent()) +"】" ;
                setCellText(cell,value, "D4DBED", 1000);
//                if(rowIndex%2==0){
//                    setCellText(cell, " cell " + rowIndex + colIndex + " ", "D4DBED", 1000);
//                }else{
//                    setCellText(cell, " cell " + rowIndex + colIndex + " ", "AEDE72", 1000);
//                }
            }
        }
    }

    public  void setCellText(XWPFTableCell cell,String text, String bgcolor, int width) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        //cell.setColor(bgcolor);
        CTTcPr ctPr = cttc.addNewTcPr();
        CTShd ctshd = ctPr.addNewShd();
        ctshd.setFill(bgcolor);
        ctPr.addNewVAlign().setVal(STVerticalJc.CENTER);
//        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        cttc.getPList().get(0).addNewPPr().addNewJc().setVal(STJc.CENTER);
        cell.setText(text);
    }


    /**
     * @Description: 跨列合并
     */
    public  void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if ( cellIndex == fromCell ) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /**
     * @Description: 跨行合并
     * @see http://stackoverflow.com/questions/24907541/row-span-with-xwpftable
     */
    public  void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if ( rowIndex == fromRow ) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    public void setTableWidth(XWPFTable table,String width){
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        CTJc cTJc=tblPr.addNewJc();
        cTJc.setVal(STJc.Enum.forString("center"));
        tblWidth.setW(new BigInteger(width));
        tblWidth.setType(STTblWidth.DXA);
    }

    public void saveDocument(XWPFDocument document, String savePath)
            throws Exception {
        FileOutputStream fos = new FileOutputStream(savePath);
        document.write(fos);
        fos.close();
    }
}
