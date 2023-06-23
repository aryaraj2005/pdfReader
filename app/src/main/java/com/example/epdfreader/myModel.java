package com.example.epdfreader;

public class myModel {
    String filename , fileurl;
    int nol , nod , noc;
    public myModel(){

    }

    public myModel(String filename, String fileurl, int nol, int nod, int noc) {
        this.filename = filename;
        this.fileurl = fileurl;
        this.nol = nol;
        this.nod = nod;
        this.noc = noc;
    }

    public myModel(String filename, String fileurl) {
        this.filename = filename;
        this.fileurl = fileurl;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFileurl() {
        return fileurl;
    }

    public void setFileurl(String fileurl) {
        this.fileurl = fileurl;
    }

    public int getNol() {
        return nol;
    }

    public void setNol(int nol) {
        this.nol = nol;
    }

    public int getNod() {
        return nod;
    }

    public void setNod(int nod) {
        this.nod = nod;
    }

    public int getNoc() {
        return noc;
    }

    public void setNoc(int noc) {
        this.noc = noc;
    }
}
