/*
 * Copyright (C) 2012 jfrankie (http://www.survivingwithandroid.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package adapter;

public class Forex {

    private String curr;
    private String sell;
    private String buy;
    private int img;


    public Forex(String curr, String buy, String sell,int img) {
        this.curr = curr;
        this.buy = buy;
        this.sell = sell;
        this.img = img;
    }


    public String getCurr() {
        return curr;
    }

    public void setCurr(String name) {
        this.curr = name;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int name) {
        this.img = name;
    }
    public String getSell() {
        return sell;
    }

    public void setSell(String desc) {
        this.sell = desc;
    }


    public String getBuy() {
        return buy;
    }

    public void setBuy(String name) {
        this.buy = name;
    }
}