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

public class LoanPOJO {

	private String ldesc;
    private String stat;
    private String acno;
    private String vamo;
    private String lbal;
    private String irate;
    private String period;
    private String install;
    private String dued;




	public LoanPOJO(String ldesc,
             String stat,
             String acno,
           String vamo,
             String lbal,
           String irate,
             String period,

           String install , String dud) {

		this.ldesc = ldesc;
this.stat = stat;
        this.acno = acno;
        this.vamo = vamo;
this.dued = dud;
        this.lbal = lbal;
        this.irate = irate;
        this.period = period;
        this.install = install;
	}



	public String getLdesc() {
		return ldesc;
	}
	public void setLdesc(String accid) {
		this.ldesc = accid;
	}

	public String getLbal() {
		return lbal;
	}
	public void setLbal(String curr) {
		this.lbal = curr;
	}

    public String getAcno() {
        return acno;
    }
    public void setAcno(String amon) {
        this.acno = amon;
    }

    public String getIrate() {
        return irate;
    }
    public void setIrate(String acct) {
        this.irate = acct;
    }

    public String getStat() {
        return stat;
    }
    public void setStat(String accid) {
        this.stat = accid;
    }

    public String getPeriod() {
        return period;
    }
    public void setPeriod(String curr) {
        this.period = curr;
    }

    public String getVamo() {
        return vamo;
    }
    public void setVamo(String amon) {
        this.vamo = amon;
    }

    public String getInstall() {
        return install;
    }
    public void setInstall(String acct) {
        this.install = acct;
    }

    public String getDueD() {
        return dued;
    }
    public void setDueD(String acct) {
        this.dued = acct;
    }
	
}
