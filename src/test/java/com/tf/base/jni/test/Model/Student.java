package com.tf.base.jni.test.Model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Set;

/**
 * Created by HP on 2018/4/24.
 */
public class Student {

    private String name;

    private String[] teachers;

    private Book[] books;

    private Box box;

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getTeachers() {
        return teachers;
    }

    public void setTeachers(String[] teachers) {
        this.teachers = teachers;
    }

    public static Student parse(JSONObject json) {
        if (null != json) {
            Student student = new Student();
            if (json.containsKey("box")) {
                JSONObject boxJson = json.getJSONObject("box");
                Box box = Box.parse(boxJson);
                student.setBox(box);
            }
            if (json.containsKey("books")) {
                JSONArray bookArray = json.getJSONArray("books");
                Book[] books = new Book[bookArray.size()];
                for (int i = 0; i < bookArray.size(); i++) {
                    JSONObject bookJson = bookArray.getJSONObject(i);
                    books[i] = Book.parse(bookJson);
                }
                student.setBooks(books);
            }
            if (json.containsKey("name")) {
                String name = json.getString("name");
                student.setName(name);
            }
            if (json.containsKey("teachers")) {
                JSONArray teachersJsonArray = json.getJSONArray("teachers");
                String[] teacherArray = new String[teachersJsonArray.size()];
                for (int i = 0; i < teachersJsonArray.size(); i++) {
                    teacherArray[i] = teachersJsonArray.getString(i);
                }
                student.setTeachers(teacherArray);
            }
            return student;
        }
        return null;
    }

}
