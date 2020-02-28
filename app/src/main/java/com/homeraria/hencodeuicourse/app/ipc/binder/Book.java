/*
 * Copyright (c) 2019 Guangdong oppo Mobile Communication(Shanghai)
 * Corp.,Ltd. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 *       copyright notice, this list of conditions and the following
 *       disclaimer in the documentation and/or other materials provided
 *       with the distribution.
 *     * Neither the name of The Linux Foundation nor the names of its
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 * IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * File: Book.java
 * Description:
 *
 * ---------------------------- Revision History: ------------------------
 * <author>             <date>          <version>           <desc>
 * sean.zhou@oppo.com   2020/2/27         1.0                 create this module
 * -----------------------------------------------------------------------
 */
package com.homeraria.hencodeuicourse.app.ipc.binder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 1. 必须继承Parcelable
 */
public class Book implements Parcelable {

    public int bookId;
    public String name;

    public Book(int bookId, String name) {
        this.bookId = bookId;
        this.name = name;
    }

    /*
     * auto generate for class implemented from Parcelable below
     */

    /**
     * 是Parcelable类需要的特殊Constructor，符合静态类CREATOR中的创建需要
     * 这是是通过Parcel类读取数据，remote-->local
     */
    protected Book(Parcel in) {
        bookId = in.readInt();
        name = in.readString();
    }

    /**
     * 负责反序列化
     */
    public static final Creator<Book> CREATOR = new Creator<Book>() {
        /**
         * 从序列化data中获取原始对象
         */
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        /**
         * 创建原始对象数组
         */
        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    /**
     * 描述
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Parcel类负责序列化
     * 这里是通过Parcel类写入数据，local-->remote
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookId);
        dest.writeString(name);
    }
}
