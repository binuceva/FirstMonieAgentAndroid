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

public class CardStat {

	private String retname;
    private String termclass;
    private String minamo;
    private String cdate;
    private String opercode;






	public CardStat(String mindesc, String minam, String tclass,String cdate,String opcode) {
		this.minamo = minam;
this.retname = mindesc;
        this.cdate = cdate;
        this.termclass = tclass;
        this.opercode = opcode;

	}



	public String getRetName() {
		return retname;
	}
	public void setRetName(String bname) {
		this.retname = bname;
	}

	public String getAmo() {
		return minamo;
	}
	public void setAmo(String bmob) {
		this.minamo = bmob;
	}

    public String getCDate() {
        return cdate;
    }
    public void setCdate(String bmob) {
        this.cdate = bmob;
    }

    public String getTClass() {
        return termclass;
    }
    public void setTClass(String bmob) {
        this.termclass = bmob;
    }

    public String getOp() {
        return opercode;
    }
    public void setOp(String bmob) {
        this.opercode = bmob;
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
