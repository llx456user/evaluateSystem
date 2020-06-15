package com.tf.base.jni.test.output;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Assert;
import org.junit.Test;

        class InParameter{
                        Integer width = 0;
                        Integer height = 0;
                        Integer[] number = new Integer[]{0};
                        Student[] student = new Student[]{new Student()};
        public static InParameter parse(JSONObject json){
        if(null != json) {
            InParameter in = new InParameter();
    if (json.containsKey("width")) {
        Integer width = json.getInteger("width");
        in.width = width;
    }
    if (json.containsKey("height")) {
        Integer height = json.getInteger("height");
        in.height = height;
    }
    if (json.containsKey("number")) {
    JSONArray numberJsonArray = json.getJSONArray("number");
        Integer[] numberArray = new Integer[numberJsonArray.size()];
    for (int i = 0; i < numberJsonArray.size(); i++) {
        numberArray[i] = numberJsonArray.getInteger(i);
    }
        in.number = numberArray;
    }
    if (json.containsKey("student")) {
    JSONArray studentJsonArray = json.getJSONArray("student");
        Student[] studentArray = new Student[studentJsonArray.size()];
    for (int i = 0; i < studentJsonArray.size(); i++) {
    JSONObject studentJson = studentJsonArray.getJSONObject(i);
        studentArray[i] = Student.parse(studentJson);
    }
        in.student = studentArray;
    }
        return in;
        }
        return null;
        }

        public String toJsonString(){
        StringBuffer sb = new StringBuffer("{");
                sb.append("\"width\":");
                    sb.append(width);
                    sb.append(",");
                sb.append("\"height\":");
                    sb.append(height);
                    sb.append(",");
                sb.append("\"number\":");
                    sb.append("[");
                    for(int i=0;i<number.length;i++){
                    sb.append(number[i]);
                    if(i < number.length-1){
                    sb.append(",");
                    }
                    }
                    sb.append("]");
                    sb.append(",");
                sb.append("\"student\":");
                    sb.append("[");
                    for(int i=0;i<student.length;i++){
                    sb.append(student[i]);
                    if(i < student.length-1){
                    sb.append(",");
                    }
                    }
                    sb.append("]");
        sb.append("}");
        return sb.toString();
        }

        @Override
        public String toString(){
        return this.toJsonString();
        }

        }
        class OutParameter{
                        Integer area = 0;
                        Integer sum = 0;
                        Integer[] number = new Integer[]{0};
                        Teacher teacher = new Teacher();
        public static OutParameter parse(JSONObject json){
        if(null != json) {
            OutParameter out = new OutParameter();
    if (json.containsKey("area")) {
        Integer area = json.getInteger("area");
        out.area = area;
    }
    if (json.containsKey("sum")) {
        Integer sum = json.getInteger("sum");
        out.sum = sum;
    }
    if (json.containsKey("number")) {
    JSONArray numberJsonArray = json.getJSONArray("number");
        Integer[] numberArray = new Integer[numberJsonArray.size()];
    for (int i = 0; i < numberJsonArray.size(); i++) {
        numberArray[i] = numberJsonArray.getInteger(i);
    }
        out.number = numberArray;
    }
    if (json.containsKey("teacher")) {
    JSONObject teacherJson = json.getJSONObject("teacher");
        Teacher teacher = Teacher.parse(teacherJson);
        out.teacher = teacher;
    }
        return out;
        }
        return null;
        }

        public String toJsonString(){
        StringBuffer sb = new StringBuffer("{");
                sb.append("\"area\":");
                    sb.append(area);
                    sb.append(",");
                sb.append("\"sum\":");
                    sb.append(sum);
                    sb.append(",");
                sb.append("\"number\":");
                    sb.append("[");
                    for(int i=0;i<number.length;i++){
                    sb.append(number[i]);
                    if(i < number.length-1){
                    sb.append(",");
                    }
                    }
                    sb.append("]");
                    sb.append(",");
                sb.append("\"teacher\":");
                    sb.append(teacher);
        sb.append("}");
        return sb.toString();
        }

        @Override
        public String toString(){
        return this.toJsonString();
        }

        }
        class Student{
                        String[] teachers = new String[]{"teachers(String)"};
                        String name = "name(String)";
                        Book[] books = new Book[]{new Book()};
                        Box box = new Box();
        public static Student parse(JSONObject json){
        if(null != json) {
            Student student = new Student();
    if (json.containsKey("teachers")) {
    JSONArray teachersJsonArray = json.getJSONArray("teachers");
        String[] teachersArray = new String[teachersJsonArray.size()];
    for (int i = 0; i < teachersJsonArray.size(); i++) {
        teachersArray[i] = teachersJsonArray.getString(i);
    }
        student.teachers = teachersArray;
    }
    if (json.containsKey("name")) {
        String name = json.getString("name");
        student.name = name;
    }
    if (json.containsKey("books")) {
    JSONArray booksJsonArray = json.getJSONArray("books");
        Book[] booksArray = new Book[booksJsonArray.size()];
    for (int i = 0; i < booksJsonArray.size(); i++) {
    JSONObject booksJson = booksJsonArray.getJSONObject(i);
        booksArray[i] = Book.parse(booksJson);
    }
        student.books = booksArray;
    }
    if (json.containsKey("box")) {
    JSONObject boxJson = json.getJSONObject("box");
        Box box = Box.parse(boxJson);
        student.box = box;
    }
        return student;
        }
        return null;
        }

        public String toJsonString(){
        StringBuffer sb = new StringBuffer("{");
                sb.append("\"teachers\":");
                    sb.append("[");
                    for(int i=0;i<teachers.length;i++){
                        sb.append("\"");
                    sb.append(teachers[i]);
                        sb.append("\"");
                    if(i < teachers.length-1){
                    sb.append(",");
                    }
                    }
                    sb.append("]");
                    sb.append(",");
                sb.append("\"name\":");
                        sb.append("\"");
                    sb.append(name);
                        sb.append("\"");
                    sb.append(",");
                sb.append("\"books\":");
                    sb.append("[");
                    for(int i=0;i<books.length;i++){
                    sb.append(books[i]);
                    if(i < books.length-1){
                    sb.append(",");
                    }
                    }
                    sb.append("]");
                    sb.append(",");
                sb.append("\"box\":");
                    sb.append(box);
        sb.append("}");
        return sb.toString();
        }

        @Override
        public String toString(){
        return this.toJsonString();
        }

        }
        class Teacher{
                        Student[] student = new Student[]{new Student()};
                        String name = "name(String)";
                        Book[] books = new Book[]{new Book()};
                        Box box = new Box();
        public static Teacher parse(JSONObject json){
        if(null != json) {
            Teacher teacher = new Teacher();
    if (json.containsKey("student")) {
    JSONArray studentJsonArray = json.getJSONArray("student");
        Student[] studentArray = new Student[studentJsonArray.size()];
    for (int i = 0; i < studentJsonArray.size(); i++) {
    JSONObject studentJson = studentJsonArray.getJSONObject(i);
        studentArray[i] = Student.parse(studentJson);
    }
        teacher.student = studentArray;
    }
    if (json.containsKey("name")) {
        String name = json.getString("name");
        teacher.name = name;
    }
    if (json.containsKey("books")) {
    JSONArray booksJsonArray = json.getJSONArray("books");
        Book[] booksArray = new Book[booksJsonArray.size()];
    for (int i = 0; i < booksJsonArray.size(); i++) {
    JSONObject booksJson = booksJsonArray.getJSONObject(i);
        booksArray[i] = Book.parse(booksJson);
    }
        teacher.books = booksArray;
    }
    if (json.containsKey("box")) {
    JSONObject boxJson = json.getJSONObject("box");
        Box box = Box.parse(boxJson);
        teacher.box = box;
    }
        return teacher;
        }
        return null;
        }

        public String toJsonString(){
        StringBuffer sb = new StringBuffer("{");
                sb.append("\"student\":");
                    sb.append("[");
                    for(int i=0;i<student.length;i++){
                    sb.append(student[i]);
                    if(i < student.length-1){
                    sb.append(",");
                    }
                    }
                    sb.append("]");
                    sb.append(",");
                sb.append("\"name\":");
                        sb.append("\"");
                    sb.append(name);
                        sb.append("\"");
                    sb.append(",");
                sb.append("\"books\":");
                    sb.append("[");
                    for(int i=0;i<books.length;i++){
                    sb.append(books[i]);
                    if(i < books.length-1){
                    sb.append(",");
                    }
                    }
                    sb.append("]");
                    sb.append(",");
                sb.append("\"box\":");
                    sb.append(box);
        sb.append("}");
        return sb.toString();
        }

        @Override
        public String toString(){
        return this.toJsonString();
        }

        }
        class Book{
                        String name = "name(String)";
        public static Book parse(JSONObject json){
        if(null != json) {
            Book books = new Book();
    if (json.containsKey("name")) {
        String name = json.getString("name");
        books.name = name;
    }
        return books;
        }
        return null;
        }

        public String toJsonString(){
        StringBuffer sb = new StringBuffer("{");
                sb.append("\"name\":");
                        sb.append("\"");
                    sb.append(name);
                        sb.append("\"");
        sb.append("}");
        return sb.toString();
        }

        @Override
        public String toString(){
        return this.toJsonString();
        }

        }
        class Pen{
                        String name = "name(String)";
        public static Pen parse(JSONObject json){
        if(null != json) {
            Pen pens = new Pen();
    if (json.containsKey("name")) {
        String name = json.getString("name");
        pens.name = name;
    }
        return pens;
        }
        return null;
        }

        public String toJsonString(){
        StringBuffer sb = new StringBuffer("{");
                sb.append("\"name\":");
                        sb.append("\"");
                    sb.append(name);
                        sb.append("\"");
        sb.append("}");
        return sb.toString();
        }

        @Override
        public String toString(){
        return this.toJsonString();
        }

        }
        class Box{
                        String name = "name(String)";
                        Pen[] pens = new Pen[]{new Pen()};
        public static Box parse(JSONObject json){
        if(null != json) {
            Box box = new Box();
    if (json.containsKey("name")) {
        String name = json.getString("name");
        box.name = name;
    }
    if (json.containsKey("pens")) {
    JSONArray pensJsonArray = json.getJSONArray("pens");
        Pen[] pensArray = new Pen[pensJsonArray.size()];
    for (int i = 0; i < pensJsonArray.size(); i++) {
    JSONObject pensJson = pensJsonArray.getJSONObject(i);
        pensArray[i] = Pen.parse(pensJson);
    }
        box.pens = pensArray;
    }
        return box;
        }
        return null;
        }

        public String toJsonString(){
        StringBuffer sb = new StringBuffer("{");
                sb.append("\"name\":");
                        sb.append("\"");
                    sb.append(name);
                        sb.append("\"");
                    sb.append(",");
                sb.append("\"pens\":");
                    sb.append("[");
                    for(int i=0;i<pens.length;i++){
                    sb.append(pens[i]);
                    if(i < pens.length-1){
                    sb.append(",");
                    }
                    }
                    sb.append("]");
        sb.append("}");
        return sb.toString();
        }

        @Override
        public String toString(){
        return this.toJsonString();
        }

        }

public class JsonParser{

public String testJson(){
return "{\"in\":{,,,\"student\":[{\"teachers\":[\"teachers(String)\"],\"name\":\"name(String)\",\"books\":[{\"name\":\"name(String)\"}],\"box\":{\"name\":\"name(String)\",\"pens\":[{\"name\":\"name(String)\"}]}}]}}";
}

//todo need to product step one
public void execute(InParameter in) {
//todo
}

//todo need to product step two
public InParameter parseJson(JSONObject sourceJson) {
return InParameter.parse(sourceJson);
}


public String getArrayString(Object[] array) {
StringBuffer sb = new StringBuffer("[");
for (int i = 0; i < array.length; i++) {
sb.append(array[i].toString());
if (i < array.length - 1) {
sb.append(",");
}
}
sb.append("]");
return sb.toString();
}

@Test
public void parseJsonTest(){
System.out.println("source is : ");
        InParameter in = new InParameter();
String jsonString = in.toString();
//String jsonString = testJson();
System.out.println(jsonString);
JSONObject json = JSONObject.parseObject(jsonString);
System.out.println("source json is : ");
System.out.println(json);
System.out.println("parse result is : ");
        InParameter inResult = parseJson(json.getJSONObject("in"));
String resultString = inResult.toJsonString();
System.out.println(resultString);
Assert.assertEquals(jsonString,resultString);
}

}