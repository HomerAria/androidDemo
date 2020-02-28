// Book.aidl
package com.homeraria.hencodeuicourse.app.ipc.binder;

// Declare any non-default types here with import statements
//会在aidl中用到的类必须在aidl中声明，AIDL才能调用Book类
//需要注意，这个Book声明的位置必须和java中的Book类位置相同
parcelable Book;
