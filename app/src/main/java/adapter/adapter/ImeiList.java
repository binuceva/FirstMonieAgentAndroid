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
package adapter.adapter;

public class ImeiList {

	private String imei;
    private String dtype;
    private String serial;





	public ImeiList(String imei,String dtype,String serial) {
		this.imei = imei;
        this.dtype = dtype;
        this.serial = serial;

	}



	public String getImei() {
		return imei;
	}
	public void setImei(String bname) {
		this.imei = bname;
	}
    public String getDtype() {
        return dtype;
    }
    public void setDtype(String bname) {
        this.dtype = bname;
    }
    public String getSerial() {
        return serial;
    }
    public void setSerial(String bname) {
        this.serial = bname;
    }

/*
    public String getAmo() {
        return amount;
    }
    public void setAmo(String amon) {
        this.amount = amon;
    }

    public String getAcctype() {
        return acctype;
    }
    public void setAcctype(String acct) {
        this.acctype = acct;
    }*/

	
	
}
