// IBookManager.aidl
package com.homeraria.hencodeuicourse.app.aidl;

// Declare any non-default types here with import statements
//即使Bool类以及在Book.aidl中声明，这里任然需要添加import来保证AIDL能找到Book类
//这个借口aidl不需要和声明Book.aidl处于同一位置，这也就是为何引用到的Book类还是需要import语句
import com.homeraria.hencodeuicourse.app.ipc.binder.Book;

interface IBookManager {

    List<Book> getAllBooks();

    void addBook(in Book book);
}
