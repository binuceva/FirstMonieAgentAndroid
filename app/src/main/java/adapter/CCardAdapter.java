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

public class CCardAdapter {

	private int cdimg;
    private  String pan;
    private  String cstatus;
    private  String cam;
    private  String limit;
    private  String ctype;
    private String accno;






	public CCardAdapter(int cdimg,String pan,String cam,String limit,String cstat,String ctype,String accno) {
		this.cdimg = cdimg;
        this.pan = pan;
        this.cam = cam;
        this.limit = limit;
        this.cstatus = cstat;
        this.ctype = ctype;
        this.accno = accno;
	}





    public int getCDImg() {
        return cdimg;
    }
    public void setCDImg(int img) {
        this.cdimg = cdimg;
    }

    public String getPan() {
        return pan;
    }
    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getCam() {
        return cam;
    }
    public void setCam(String cam) {
        this.cam = cam;
    }

    public String getLimit() {
        return limit;
    }
    public void setLimit(String lim) {
        this.limit = lim;
    }

    public String getCstatus() {
        return cstatus;
    }
    public void setCstatus(String lim) {
        this.cstatus = lim;
    }

    public String getCtype() {
        return ctype;
    }
    public void setCtype(String lim) {
        this.ctype = lim;
    }

    public String getAccno() {
        return accno;
    }
    public void setAccno(String acno) {
        this.accno = acno;
    }
	
}
