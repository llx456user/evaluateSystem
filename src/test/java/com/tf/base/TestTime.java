package com.tf.base;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Description
 * @Author 圈哥
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/12/20
 */
public class TestTime
{


    public static void main(String[] args) {
        //
      Date  startDagte = StringToDate("2019/12/19 23:18");

        Date  infoDate = StringToDate("2019/12/20 06:00");
        int ret =  differentDays(startDagte,infoDate);
        if(ret==0) {//第一天
            System.out.printf("第一天");
        }else if(ret==1) {//第二天
            System.out.printf("第二天");
            try {
                int sRet = dateCompare6(infoDate);
                if(sRet==-1){
                    System.out.println("时间小");
                }else{
                    System.out.println("时间大");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {//第三天及以后
            System.out.printf("第三天及以后");
        }

    }

    public static Date StringToDate(String dateStr) {
        String formatStr = "yyyy/MM/dd HH:mm";
        DateFormat dd = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = dd.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 根据时间类型比较时间大小
     *
     * @param source
     * @param traget
     * @param type "YYYY-MM-DD" "yyyyMMdd HH:mm:ss"  类型可自定义  传递时间的对比格式
     * @return
     *  0 ：source和traget时间相同
     *  1 ：source比traget时间大
     *  -1：source比traget时间小
     * @throws Exception
     */
//    public static int dateCompare(String source, String traget, String type) throws Exception {
//        int ret = 2;
//        SimpleDateFormat format = new SimpleDateFormat(type);
//        Date sourcedate = format.parse(source);
//        Date tragetdate = format.parse(traget);
//        ret = sourcedate.compareTo(tragetdate);
//        return ret;
//    }
        public static int dateCompare6(Date sourceDate) throws Exception {

            Calendar c = Calendar.getInstance();
            c.setTime(sourceDate);
            c.set(Calendar.HOUR_OF_DAY, 6);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
           int  ret=1 ;
           if(sourceDate.getTime()<c.getTimeInMillis()){
               return  -1 ;
           }else if(sourceDate.getTime()==c.getTimeInMillis()){
               return  0 ;
           }
            return ret;
          }


    /**
     * date2比date1多的天数
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1,Date date2)
    {
        if(date1.after(date2)){
            Date date3=date1;
            date1=date2;
            date2=date3;
        }
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //不同年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2-day1) ;
        }
        else    //同年
        {
            return day2-day1;
        }
    }

}
