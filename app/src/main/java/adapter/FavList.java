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

public class FavList {

	private String name;
    private  String ftype;
    private String accn;
	private int idImg;



	public FavList(String name, int idImg, String ftype,String acn) {
		this.name = name;
            this.ftype = ftype;
          this.accn = acn;
		this.idImg = idImg;
	}



	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getIdImg() {
		return idImg;
	}
	public void setIdImg(int idImg) {
		this.idImg = idImg;
	}

    public String getFType() {
        return ftype;
    }
    public void setFtype(String fty) {
        this.ftype = fty;
    }


    public String getAccn() {
        return accn;
    }
    public void setAccn(String acn) {
        this.accn = acn;
    }
}
