package com;

public class Device {
	 public String dmac;
	    public int refpower;
	    public String uuid;
	    public int majorID;
	    public int rssi;
	    public int minorID;
	    public int type;
	    
	    public String time;
		public String getDmac() {
			return dmac;
		}
		public void setDmac(String dmac) {
			this.dmac = dmac;
		}
		public int getRefpower() {
			return refpower;
		}
		public void setRefpower(int refpower) {
			this.refpower = refpower;
		}
		public String getUuid() {
			return uuid;
		}
		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		public int getMajorID() {
			return majorID;
		}
		public void setMajorID(int majorID) {
			this.majorID = majorID;
		}
		public int getRssi() {
			return rssi;
		}
		public void setRssi(int rssi) {
			this.rssi = rssi;
		}
		public int getMinorID() {
			return minorID;
		}
		public void setMinorID(int minorID) {
			this.minorID = minorID;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
	    
	    
}
